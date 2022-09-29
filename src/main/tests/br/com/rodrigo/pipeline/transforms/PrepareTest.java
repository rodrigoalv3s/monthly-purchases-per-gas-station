package br.com.rodrigo.pipeline.transforms;

import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PrepareTest {
    @Rule
    public final transient TestPipeline pipeline = TestPipeline.create();

    static final Schema schema = Schema
            .builder()
            .addInt32Field("gasStationId")
            .addStringField("yearMonth")
            .addDoubleField("price")
            .build();

    @Test
    public void testPrepare() {
        PCollection<Row> output =
                pipeline
                        .apply("ReadTestCSV", TextIO.read()
                                .from(getClass().getClassLoader().getResource("test1.csv").toString()))
                        .apply("Prepare", new Prepare());

        Row expectedRow1 = Row.withSchema(schema).addValues(3704,"2012-08",672.64).build();
        Row expectedRow2 = Row.withSchema(schema).addValues(3704,"2012-08",430.72).build();
        Row expectedRow3 = Row.withSchema(schema).addValues(3704,"2012-08",121.99).build();

        PAssert.that("Should have 3 Row objects in any order with 3 columns and the correct values",
                output).containsInAnyOrder(expectedRow1, expectedRow2, expectedRow3);

        pipeline.run().waitUntilFinish();
    }
}
