package com.aierken.aierken_practice.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private Long fromId;
    private Long toId;
    private Double amount;
}
