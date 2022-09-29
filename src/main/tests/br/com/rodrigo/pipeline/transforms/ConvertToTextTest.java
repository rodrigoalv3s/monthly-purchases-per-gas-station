package br.com.rodrigo.pipeline.transforms;

import org.apache.beam.sdk.schemas.Schema;
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
public class ConvertToTextTest {

    @Rule
    public final transient TestPipeline pipeline = TestPipeline.create();

    static final Schema keySchema = Schema
            .builder()
            .addInt32Field("gasStationId")
            .addStringField("yearMonth")
            .build();

    static final Schema valueSchema = Schema
            .builder()
            .addDoubleField("sumTransactions")
            .build();

    static final Schema resultSchema = Schema
            .builder()
            .addRowField("key", keySchema)
            .addRowField("value", valueSchema)
            .build();

    @Test
    public void testConvertToTextTest() {

        PCollection<Row> groupedTransactions = pipeline.apply("GetInputData", Create.of(List.of(
                Row.withSchema(resultSchema)
                        .addValues(
                                Row.withSchema(keySchema)
                                        .addValues(3704,"2012-08").build(),
                                Row.withSchema(valueSchema)
                                        .addValues(1225.35).build()
                        ).build()
        ))).setRowSchema(resultSchema);

        PCollection<String> output = groupedTransactions.apply("ConvertToText", new ConvertToText());

        String expectedResult = "3704,2012-08,1225.35";

        PAssert.that("Should create a comma separated string with 3 columns and the expected values",
                output).containsInAnyOrder(expectedResult);

        pipeline.run().waitUntilFinish();
    }
}
