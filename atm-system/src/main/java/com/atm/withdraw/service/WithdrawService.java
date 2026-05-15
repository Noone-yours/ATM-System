package com.atm.withdraw.service;

import com.atm.core.entity.AccountEntity;
import com.atm.core.entity.TransactionEntity;
import com.atm.core.repository.AccountRepository;
import com.atm.core.repository.TransactionRepository;
import com.atm.withdraw.dto.WithdrawRequest;
import com.atm.withdraw.dto.WithdrawResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WithdrawService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public WithdrawResponse withdraw(WithdrawRequest request) {
        AccountEntity account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        validateWithdrawal(account, request.getAmount());

        // Update balance and limits
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        account.setRemainingDailyLimit(account.getRemainingDailyLimit().subtract(request.getAmount()));
        accountRepository.save(account);

        // Record transaction
        TransactionEntity transaction = TransactionEntity.builder()
                .account(account)
                .amount(request.getAmount())
                .type(TransactionEntity.TransactionType.WITHDRAWAL)
                .timestamp(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);

        return WithdrawResponse.builder()
                .accountNumber(account.getAccountNumber())
                .amountWithdrawn(request.getAmount())
                .remainingBalance(account.getBalance())
                .timestamp(LocalDateTime.now())
                .status("SUCCESS")
                .message("Withdrawal successful")
                .build();
    }

    private void validateWithdrawal(AccountEntity account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Withdrawal amount must be positive");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        if (account.getRemainingDailyLimit().compareTo(amount) < 0) {
            throw new RuntimeException("Daily withdrawal limit exceeded. Remaining limit: " + account.getRemainingDailyLimit());
        }
        
        // Optional: ATM specific validation (e.g. multiple of 100)
        if (amount.remainder(new BigDecimal("100")).compareTo(BigDecimal.ZERO) != 0) {
            throw new RuntimeException("Withdrawal amount must be a multiple of 100");
        }
    }
}
