package br.com.rodrigo.pipeline;

import br.com.rodrigo.pipeline.options.GasStationOptions;
import br.com.rodrigo.pipeline.transforms.Aggregate;
import br.com.rodrigo.pipeline.transforms.Prepare;
import br.com.rodrigo.pipeline.transforms.Sink;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A starter example for writing Beam programs.
 *
 * <p>The example takes two strings, converts them to their upper-case
 * representation and logs them.
 *
 * <p>To run this starter example locally using DirectRunner, just
 * execute it without any additional parameters from your favorite development
 * environment.
 *
 * <p>To run this starter example using managed resource in Google Cloud
 * Platform, you should specify the following command-line options:
 *   --project=<YOUR_PROJECT_ID>
 *   --stagingLocation=<STAGING_LOCATION_IN_CLOUD_STORAGE>
 *   --runner=DataflowRunner
 */
public class MonthlyPurchasesPerGasStation {
  private static final Logger LOG = LoggerFactory.getLogger(MonthlyPurchasesPerGasStation.class);

  public static void main(String[] args) {

    PipelineOptionsFactory.register(GasStationOptions.class);

    GasStationOptions options = PipelineOptionsFactory
            .fromArgs(args).withValidation()
            .as(GasStationOptions.class);

    Pipeline p = Pipeline.create(options);

    p.apply("ReadFile", TextIO.read()
                    .from(options.getInputPath() + options.getFileName()))
            .apply("Prepare", new Prepare())
            .apply("Aggregate", new Aggregate())
            .apply("Sink", new Sink(options.getOutputPath()))
    ;

    p.run();
  }
}
