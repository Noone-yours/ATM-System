package com.atm;

import com.atm.core.model.AccountEntity;
import com.atm.core.model.TransactionEntity;
import com.atm.core.repository.AccountRepository;
import com.atm.core.repository.TransactionRepository;
import com.atm.withdraw.WithdrawService;
import com.atm.deposit.DepositService;
import com.atm.receipt.ReceiptService;
import com.atm.auth.AuthService;
import com.atm.core.TransactionHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AtmApplicationTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private TransactionHistoryService historyService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String TEST_ACCOUNT = "123456789";
    private final String TEST_PIN = "1234";

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        AccountEntity account = AccountEntity.builder()
                .accountNumber(TEST_ACCOUNT)
                .pin(passwordEncoder.encode(TEST_PIN))
                .balance(new BigDecimal("1000.00"))
                .accountHolderName("Test User")
                .build();
        accountRepository.save(account);
    }

    @Test
    void testCorrectPinAndWithdraw() {
        // Verify PIN
        assertTrue(authService.verifyPin(TEST_ACCOUNT, TEST_PIN));

        // Withdraw
        withdrawService.withdraw(TEST_ACCOUNT, new BigDecimal("200.00"));

        // Check Balance
        AccountEntity account = accountRepository.findByAccountNumber(TEST_ACCOUNT).orElseThrow();
        assertEquals(new BigDecimal("800.00"), account.getBalance());

        // Verify Transaction History
        List<TransactionEntity> history = historyService.getHistory(TEST_ACCOUNT);
        assertEquals(1, history.size());
        assertEquals("WITHDRAWAL", history.get(0).getTransactionType());
        assertEquals(new BigDecimal("200.00"), history.get(0).getAmount());
    }

    @Test
    void testInsufficientBalance() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            withdrawService.withdraw(TEST_ACCOUNT, new BigDecimal("1500.00"));
        });

        assertEquals("Insufficient balance", exception.getMessage());
        
        // Balance should remain unchanged
        AccountEntity account = accountRepository.findByAccountNumber(TEST_ACCOUNT).orElseThrow();
        assertEquals(new BigDecimal("1000.00"), account.getBalance());

        // No transaction should be recorded
        List<TransactionEntity> history = historyService.getHistory(TEST_ACCOUNT);
        assertTrue(history.isEmpty());
    }

    @Test
    void testDepositAndReceipt() {
        // Deposit
        depositService.deposit(TEST_ACCOUNT, new BigDecimal("500.00"));

        // Check Balance
        AccountEntity account = accountRepository.findByAccountNumber(TEST_ACCOUNT).orElseThrow();
        assertEquals(new BigDecimal("1500.00"), account.getBalance());

        // Verify Transaction History
        List<TransactionEntity> history = historyService.getHistory(TEST_ACCOUNT);
        assertEquals(1, history.size());
        assertEquals("DEPOSIT", history.get(0).getTransactionType());

        // Generate Receipt
        String receipt = receiptService.generateReceipt(TEST_ACCOUNT, "DEPOSIT", new BigDecimal("500.00"), account.getBalance());
        assertNotNull(receipt);
        assertTrue(receipt.contains("DEPOSIT"));
        assertTrue(receipt.contains("$500.00"));
        assertTrue(receipt.contains("$1500.00"));
    }

    @Test
    void testMiniStatement() {
        // Perform multiple transactions
        depositService.deposit(TEST_ACCOUNT, new BigDecimal("100.00"));
        withdrawService.withdraw(TEST_ACCOUNT, new BigDecimal("50.00"));
        depositService.deposit(TEST_ACCOUNT, new BigDecimal("200.00"));

        // Get History
        List<TransactionEntity> history = historyService.getHistory(TEST_ACCOUNT);

        // Generate Mini-Statement
        String miniStatement = receiptService.generateMiniStatement(TEST_ACCOUNT, history);

        assertNotNull(miniStatement);
        assertTrue(miniStatement.contains("MINI STATEMENT"));
        assertTrue(miniStatement.contains("DEPOSIT"));
        assertTrue(miniStatement.contains("WITHDRAWAL"));
        assertTrue(miniStatement.contains("$100.00"));
        assertTrue(miniStatement.contains("$50.00"));
        assertTrue(miniStatement.contains("$200.00"));
    }
}
