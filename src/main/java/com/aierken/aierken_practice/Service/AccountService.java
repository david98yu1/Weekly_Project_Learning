package com.aierken.aierken_practice.Service;

import com.aierken.aierken_practice.Exception.AccountNotFoundException;
import com.aierken.aierken_practice.Exception.IllegalAmountException;
import com.aierken.aierken_practice.Exception.InsufficientBalanceException;
import com.aierken.aierken_practice.Exception.UnauthorizedAccessException;
import com.aierken.aierken_practice.entity.Account;
import com.aierken.aierken_practice.entity.Transaction;
import com.aierken.aierken_practice.entity.TransactionType;
import com.aierken.aierken_practice.repository.AccountRepository;
import com.aierken.aierken_practice.repository.TransactionRepository;
import com.aierken.aierken_practice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public double withdraw(Long userId, Long accountId, double amount){
        Account account = accountRepository.findById(accountId).orElseThrow(()-> {
            return new AccountNotFoundException("Account not found");
        });

        if (amount <= 0) {
            throw new IllegalAmountException("Withdraw amount is zero or negative");
        }

        if(!account.getUser().getId().equals(userId)){
            throw new UnauthorizedAccessException("User is not allowed to withdraw");
        }

        if(account.getBalance() < amount){
            throw new InsufficientBalanceException("Insufficient balance");
        }

        double before = account.getBalance();
        double after = account.getBalance() - amount;

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction transaction = Transaction.builder().account(account).type(TransactionType.WITHDRAW).amount(amount).balanceBefore(before).balanceAfter(after).build();
        transactionRepository.save(transaction);

        return account.getBalance();
    }

    @Transactional
    public double deposit(Long userId, Long accountId, double amount){
        Account account = accountRepository.findById(accountId).orElseThrow(()-> {
            return new AccountNotFoundException("Account not found");
        });

        if (amount <= 0) {
            throw new IllegalAmountException("Deposit amount is zero or negative");
        }

        if(!account.getUser().getId().equals(userId)){
            throw new UnauthorizedAccessException("User is not allowed to withdraw");
        }

        double before = account.getBalance();
        double after = account.getBalance() + amount;

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction transaction = Transaction.builder().account(account).type(TransactionType.DEPOSIT).amount(amount).balanceBefore(before).balanceAfter(after).build();
        transactionRepository.save(transaction);

        return account.getBalance();
    }

    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount){
        if (amount <= 0) {
            throw new IllegalAmountException("Transfer amount is zero or negative");
        }

        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber).orElseThrow(()-> new AccountNotFoundException("From Account not found"));

        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber).orElseThrow(()-> new AccountNotFoundException("To Account not found"));

        double fromBefore = fromAccount.getBalance();
        double fromAfter = fromAccount.getBalance() - amount;

        double toBefore = toAccount.getBalance();
        double toAfter = toAccount.getBalance() + amount;

        if (fromAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        fromAccount.setBalance(fromAfter);
        toAccount.setBalance(toAfter);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        transactionRepository.save(Transaction.builder().account(fromAccount).type(TransactionType.TRANSFER).amount(amount).balanceBefore(fromBefore).balanceAfter(fromAfter).build());
        transactionRepository.save(Transaction.builder().account(toAccount).type(TransactionType.TRANSFER).amount(amount).balanceBefore(toBefore).balanceAfter(toAfter).build());
    }

    public List<Transaction> getTransactionsByAccountNumber(String accountNumber){
        accountRepository.findByAccountNumber(accountNumber).orElseThrow(()-> new AccountNotFoundException("Account not found"));
        return transactionRepository.findByAccount_AccountNumberOrderByCreatedAtDesc(accountNumber);
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
