package com.atm.withdraw;

import com.atm.core.model.AccountEntity;
import com.atm.core.model.TransactionEntity;
import com.atm.core.repository.AccountRepository;
import com.atm.core.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WithdrawService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void withdraw(String accountNumber, BigDecimal amount) {
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        TransactionEntity transaction = TransactionEntity.builder()
                .account(account)
                .transactionType("WITHDRAWAL")
                .amount(amount)
                .timestamp(java.time.LocalDateTime.now())
                .description("Cash withdrawal")
                .build();
        transactionRepository.save(transaction);
    }
}
