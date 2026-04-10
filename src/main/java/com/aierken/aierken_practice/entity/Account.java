package com.aierken.aierken_practice.entity;
import lombok.Data;

@Data
public class Account {
    private Long id;
    private Long userId;
    private String accountNumber;
    private String accountType;
    private double balance;
    private boolean isActive;

}
