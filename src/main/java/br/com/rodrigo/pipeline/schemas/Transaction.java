package br.com.rodrigo.pipeline.schemas;

import com.google.auto.value.AutoValue;
import org.apache.beam.sdk.schemas.AutoValueSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;
import org.apache.beam.sdk.schemas.annotations.SchemaCreate;
import org.apache.beam.sdk.schemas.annotations.SchemaFieldName;

@AutoValue
@DefaultSchema(AutoValueSchema.class)
public abstract class Transaction {
    @SchemaFieldName("transactionId")
    abstract int getTransactionId();
    @SchemaFieldName("date")
    abstract String getDate();
    @SchemaFieldName("time")
    abstract String getTime();
    @SchemaFieldName("customerId")
    abstract int getCustomerId();
    @SchemaFieldName("cardId")
    abstract int getCardId();
    @SchemaFieldName("gasStationId")
    abstract int getGasStationId();
    @SchemaFieldName("productId")
    abstract int getProductId();
    @SchemaFieldName("amount")
    abstract int getAmount();
    @SchemaFieldName("price")
    abstract Double getPrice();

    @SchemaCreate
    public static Transaction create(int transactionId, String date, String time, int customerId,
                                     int cardId, int gasStationId, int productId, int amount, Double price) {
        return new AutoValue_Transaction(transactionId, date, time, customerId, cardId, gasStationId, productId, amount, price);
    }
}
