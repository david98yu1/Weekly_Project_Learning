package com.aierken.aierken_practice.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private String fromId;
    private String toId;
    private Double amount;
}
