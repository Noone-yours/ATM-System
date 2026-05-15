package com.atm.core.repository;

// 1. Piliin ang tamang folder (base sa AccountRepository kanina, kung .entity o .model)
import com.atm.core.entity.TransactionEntity; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // 2. Siguraduhing kasama ito para sa List

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    // 3. Panatilihin ang method na ito dahil kailangan ito para sa history/logs
    List<TransactionEntity> findByAccountAccountNumberOrderByTimestampDesc(String accountNumber);
}