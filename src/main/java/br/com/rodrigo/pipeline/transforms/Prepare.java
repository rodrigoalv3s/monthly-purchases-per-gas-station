package br.com.rodrigo.pipeline.transforms;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.schemas.transforms.AddFields;
import org.apache.beam.sdk.schemas.transforms.Select;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.Row;

public class Prepare extends PTransform<PCollection<String>, PCollection<Row>> {
    static final Schema schema = Schema
            .builder()
            .addInt32Field("gasStationId")
            .addStringField("yearMonth")
            .addDoubleField("price")
            .build();

    @Override
    public PCollection<Row> expand(PCollection<String> input) {
        return input
                .apply("ParseCSVtoTransactions", new ConvertToTransactions())
                .apply("SelectFields", Select.fieldNames("gasStationId", "date", "price"))
                .apply("CreateMonthField",
                        AddFields.<Row>create()
                                .field("yearMonth", Schema.FieldType.STRING))
                .apply("ConvertDateFormat", new ConvertToMonthDate()).setRowSchema(schema)
                ;
    }
}
