package br.com.rodrigo.pipeline.transforms;

import org.apache.beam.sdk.schemas.transforms.Group;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.Sum;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;

public class Aggregate extends PTransform<PCollection<Row>, PCollection<Row>> {
    @Override
    public PCollection<Row> expand(PCollection<Row> input) {
        return input.apply("GroupByGasStationId",
                Group.<Row>byFieldNames("gasStationId","yearMonth")
                .aggregateField("price", Sum.ofDoubles(), "sumTransactions"));
    }
}
