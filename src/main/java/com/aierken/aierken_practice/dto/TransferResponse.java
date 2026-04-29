package com.aierken.aierken_practice.dto;

import lombok.Data;

@Data
public class TransferResponse {
    private String fromAccountNumber;
    private String toAccountNumber;
    private double amount;
    private String status;

    public TransferResponse(String fromAccountNumber, String toAccountNumber, double amount, String status) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.status = status;
    }
}