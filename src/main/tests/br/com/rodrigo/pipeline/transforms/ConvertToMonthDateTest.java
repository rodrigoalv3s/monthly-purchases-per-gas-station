package br.com.rodrigo.pipeline.transforms;

import br.com.rodrigo.pipeline.schemas.Transaction;
import br.com.rodrigo.pipeline.schemas.TransactionPerMonth;
import br.com.rodrigo.pipeline.schemas.TransactionPerMonthTest;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.schemas.transforms.AddFields;
import org.apache.beam.sdk.schemas.transforms.Select;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class ConvertToMonthDateTest {
    @Rule
    public final transient TestPipeline pipeline = TestPipeline.create();

    @Test
    public void testConvertToMonthDate() {

        final Schema schema = Schema
                .builder()
                .addInt32Field("gasStationId")
                .addStringField("yearMonth")
                .addDoubleField("price")
                .build();

        PCollection<Transaction> transactions = pipeline.apply(Create.of(List.of(
                Transaction.create(1, "2022-09-28", "16:56:00", 1, 2, 3, 4, 1, 100.99),
                Transaction.create(2, "2022-09-28", "16:57:00", 1, 3, 3, 4, 1, 100.99),
                Transaction.create(3, "2022-08-28", "16:58:00", 2, 4, 2, 4, 3, 302.97))));

        PCollection<Row> output = transactions.apply("SelectFields", Select.fieldNames("gasStationId", "date", "price"))
                .apply("CreateMonthField",
                        AddFields.<Row>create()
                                .field("yearMonth", Schema.FieldType.STRING))
                .apply("ConvertDateFormat", new ConvertToMonthDate()).setRowSchema(schema);

        PAssert.that("Should contain 3 rows with 3 columns and the correct values", output).containsInAnyOrder(
                Row.withSchema(schema)
                        .addValues(3, "2022-09", 100.99)
                        .build(),
                Row.withSchema(schema)
                        .addValues(3, "2022-09", 100.99)
                        .build(),
                Row.withSchema(schema)
                        .addValues(2, "2022-08", 302.97)
                        .build());

        pipeline.run().waitUntilFinish();
    }
}
