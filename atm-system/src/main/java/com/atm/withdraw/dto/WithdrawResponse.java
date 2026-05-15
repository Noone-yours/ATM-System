package com.atm.withdraw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawResponse {
    private String accountNumber;
    private BigDecimal amountWithdrawn;
    private BigDecimal remainingBalance;
    private LocalDateTime timestamp;
    private String status;
    private String message;
}
