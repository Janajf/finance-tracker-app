package com.francis.janaj.financetracker.domain.account.controllers;

import com.francis.janaj.financetracker.domain.account.services.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountService accountService;


}
