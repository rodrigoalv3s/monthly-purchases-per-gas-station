package br.com.rodrigo.pipeline.transforms;

import br.com.rodrigo.pipeline.schemas.TransactionPerMonth;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;

import java.text.ParseException;

public class ConvertToMonthDate extends PTransform<PCollection<Row>, PCollection<Row>> {
    @Override
    public PCollection<Row> expand(PCollection<Row> input) {
        return input.apply("ConvertDateToMonth", ParDo.of(new ConvertToMonthDateFun()));
    }

    public class ConvertToMonthDateFun extends DoFn<Row, Row> {
        @ProcessElement
        public void processElement(@Element Row row, OutputReceiver<Row> sink) throws ParseException {
            String date = row.getValue("date").toString().substring(0, 7);

            Row newRow = Row.withSchema(TransactionPerMonth.getSchema())
                    .addValues(row.getInt32("gasStationId")
                            , date
                            , row.getDouble("price"))
                    .build();

            sink.output(newRow);
        }
    }
}
