package com.aierken.aierken_practice.controller;

import com.aierken.aierken_practice.Exception.InsufficientBalanceException;
import com.aierken.aierken_practice.Exception.AccountNotFoundException;
import com.aierken.aierken_practice.Exception.UnauthorizedAccessException;
import com.aierken.aierken_practice.Service.AccountService;
import com.aierken.aierken_practice.dto.AccountResponse;
import com.aierken.aierken_practice.dto.ErrorResponse;
import com.aierken.aierken_practice.dto.SumResponse;
import com.aierken.aierken_practice.dto.WithdrawRequest;
import com.aierken.aierken_practice.entity.Account;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/users/{userId}/accounts/rich")
    public ResponseEntity<List<AccountResponse>> rich(@PathVariable Long userId) throws Exception {
        List<AccountResponse> responses = accountService.filterAccountsOver1000(userId).stream().map(this::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/users/{userId}/accounts/sum")
    public ResponseEntity<SumResponse> sum(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(new SumResponse(accountService.sumBalancesOver1000(userId)));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> HandleAccountNotFoundException(AccountNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> HandleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> HandleInsufficientBalanceException(InsufficientBalanceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getBalance()
        );
    }
}
