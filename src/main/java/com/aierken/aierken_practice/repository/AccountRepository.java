package com.aierken.aierken_practice.repository;

import com.aierken.aierken_practice.entity.Account;
import com.aierken.aierken_practice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    default Account createAccount(Account account) {
        return save(account);
    }

    List<Account> findByUser_Id(Long userId);

    Optional<Account> findByAccountNumber(String accountNumber);


    void deleteByAccountNumber(String accountNumber);

    void deleteById(Long account_id);

    Long user(User user);
}
