package br.com.rodrigo.pipeline.options;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Validation.Required;

public interface GasStationOptions extends PipelineOptions {
    @Description("Input file path")
    @Required
    String getInputPath();
    void setInputPath(String inputPath);

    @Description("Input file name")
    @Required
    String getFileName();
    void setFileName(String fileName);

    @Description("Output file path")
    @Required
    String getOutputPath();
    void setOutputPath(String outputPath);
}
