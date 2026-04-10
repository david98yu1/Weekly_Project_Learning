package com.aierken.aierken_practice.repository;


import com.aierken.aierken_practice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepository {


    private final Map<Long, User> userStore;


    @Autowired
    public UserRepository(Map<Long, User> userStore) {
        this.userStore = userStore;
    }

    public User findById(Long id) {
        return userStore.get(id);
    }

    public void save(User user) {
        userStore.put(user.getId(), user);
    }

    public User findByAccountNumber(String accountNumber) {
        return userStore.values().stream()
                .filter(user -> user.getAccounts().stream()
                        .anyMatch(account -> account.getAccountNumber().equals(accountNumber)))
                .findFirst()
                .orElse(null);
    }
}
