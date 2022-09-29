package br.com.rodrigo.pipeline.schemas;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

@RunWith(JUnit4.class)
public class TransactionTest {
    @Test
    public void testCreateTransactionSchema() {
        Transaction transaction = Transaction.create(1,
                "2022-09-27",
                "15:39",
                2,
                3,
                4,
                5,
                1000,
                10.18);

        Transaction transaction2 = Transaction.create(2,
                "2022-09-28",
                "15:19",
                3,
                4,
                8,
                2115,
                10,
                59.59);

        Transaction transaction3 = Transaction.create(1,
                "2022-09-27",
                "15:39",
                2,
                3,
                4,
                5,
                1000,
                10.18);

        assertEquals(transaction.getTransactionId(), 1);
        assertEquals(transaction.getDate(), "2022-09-27");
        assertEquals(transaction.getTime(), "15:39");
        assertEquals(transaction.getCustomerId(), 2);
        assertEquals(transaction.getCardId(), 3);
        assertEquals(transaction.getGasStationId(), 4);
        assertEquals(transaction.getProductId(), 5);
        assertEquals(transaction.getAmount(), 1000);
        assertEquals(Optional.ofNullable(transaction.getPrice()), Optional.ofNullable(10.18));

        assertEquals(transaction, transaction);
        assertEquals(transaction, transaction3);

        assertNotEquals(transaction, transaction2);

        assertEquals(transaction.toString(), "Transaction{"
                + "transactionId=1, "
                + "date=2022-09-27, "
                + "time=15:39, "
                + "customerId=2, "
                + "cardId=3, "
                + "gasStationId=4, "
                + "productId=5, "
                + "amount=1000, "
                + "price=10.18}");

        assertThrows(NullPointerException.class, () -> {
            Transaction.create(1,
                null,
                "15:39",
                2,
                3,
                4,
                5,
                1000,
                10.18);
        });

        assertThrows(NullPointerException.class, () -> {
            Transaction.create(1,
                    "2022-09-27",
                    null,
                    2,
                    3,
                    4,
                    5,
                    1000,
                    10.18);
        });

        assertThrows(NullPointerException.class, () -> {
            Transaction.create(1,
                    "2022-09-27",
                    "15:39",
                    2,
                    3,
                    4,
                    5,
                    1000,
                    null);
        });

        assertNotEquals(transaction, new Object());
    }
}
