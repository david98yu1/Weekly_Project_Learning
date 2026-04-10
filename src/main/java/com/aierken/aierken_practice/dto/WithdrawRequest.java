package com.aierken.aierken_practice.dto;

import lombok.Data;

@Data
public class WithdrawRequest {
    private Long userId;
    private long accountId;
    private double amount;
}
