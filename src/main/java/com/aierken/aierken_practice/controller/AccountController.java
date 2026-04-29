package com.aierken.aierken_practice.controller;

import com.aierken.aierken_practice.Service.AccountService;
import com.aierken.aierken_practice.dto.*;
import com.aierken.aierken_practice.entity.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts/withdraw")
    public ResponseEntity<Double> withdraw(@RequestBody WithdrawRequest request) {
        double remainingBalance = accountService.withdraw(request.getUserId(), request.getAccountId(), request.getAmount());
        return ResponseEntity.ok(remainingBalance);
    }

    @PostMapping("/accounts/deposit")
    public ResponseEntity<Double> deposit(@RequestBody WithdrawRequest request) {
        double remainingBalance = accountService.deposit(request.getUserId(), request.getAccountId(), request.getAmount());
        return ResponseEntity.ok(remainingBalance);
    }

    @PostMapping("/accounts/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request) {
        accountService.transfer(request.getFromId(), request.getToId(), request.getAmount());
        return ResponseEntity.ok(new TransferResponse(request.getFromId(), request.getToId(), request.getAmount(), "SUCCESS"));
    }

    @GetMapping("/users/accounts/rich")
    public ResponseEntity<List<AccountResponse>> rich(@RequestBody AccountRequest request) throws Exception {
        List<AccountResponse> responses = accountService.filterAccountsOver1000(request.getUserId()).stream().map(this::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/users/accounts/sum")
    public ResponseEntity<SumResponse> sum(@RequestBody AccountRequest request) throws Exception {
        return ResponseEntity.ok(new SumResponse(accountService.sumBalancesOver1000(request.getUserId())));
    }

    @GetMapping("/accounts/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@RequestBody TransactionRequest request) throws Exception {
        List<TransactionResponse> responses = accountService.getTransactionsByAccountNumber(request.getAccountNumber())
                .stream()
                .map(t -> new TransactionResponse(t.getId(), t.getType(), t.getAmount(), t.getBalanceBefore(), t.getBalanceAfter(), t.getCreatedAt()))
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> getAccount(@RequestBody AccountRequest request) throws Exception {
        List<AccountResponse> responses = accountService.getAccountsByUserId(request.getUserId()).stream().map(this::toResponse).toList();
        return ResponseEntity.ok(responses);
    }


    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getBalance()
        );
    }
}
