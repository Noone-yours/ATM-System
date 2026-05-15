package com.atm.core;

import com.atm.core.model.TransactionEntity;
import com.atm.core.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
    private final TransactionRepository transactionRepository;

    public List<TransactionEntity> getHistory(String accountNumber) {
        return transactionRepository.findByAccountAccountNumberOrderByTimestampDesc(accountNumber);
    }
}
