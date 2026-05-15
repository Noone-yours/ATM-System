package com.atm.auth;

import com.atm.core.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean verifyPin(String accountNumber, String pin) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(account -> passwordEncoder.matches(pin, account.getPin()))
                .orElse(false);
    }
}
