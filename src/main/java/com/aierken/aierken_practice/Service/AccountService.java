package com.aierken.aierken_practice.Service;

import com.aierken.aierken_practice.entity.Account;
import com.aierken.aierken_practice.repository.AccountRepository;
import com.aierken.aierken_practice.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public double withdraw(Long accountId, double amount){
        Account account1 = accountRepository.findById(accountId);
        if(account1 == null){
            throw new RuntimeException("Account not found");
        }
        if(account1.getBalance() < amount){
            throw new RuntimeException("Insufficient balance");
        }
        account1.setBalance(account1.getBalance()-amount);
        accountRepository.save(account1);

        return account1.getBalance();
    }

    public List<Account> filterAccountsOver1000(Long userId) throws Exception {
        return accountRepository.findAccountsByUserId(userId).stream()
                .filter(account -> account.getBalance()>1000)
                .collect(Collectors.toList());
    }

    public double sumBalancesOver1000(Long userId) throws Exception {
        return accountRepository.findAccountsByUserId(userId).stream()
                .filter(account -> account.getBalance()>1000)
                .mapToDouble(Account::getBalance)
                .sum();
    }
}
