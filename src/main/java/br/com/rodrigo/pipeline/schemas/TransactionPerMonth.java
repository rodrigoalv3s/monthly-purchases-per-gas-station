package br.com.rodrigo.pipeline.schemas;

import com.google.auto.value.AutoValue;
import org.apache.beam.sdk.schemas.JavaFieldSchema;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.schemas.Schema.Field;
import org.apache.beam.sdk.schemas.Schema.FieldType;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;
import org.apache.beam.sdk.schemas.annotations.SchemaCreate;
import org.apache.beam.sdk.schemas.annotations.SchemaFieldName;

@AutoValue
@DefaultSchema(JavaFieldSchema.class)
public abstract class TransactionPerMonth {

    @SchemaFieldName("gasStationId")
    abstract int getGasStationId();
    @SchemaFieldName("yearMonth")
    abstract String getYearMonth();
    @SchemaFieldName("price")
    abstract Double getPrice();

    @SchemaCreate
    public static TransactionPerMonth create(int gasStationId, String yearMonth, Double price) {
        return new AutoValue_TransactionPerMonth(gasStationId, yearMonth, price);
    }

    public static Schema getSchema() {
        Schema schema = Schema.of(
                Field.of("gasStationId", FieldType.INT32),
                Field.of("yearMonth", FieldType.STRING),
                Field.of("price", FieldType.DOUBLE));
        return schema;
    }
}
