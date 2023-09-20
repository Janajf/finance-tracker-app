package com.francis.janaj.financetracker.domain.account.services;

import com.francis.janaj.financetracker.domain.account.exceptions.AccountException;
import com.francis.janaj.financetracker.domain.account.models.Account;

import java.util.List;

public interface AccountService {
    Account create(Account account);
    Account getAccountById(Integer id) throws AccountException;
    List<Account> getAllAccounts();
    Account updateAccount(Integer id, Account account) throws AccountException;
    Boolean deleteAccount(Integer id) throws AccountException;
}
