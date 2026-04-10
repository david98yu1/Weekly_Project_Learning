package com.aierken.aierken_practice.entity;
import java.util.*;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;
    private String status;
    private List<Account> accounts;
}
