package com.atm.core.repository;

// Piliin mo lang ang ISA dito depende kung nasaan talaga yung AccountEntity file mo:
import com.atm.core.entity.AccountEntity; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}