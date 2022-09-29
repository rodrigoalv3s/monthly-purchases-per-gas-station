package br.com.rodrigo.pipeline.transforms;

import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import org.apache.beam.sdk.values.Row;

public class Sink extends PTransform<PCollection<Row>, PDone> {

    private String outputPath;
    public Sink(String outputPath) {
        this.outputPath = outputPath + "output-transactions-summary";
    }

    @Override
    public PDone expand(PCollection<Row> input) {
        return input
                .apply("ConvertToText", new ConvertToText())
                .apply("WriteToCSV", TextIO.write()
                        .to(this.outputPath));
    }
}
