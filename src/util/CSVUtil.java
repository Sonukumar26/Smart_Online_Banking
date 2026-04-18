package util;

import model.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVUtil {

    public static String generateTransactionCSV(List<Transaction> list)
            throws IOException {

        String filePath =
                System.getProperty("java.io.tmpdir") + "transactions.csv";

        FileWriter writer = new FileWriter(filePath);

        // Header
        writer.append("Transaction ID,Account No,Type,Amount,Date\n");

        // Data rows
        for (Transaction t : list) {
            writer.append(t.getTxnId() + ",")
                  .append(String.valueOf(t.getAccNo())).append(",")
                  .append(t.getTxnType()).append(",")
                  .append(String.valueOf(t.getAmount())).append(",")
                  .append(String.valueOf(t.getTxnTime()))
                  .append("\n");
        }

        writer.flush();
        writer.close();

        return filePath;
    }
}
