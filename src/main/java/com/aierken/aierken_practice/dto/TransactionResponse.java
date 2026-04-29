package com.aierken.aierken_practice.dto;

import com.aierken.aierken_practice.entity.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private TransactionType type;
    private double amount;
    private double balanceBefore;
    private double balanceAfter;
    private LocalDateTime createdAt;

    public TransactionResponse(Long id, TransactionType type, double amount, double balanceBefore, double balanceAfter, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
    }
}