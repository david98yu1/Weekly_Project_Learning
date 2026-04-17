package com.aierken.aierken_practice.Service;

import com.aierken.aierken_practice.Exception.AccountNotFoundException;
import com.aierken.aierken_practice.Exception.InsufficientBalanceException;
import com.aierken.aierken_practice.Exception.UnauthorizedAccessException;
import com.aierken.aierken_practice.entity.Account;
import com.aierken.aierken_practice.repository.AccountRepository;
import com.aierken.aierken_practice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public double withdraw(Long userId, Long accountId, double amount){
        //Optional<Account> account1 = accountRepository.findById(accountId);
        Account account1 = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(account1.getUser() == null || !(account1.getUser().getId().equals(userId))){
            throw new UnauthorizedAccessException("User is not allowed to withdraw");
        }
        if(account1.getBalance() < amount){
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account1.setBalance(account1.getBalance() - amount);
        accountRepository.save(account1);

        return account1.getBalance();
    }

    public List<Account> filterAccountsOver1000(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new AccountNotFoundException("User not found"));

        return accountRepository.findByUser_Id(userId).stream()
                .filter(account -> account.getBalance() > 1000)
                .collect(Collectors.toList());
    }

    public double sumBalancesOver1000(Long userId) throws Exception {
        double totalresult = accountRepository.findByUser_Id(userId).stream()
                .mapToDouble(Account::getBalance)
                .sum();
        return totalresult > 1000?totalresult:0;
    }
}
