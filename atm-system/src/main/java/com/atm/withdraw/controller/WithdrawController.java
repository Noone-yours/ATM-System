package com.atm.withdraw.controller;

import com.atm.withdraw.dto.WithdrawRequest;
import com.atm.withdraw.dto.WithdrawResponse;
import com.atm.withdraw.service.WithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/withdraw")
@RequiredArgsConstructor
public class WithdrawController {

    private final WithdrawService withdrawService;

    @PostMapping
    public ResponseEntity<WithdrawResponse> withdraw(@RequestBody WithdrawRequest request) {
        try {
            WithdrawResponse response = withdrawService.withdraw(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(WithdrawResponse.builder()
                    .status("FAILED")
                    .message(e.getMessage())
                    .build());
        }
    }
}
