package com.atm.deposit;

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
public class DepositService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void deposit(String accountNumber, BigDecimal amount) {
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        TransactionEntity transaction = TransactionEntity.builder()
                .account(account)
                .transactionType("DEPOSIT")
                .amount(amount)
                .timestamp(java.time.LocalDateTime.now())
                .description("Cash deposit")
                .build();
        transactionRepository.save(transaction);
    }
}
