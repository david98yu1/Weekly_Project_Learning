package com.aierken.aierken_practice.config;
import com.aierken.aierken_practice.entity.Account;
import com.aierken.aierken_practice.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;



@Configuration
public class StoreConfig {
    @Bean
    public Map<Long, User> userStore() {
        Map<Long, User> userStore = new HashMap<>();
        Account a1 = new Account();
        a1.setId(101L);
        a1.setUserId(1L);
        a1.setAccountNumber("ACC-1001");
        a1.setAccountType("CHECKING");
        a1.setBalance(500.0);
        a1.setActive(true);

        Account a2 = new Account();
        a2.setId(102L);
        a2.setUserId(1L);
        a2.setAccountNumber("ACC-1002");
        a2.setAccountType("SAVINGS");
        a2.setBalance(1500.0);
        a2.setActive(true);

        Account a3 = new Account();
        a3.setId(103L);
        a3.setUserId(1L);
        a3.setAccountNumber("ACC-1003");
        a3.setAccountType("CHECKING");
        a3.setBalance(2500.0);
        a3.setActive(false);

        User user1 = new User();
        user1.setId(1L);
        user1.setName("Alice Johnson");
        user1.setEmail("alice@example.com");
        user1.setPassword("alice123");
        user1.setPhone("408-111-1111");
        user1.setAddress("101 Main St, San Jose, CA");
        user1.setRole("CUSTOMER");
        user1.setStatus("ACTIVE");
        user1.setAccounts(Arrays.asList(a1, a2, a3));

        // ========== User 2 ==========
        Account b1 = new Account();
        b1.setId(201L);
        b1.setUserId(2L);
        b1.setAccountNumber("ACC-2001");
        b1.setAccountType("SAVINGS");
        b1.setBalance(800.0);
        b1.setActive(true);

        Account b2 = new Account();
        b2.setId(202L);
        b2.setUserId(2L);
        b2.setAccountNumber("ACC-2002");
        b2.setAccountType("CHECKING");
        b2.setBalance(2200.0);
        b2.setActive(true);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Bob Smith");
        user2.setEmail("bob@example.com");
        user2.setPassword("bob123");
        user2.setPhone("408-222-2222");
        user2.setAddress("202 Market St, Santa Clara, CA");
        user2.setRole("CUSTOMER");
        user2.setStatus("ACTIVE");
        user2.setAccounts(Arrays.asList(b1, b2));

        // ========== User 3 ==========
        Account c1 = new Account();
        c1.setId(301L);
        c1.setUserId(3L);
        c1.setAccountNumber("ACC-3001");
        c1.setAccountType("SAVINGS");
        c1.setBalance(3000.0);
        c1.setActive(true);

        Account c2 = new Account();
        c2.setId(302L);
        c2.setUserId(3L);
        c2.setAccountNumber("ACC-3002");
        c2.setAccountType("CHECKING");
        c2.setBalance(200.0);
        c2.setActive(false);

        Account c3 = new Account();
        c3.setId(303L);
        c3.setUserId(3L);
        c3.setAccountNumber("ACC-3003");
        c3.setAccountType("SAVINGS");
        c3.setBalance(1100.0);
        c3.setActive(true);

        User user3 = new User();
        user3.setId(3L);
        user3.setName("Charlie Brown");
        user3.setEmail("charlie@example.com");
        user3.setPassword("charlie123");
        user3.setPhone("408-333-3333");
        user3.setAddress("303 El Camino Real, Sunnyvale, CA");
        user3.setRole("CUSTOMER");
        user3.setStatus("INACTIVE");
        user3.setAccounts(Arrays.asList(c1, c2, c3));

        userStore.put(user1.getId(), user1);
        userStore.put(user2.getId(), user2);
        userStore.put(user3.getId(), user3);

        return userStore;
    }

    @Bean
    public Map<Long, Account> accountStore() {
        Map<Long, Account> accountStore = new HashMap<>();

        Account a1 = new Account();
        a1.setId(101L);
        a1.setUserId(1L);
        a1.setAccountNumber("ACC-1001");
        a1.setAccountType("CHECKING");
        a1.setBalance(500.0);
        a1.setActive(true);

        Account a2 = new Account();
        a2.setId(102L);
        a2.setUserId(1L);
        a2.setAccountNumber("ACC-1002");
        a2.setAccountType("SAVINGS");
        a2.setBalance(1500.0);
        a2.setActive(true);

        Account a3 = new Account();
        a3.setId(103L);
        a3.setUserId(1L);
        a3.setAccountNumber("ACC-1003");
        a3.setAccountType("CHECKING");
        a3.setBalance(2500.0);
        a3.setActive(false);

        Account b1 = new Account();
        b1.setId(201L);
        b1.setUserId(2L);
        b1.setAccountNumber("ACC-2001");
        b1.setAccountType("SAVINGS");
        b1.setBalance(800.0);
        b1.setActive(true);

        Account b2 = new Account();
        b2.setId(202L);
        b2.setUserId(2L);
        b2.setAccountNumber("ACC-2002");
        b2.setAccountType("CHECKING");
        b2.setBalance(2200.0);
        b2.setActive(true);

        Account c1 = new Account();
        c1.setId(301L);
        c1.setUserId(3L);
        c1.setAccountNumber("ACC-3001");
        c1.setAccountType("SAVINGS");
        c1.setBalance(3000.0);
        c1.setActive(true);

        Account c2 = new Account();
        c2.setId(302L);
        c2.setUserId(3L);
        c2.setAccountNumber("ACC-3002");
        c2.setAccountType("CHECKING");
        c2.setBalance(200.0);
        c2.setActive(false);

        Account c3 = new Account();
        c3.setId(303L);
        c3.setUserId(3L);
        c3.setAccountNumber("ACC-3003");
        c3.setAccountType("SAVINGS");
        c3.setBalance(1100.0);
        c3.setActive(true);

        accountStore.put(a1.getId(), a1);
        accountStore.put(a2.getId(), a2);
        accountStore.put(a3.getId(), a3);
        accountStore.put(b1.getId(), b1);
        accountStore.put(b2.getId(), b2);
        accountStore.put(c1.getId(), c1);
        accountStore.put(c2.getId(), c2);
        accountStore.put(c3.getId(), c3);
        return accountStore;
    }


}
