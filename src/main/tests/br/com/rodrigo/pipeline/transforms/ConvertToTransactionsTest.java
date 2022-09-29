package br.com.rodrigo.pipeline.transforms;
import br.com.rodrigo.pipeline.schemas.Transaction;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.testing.NeedsRunner;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class ConvertToTransactionsTest {
    @Rule
    public final transient TestPipeline pipeline = TestPipeline.create();

    @Test
    @Category(NeedsRunner.class)
    public void testReadFile() {
        PCollection<String> output =
                pipeline
                        .apply("ReadFile", TextIO.read()
                                .from(getClass().getClassLoader().getResource("test1.csv").toString()));

        String expectedRow1 = "1,2012-08-24,\"09:41:00\",31543,486621,3704,2,28,672.64";
        String expectedRow2 = "2,2012-08-24,\"10:03:00\",46707,550134,3704,2,18,430.72";
        String expectedRow3 = "3,2012-08-24,\"10:03:00\",46707,550134,3704,23,1,121.99";

        PAssert.that("Should have 3 string lines in any order with the correct values according to the input file",
                output).containsInAnyOrder(expectedRow1, expectedRow2, expectedRow3);

        pipeline.run().waitUntilFinish();
    }

    @Test
    @Category(NeedsRunner.class)
    public void testReadStrings() {
        final String[] LINE_ARRAY = new String[] {"1,2012-08-24,\"09:41:00\",31543,486621,3704,2,28,672.64",
                "2,2012-08-24,\"10:03:00\",46707,550134,3704,2,18,430.72",
                "3,2012-08-24,\"10:03:00\",46707,550134,3704,23,1,121.99"};

        final List<String> LINES = Arrays.asList(LINE_ARRAY);

        PCollection<Transaction> output =
                pipeline
                        .apply("CreateInputData", Create.of(LINES))
                        .apply("ParseCSVtoTransactions", new ConvertToTransactions());

        Transaction expectedRow1 =
                Transaction.create(1,
                        "2012-08-24",
                        "09:41:00",
                        31543,
                        486621,
                        3704,
                        2,
                        28,
                        672.64
                );

        Transaction expectedRow2 =
                Transaction.create(2,
                        "2012-08-24",
                        "10:03:00",
                        46707,
                        550134,
                        3704,
                        2,
                        18,
                        430.72
                );

        Transaction expectedRow3 =
                Transaction.create(3,
                        "2012-08-24",
                        "10:03:00",
                        46707,
                        550134,
                        3704,
                        23,
                        1,
                        121.99
                );

        PAssert.that("Should have 3 Transaction objects in any order with the correct values",
                output).containsInAnyOrder(expectedRow1, expectedRow2, expectedRow3);

        pipeline.run().waitUntilFinish();
    }
}
