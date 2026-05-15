package com.atm.withdraw.service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.atm.core.entity.AccountEntity;
import com.atm.core.repository.AccountRepository;
import com.atm.core.repository.TransactionRepository;
import com.atm.withdraw.dto.WithdrawRequest;
import com.atm.withdraw.dto.WithdrawResponse;

@ExtendWith(MockitoExtension.class)
public class WithdrawServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private WithdrawService withdrawService;

    private AccountEntity account;

    @BeforeEach
    void setUp() {
        account = AccountEntity.builder()
                .accountNumber("1234567890")
                .balance(new BigDecimal("1000.00"))
                .dailyLimit(new BigDecimal("500.00"))
                .remainingDailyLimit(new BigDecimal("500.00"))
                .build();
    }

    @Test
    void withdraw_Success() {
        WithdrawRequest request = new WithdrawRequest("1234567890", new BigDecimal("200.00"));
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(account));

        WithdrawResponse response = withdrawService.withdraw(request);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals(new BigDecimal("800.00"), response.getRemainingBalance());
        assertEquals(new BigDecimal("300.00"), account.getRemainingDailyLimit());
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    void withdraw_InsufficientBalance() {
        WithdrawRequest request = new WithdrawRequest("1234567890", new BigDecimal("1200.00"));
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(account));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> withdrawService.withdraw(request));
        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void withdraw_ExceedDailyLimit() {
        WithdrawRequest request = new WithdrawRequest("1234567890", new BigDecimal("600.00"));
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(account));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> withdrawService.withdraw(request));
        assertTrue(exception.getMessage().contains("Daily withdrawal limit exceeded"));
    }

    @Test
    void withdraw_NotMultipleOf100() {
        WithdrawRequest request = new WithdrawRequest("1234567890", new BigDecimal("150.00"));
        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(account));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> withdrawService.withdraw(request));
        assertEquals("Withdrawal amount must be a multiple of 100", exception.getMessage());
    }
}
