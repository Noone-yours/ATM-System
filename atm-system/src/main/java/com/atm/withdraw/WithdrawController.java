package com.atm.withdraw;

import com.atm.withdraw.dto.TransactionRequest;
import com.atm.receipt.ReceiptService;
import com.atm.core.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/withdraw")
@RequiredArgsConstructor
public class WithdrawController {
    private final WithdrawService withdrawService;
    private final AccountRepository accountRepository;
    private final ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<String> withdraw(@RequestBody TransactionRequest request) {
        try {
            withdrawService.withdraw(request.getAccountNumber(), request.getAmount());
            var account = accountRepository.findByAccountNumber(request.getAccountNumber()).orElseThrow();
            String receipt = receiptService.generateReceipt(request.getAccountNumber(), "WITHDRAWAL", request.getAmount(), account.getBalance());
            return ResponseEntity.ok(receipt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
