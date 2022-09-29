package br.com.rodrigo.pipeline.schemas;

import javax.annotation.processing.Generated;
import org.apache.beam.sdk.schemas.annotations.SchemaFieldName;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_TransactionPerMonth extends TransactionPerMonth {

  private final int gasStationId;

  private final String yearMonth;

  private final Double price;

  AutoValue_TransactionPerMonth(
      int gasStationId,
      String yearMonth,
      Double price) {
    this.gasStationId = gasStationId;
    if (yearMonth == null) {
      throw new NullPointerException("Null yearMonth");
    }
    this.yearMonth = yearMonth;
    if (price == null) {
      throw new NullPointerException("Null price");
    }
    this.price = price;
  }

  @SchemaFieldName("gasStationId")
  @Override
  int getGasStationId() {
    return gasStationId;
  }

  @SchemaFieldName("yearMonth")
  @Override
  String getYearMonth() {
    return yearMonth;
  }

  @SchemaFieldName("price")
  @Override
  Double getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "TransactionPerMonth{"
        + "gasStationId=" + gasStationId + ", "
        + "yearMonth=" + yearMonth + ", "
        + "price=" + price
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof TransactionPerMonth) {
      TransactionPerMonth that = (TransactionPerMonth) o;
      return this.gasStationId == that.getGasStationId()
          && this.yearMonth.equals(that.getYearMonth())
          && this.price.equals(that.getPrice());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= gasStationId;
    h$ *= 1000003;
    h$ ^= yearMonth.hashCode();
    h$ *= 1000003;
    h$ ^= price.hashCode();
    return h$;
  }

}
