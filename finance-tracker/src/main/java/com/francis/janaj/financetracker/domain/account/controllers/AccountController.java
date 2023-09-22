package com.francis.janaj.financetracker.domain.account.controllers;

import com.francis.janaj.financetracker.domain.account.exceptions.AccountException;
import com.francis.janaj.financetracker.domain.account.models.Account;
import com.francis.janaj.financetracker.domain.account.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account savedAccount = accountService.createAccount(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Account>> getAllAccounts(){
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id){
        try{
            Account account = accountService.getAccountById(id);
            return new ResponseEntity<>(account, HttpStatus.OK);

        } catch(AccountException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Integer id, @RequestBody Account account){
        try{
            Account updatedAccount = accountService.updateAccount(id,account);
            return new ResponseEntity<>(updatedAccount,HttpStatus.OK);

        } catch(AccountException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer id){
        try{
            accountService.deleteAccount(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();

        }catch(AccountException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }


}
