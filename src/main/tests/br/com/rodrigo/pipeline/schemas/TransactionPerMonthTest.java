package br.com.rodrigo.pipeline.schemas;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.schemas.Schema.FieldType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TransactionPerMonthTest {
    @Test
    public void testGetTransactionSchema() {
        Schema schema = TransactionPerMonth.getSchema();

        assertEquals(schema.getFieldCount(), 3);
        assertEquals(schema.getField("gasStationId").getType(), FieldType.INT32);
        assertEquals(schema.getField("yearMonth").getType(), FieldType.STRING);
        assertEquals(schema.getField("price").getType(), FieldType.DOUBLE);

        TransactionPerMonth transactionPerMonth1 = TransactionPerMonth.create(
                1, "2022-09", 198.89);
        TransactionPerMonth transactionPerMonth2 = TransactionPerMonth.create(
                2, "2022-10", 278.15);
        TransactionPerMonth transactionPerMonth3 = TransactionPerMonth.create(
                3, "2022-11", 1.99);
        TransactionPerMonth transactionPerMonth4 = TransactionPerMonth.create(
                3, "2022-11", 1.99);

        assertEquals(transactionPerMonth1.getGasStationId(), 1);
        assertEquals(transactionPerMonth1.getYearMonth(), "2022-09");
        assertEquals(Optional.ofNullable(transactionPerMonth1.getPrice()), Optional.ofNullable(198.89));
        assertEquals(transactionPerMonth1.toString(), "TransactionPerMonth{"
                + "gasStationId=1, "
                + "yearMonth=2022-09, "
                + "price=198.89}");

        assertEquals(transactionPerMonth3, transactionPerMonth4);
        assertNotEquals(transactionPerMonth1, transactionPerMonth2);
        assertEquals(transactionPerMonth1, transactionPerMonth1);
        assertNotEquals(transactionPerMonth3, new Object());

        assertThrows(NullPointerException.class, () -> {
            TransactionPerMonth.create(1, null, 198.89);
        });

        assertThrows(NullPointerException.class, () -> {
            TransactionPerMonth.create(1, "2022-09", null);
        });

        assertEquals(transactionPerMonth2.hashCode(), 1332240935);
    }
}
