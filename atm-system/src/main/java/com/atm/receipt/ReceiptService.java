package com.atm.receipt;

import com.atm.core.model.TransactionEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptService {
    public String generateReceipt(String accountNumber, String transactionType, BigDecimal amount, BigDecimal newBalance) {
        return String.format(
            "--- ATM RECEIPT ---\n" +
            "Date: %s\n" +
            "Account: %s\n" +
            "Type: %s\n" +
            "Amount: $%s\n" +
            "Balance: $%s\n" +
            "-------------------",
            LocalDateTime.now(), accountNumber, transactionType, amount, newBalance
        );
    }

    public String generateMiniStatement(String accountNumber, List<TransactionEntity> transactions) {
        String history = transactions.stream()
            .limit(5)
            .map(t -> String.format("%s | %-10s | $%s", 
                t.getTimestamp().toLocalDate(), 
                t.getTransactionType(), 
                t.getAmount()))
            .collect(Collectors.joining("\n"));

        return String.format(
            "--- MINI STATEMENT ---\n" +
            "Account: %s\n" +
            "Date: %s\n" +
            "----------------------\n" +
            "%s\n" +
            "----------------------",
            accountNumber, LocalDateTime.now().toLocalDate(), history
        );
    }
}
