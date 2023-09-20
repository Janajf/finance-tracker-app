package com.francis.janaj.financetracker.domain.account.services;

import com.francis.janaj.financetracker.domain.account.exceptions.AccountException;
import com.francis.janaj.financetracker.domain.account.models.Account;
import com.francis.janaj.financetracker.domain.account.repos.AccountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private AccountRepo accountRepo;

    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public Account create(Account account) {
        Account savedAccount = accountRepo.save(account);
        return savedAccount;
    }

    @Override
    public Account getAccountById(Integer id) throws AccountException {
        Optional<Account> accountOptional = accountRepo.findById(id);
        if(accountOptional.isEmpty()){
            logger.error("Account with id {} does not exist", id);
            throw new AccountException("Account not found");
        }
        return accountOptional.get();
    }

    @Override
    public List<Account> getAllAccounts() {

        return (List)accountRepo.findAll();
    }

    @Override
    public Account updateAccount(Integer id, Account account) throws AccountException {
        Optional<Account> accountOptional = accountRepo.findById(id);

        if(accountOptional.isEmpty()){
            throw new AccountException("Account does not exist, cannot update");
        }

        Account accountToUpdate = accountOptional.get();

        accountToUpdate.setType(account.getType());
        accountToUpdate.setBalance(account.getBalance());
        accountToUpdate.setIncomes(account.getIncomes());
        accountToUpdate.setExpenses(account.getExpenses());

        return accountRepo.save(accountToUpdate);
    }

    @Override
    public Boolean deleteAccount(Integer id) throws AccountException {
        Optional<Account> accountOptional = accountRepo.findById(id);

        if(accountOptional.isEmpty()){
            throw new AccountException("Account does not exist, cannot delete");
        }

        Account accountToDelete = accountOptional.get();
        accountRepo.delete(accountToDelete);

        return true;
    }
}
