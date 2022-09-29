package br.com.rodrigo.pipeline.transforms;

import br.com.rodrigo.pipeline.options.GasStationOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import org.apache.beam.sdk.values.Row;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static br.com.rodrigo.pipeline.utils.FileUtils.findFilesStartWith;

@RunWith(JUnit4.class)
public class SinkTest {
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

    static final String rootDir = FileSystems.getDefault()
            .getPath("")
            .toAbsolutePath()
            .toString();

    GasStationOptions options;

    @Before
    public void createOptions() {
        String[] args = {
                String.format("--inputPath=%s/input/", rootDir),
                "--fileName=transactions_1k_202209260946.csv",
                String.format("--outputPath=%s/output/", rootDir)
        };

        PipelineOptionsFactory.register(GasStationOptions.class);

        options = PipelineOptionsFactory
                .fromArgs(args).withValidation()
                .as(GasStationOptions.class);
    }

    @Test
    public void testSink() throws URISyntaxException, IOException {
        PCollection<Row> inputRows = pipeline.apply("GetInputData", Create.of(List.of(
                Row.withSchema(resultSchema)
                        .addValues(
                                Row.withSchema(keySchema)
                                        .addValues(3704,"2012-08").build(),
                                Row.withSchema(valueSchema)
                                        .addValues(1225.35).build()
                        ).build()
        ))).setRowSchema(resultSchema);

        PDone output = inputRows.apply("Sink", new Sink(options.getOutputPath()));

        pipeline.run().waitUntilFinish();

        Assert.assertEquals(output.getClass(), PDone.class);

        Assert.assertTrue("Should exist a file with the name prefix ",
                findFilesStartWith(options.getOutputPath(), "output-transactions-summary*").size() > 0);
    }

    @After
    public void removeFiles() throws IOException {
        List<String> files = findFilesStartWith(options.getOutputPath(), "output-transactions-summary*");

        for(String fileName: files) {
            Files.deleteIfExists(Path.of(options.getOutputPath() + fileName));
        }
    }
}
