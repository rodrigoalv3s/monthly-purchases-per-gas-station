package br.com.rodrigo.pipeline.transforms;

import org.apache.beam.sdk.schemas.transforms.Select;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;

public class ConvertToText extends PTransform<PCollection<Row>, PCollection<String>> {

    @Override
    public PCollection<String> expand(PCollection<Row> input) {
        PCollection<String> transactions = input
                .apply("UnnestFields", Select.fieldNames("key.gasStationId", "key.yearMonth", "value.sumTransactions"))
                .apply("ConvertRowToText", ParDo.of(
                        new ConvertToTextFun()
                ));

        return transactions;
    }

    public static class ConvertToTextFun extends DoFn<Row, String> {
        @ProcessElement
        public void processElement(@Element Row row, OutputReceiver<String> sink) {
            String line = "";

            line = row.getValue("gasStationId").toString() + "," +
                    row.getValue("yearMonth").toString() + "," +
                    row.getValue("sumTransactions");

            sink.output(line);
        }
    }
}
