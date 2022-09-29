package br.com.rodrigo.pipeline.transforms;

import br.com.rodrigo.pipeline.schemas.Transaction;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertToTransactions extends PTransform<PCollection<String>, PCollection<Transaction>> {

    @Override
    public PCollection<Transaction> expand(PCollection<String> input) {

        PCollection<Transaction> transactions
                = input.apply("ConvertToTransactions", ParDo.of(new ConvertToTransactionsFun()));
        return transactions;
    }

    public static class ConvertToTransactionsFun extends DoFn<String, Transaction> {
        @ProcessElement
        public void processElement(@Element String csv, OutputReceiver<Transaction> transaction) throws ParseException {
            String[] line = csv.split(",");

            if (!line[0].equals("\"TransactionID\"")) {
                int transactionId = Integer.valueOf(line[0]);
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdfDate.parse(line[1]);
                String time = line[2].replace("\"", "");
                int customerId = Integer.valueOf(line[3]);
                int cardId = Integer.valueOf(line[4]);
                int gasStationId = Integer.valueOf(line[5]);
                int productId = Integer.valueOf(line[6]);
                int amount = Integer.valueOf(line[7]);
                Double price = Double.valueOf(line[8]);

                transaction.output(Transaction.create(transactionId, line[1], time, customerId, cardId, gasStationId, productId, amount, price));
            }
        }
    }
}
