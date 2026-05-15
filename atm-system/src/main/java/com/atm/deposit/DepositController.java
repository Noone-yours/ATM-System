package com.atm.deposit;

import com.atm.withdraw.dto.TransactionRequest;
import com.atm.receipt.ReceiptService;
import com.atm.core.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
public class DepositController {
    private final DepositService depositService;
    private final AccountRepository accountRepository;
    private final ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<String> deposit(@RequestBody TransactionRequest request) {
        try {
            depositService.deposit(request.getAccountNumber(), request.getAmount());
            var account = accountRepository.findByAccountNumber(request.getAccountNumber()).orElseThrow();
            String receipt = receiptService.generateReceipt(request.getAccountNumber(), "DEPOSIT", request.getAmount(), account.getBalance());
            return ResponseEntity.ok(receipt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
