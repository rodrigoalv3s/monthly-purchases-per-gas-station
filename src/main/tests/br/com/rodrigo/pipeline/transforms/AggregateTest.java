package br.com.rodrigo.pipeline.transforms;

import avro.shaded.com.google.common.collect.Lists;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class AggregateTest {

    @Rule
    public final transient TestPipeline pipeline = TestPipeline.create();

    static final Schema inputSchema = Schema
            .builder()
            .addInt32Field("gasStationId")
            .addStringField("yearMonth")
            .addDoubleField("price")
            .build();

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
    public void testAggregate() {
        PCollection<Row> transactions = pipeline.apply(Create.of(
                List.of(
                        Row.withSchema(inputSchema).addValues(3704,"2012-08",672.64).build(),
                        Row.withSchema(inputSchema).addValues(3704,"2012-08",430.72).build(),
                        Row.withSchema(inputSchema).addValues(3704,"2012-08",121.99).build())))
                .setRowSchema(inputSchema);

        PCollection<Row> output = transactions.apply("Aggregate",  new Aggregate());

        Row expectedRow = Row.withSchema(resultSchema)
                .addValues(
                        Row.withSchema(keySchema)
                                .addValues(3704,"2012-08").build(),
                        Row.withSchema(valueSchema)
                                .addValues(1225.35).build()
                ).build();

//        PAssert.that("Should contain 1 row with the expected result aggregated by gasStationId and yearMonth",
//                output).containsInAnyOrder(expectedRow);

        // Validate with more control over the Double calculation
        PAssert.that(output)
                .satisfies(
                        input -> {
                            LinkedList<Row> aggregatedRow = Lists.newLinkedList(input);

                            assertEquals(1, aggregatedRow.size());

                            for (Row row : aggregatedRow) {
                                assertEquals("Should have 2 fields (key and value)", 2, row.getFieldCount());

                                Row key = row.getRow("key");
                                Row value = row.getRow("value");

                                assertEquals("Should have 2 fields in the key", 2, key.getFieldCount());

                                assertEquals("The gasStationId should be 3704", Optional.of(3704), Optional.of(key.getInt32("gasStationId")));
                                assertEquals("The yearMonth should be 2012-08", "2012-08", key.getString("yearMonth"));

                                assertEquals("Should have 1 field in the value", 1, value.getFieldCount());

                                assertEquals("The sumTransactions value should be 1225.35", Optional.of(Math.round(1225.35)), Optional.of(Math.round(value.getDouble("sumTransactions"))));
                            }
                            return null;
                        });

        pipeline.run().waitUntilFinish();
    }
}
