package br.com.rodrigo.pipeline.schemas;

import javax.annotation.processing.Generated;
import org.apache.beam.sdk.schemas.annotations.SchemaFieldName;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_Transaction extends Transaction {

  private final int transactionId;

  private final String date;

  private final String time;

  private final int customerId;

  private final int cardId;

  private final int gasStationId;

  private final int productId;

  private final int amount;

  private final Double price;

  AutoValue_Transaction(
      int transactionId,
      String date,
      String time,
      int customerId,
      int cardId,
      int gasStationId,
      int productId,
      int amount,
      Double price) {
    this.transactionId = transactionId;
    if (date == null) {
      throw new NullPointerException("Null date");
    }
    this.date = date;
    if (time == null) {
      throw new NullPointerException("Null time");
    }
    this.time = time;
    this.customerId = customerId;
    this.cardId = cardId;
    this.gasStationId = gasStationId;
    this.productId = productId;
    this.amount = amount;
    if (price == null) {
      throw new NullPointerException("Null price");
    }
    this.price = price;
  }

  @SchemaFieldName("transactionId")
  @Override
  int getTransactionId() {
    return transactionId;
  }

  @SchemaFieldName("date")
  @Override
  String getDate() {
    return date;
  }

  @SchemaFieldName("time")
  @Override
  String getTime() {
    return time;
  }

  @SchemaFieldName("customerId")
  @Override
  int getCustomerId() {
    return customerId;
  }

  @SchemaFieldName("cardId")
  @Override
  int getCardId() {
    return cardId;
  }

  @SchemaFieldName("gasStationId")
  @Override
  int getGasStationId() {
    return gasStationId;
  }

  @SchemaFieldName("productId")
  @Override
  int getProductId() {
    return productId;
  }

  @SchemaFieldName("amount")
  @Override
  int getAmount() {
    return amount;
  }

  @SchemaFieldName("price")
  @Override
  Double getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "Transaction{"
        + "transactionId=" + transactionId + ", "
        + "date=" + date + ", "
        + "time=" + time + ", "
        + "customerId=" + customerId + ", "
        + "cardId=" + cardId + ", "
        + "gasStationId=" + gasStationId + ", "
        + "productId=" + productId + ", "
        + "amount=" + amount + ", "
        + "price=" + price
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Transaction) {
      Transaction that = (Transaction) o;
      return this.transactionId == that.getTransactionId()
          && this.date.equals(that.getDate())
          && this.time.equals(that.getTime())
          && this.customerId == that.getCustomerId()
          && this.cardId == that.getCardId()
          && this.gasStationId == that.getGasStationId()
          && this.productId == that.getProductId()
          && this.amount == that.getAmount()
          && this.price.equals(that.getPrice());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= transactionId;
    h$ *= 1000003;
    h$ ^= date.hashCode();
    h$ *= 1000003;
    h$ ^= time.hashCode();
    h$ *= 1000003;
    h$ ^= customerId;
    h$ *= 1000003;
    h$ ^= cardId;
    h$ *= 1000003;
    h$ ^= gasStationId;
    h$ *= 1000003;
    h$ ^= productId;
    h$ *= 1000003;
    h$ ^= amount;
    h$ *= 1000003;
    h$ ^= price.hashCode();
    return h$;
  }

}
