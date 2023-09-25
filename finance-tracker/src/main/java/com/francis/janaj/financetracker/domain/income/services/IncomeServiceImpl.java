package com.francis.janaj.financetracker.domain.income.services;

import com.francis.janaj.financetracker.domain.account.exceptions.AccountException;
import com.francis.janaj.financetracker.domain.account.models.Account;
import com.francis.janaj.financetracker.domain.account.repos.AccountRepo;
import com.francis.janaj.financetracker.domain.income.exceptions.IncomeException;
import com.francis.janaj.financetracker.domain.income.models.Income;
import com.francis.janaj.financetracker.domain.income.repos.IncomeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService{
    private static Logger logger = LoggerFactory.getLogger(IncomeServiceImpl.class);
    private IncomeRepo incomeRepo;
    private AccountRepo accountRepo;

    @Autowired
    public IncomeServiceImpl(IncomeRepo incomeRepo, AccountRepo accountRepo) {
        this.incomeRepo = incomeRepo;
        this.accountRepo = accountRepo;
    }

    @Override
    public Income createIncome(Income income) throws Exception {
        Integer accountId = income.getAccountId();

        Optional<Account> accountOptional = accountRepo.findById(accountId);

        if(accountOptional.isEmpty()){
            logger.error("Account with id {} does not exist", accountId);
            throw new AccountException("Account not found");
        }

        Account accountToUpdate = accountOptional.get();
        BigDecimal newBalance = accountToUpdate.getBalance().add(income.getAmount());
        accountToUpdate.setBalance(newBalance);

        accountRepo.save(accountToUpdate);
        return incomeRepo.save(income);
    }

    @Override
    public Income getIncomeById(Integer id) throws IncomeException {
        Optional<Income> incomeOptional = incomeRepo.findById(id);

        if(incomeOptional.isEmpty()){
            logger.error("income with id {} does not exist",id);
            throw new IncomeException("Income not found");
        }
        return incomeOptional.get();
    }

    @Override
    public List<Income> getAllIncomes() {
        return (List)incomeRepo.findAll();
    }

    @Override
    public Income updateIncome(Integer id, Income income) throws Exception {
        Integer accountId = income.getAccountId();

        Optional<Account> accountOptional = accountRepo.findById(accountId);

        if(accountOptional.isEmpty()){
            logger.error("Account with id {} does not exist", accountId);
            throw new AccountException("Account not found");
        }

        Account accountToUpdate = accountOptional.get();
        Optional<Income> incomeOptional = incomeRepo.findById(id);

        if(incomeOptional.isEmpty()){
            logger.error("Income with id {} does not exist",id);
            throw new IncomeException("Income not found");
        }

        //remove old income from account balance
        Income oldIncome = incomeOptional.get();

        BigDecimal resetBalance = accountToUpdate.getBalance().subtract(oldIncome.getAmount());
        accountToUpdate.setBalance(resetBalance);

        accountRepo.save(accountToUpdate);

        //increase balance by new income amount
        BigDecimal newBalance = accountToUpdate.getBalance().add(income.getAmount());
        accountToUpdate.setBalance(newBalance);

        accountRepo.save(accountToUpdate);
        return incomeRepo.save(income);
    }

    @Override
    public Boolean deleteIncome(Integer id) throws Exception {
        Optional<Income> incomeOptional = incomeRepo.findById(id);

        if(incomeOptional.isEmpty()){
            logger.error("Income with id {} does not exist",id);
            throw new IncomeException("Income not found");
        }

        Integer accountId = incomeOptional.get().getAccountId();

        Optional<Account> accountOptional = accountRepo.findById(accountId);

        if(accountOptional.isEmpty()){
            logger.error("Account with id {} does not exist", accountId);
            throw new Exception("Account not found");
        }

        Account accountToUpdate = accountOptional.get();

        //remove income from account balance
        Income incomeToDelete = incomeOptional.get();
        BigDecimal resetBalance = accountToUpdate.getBalance().subtract(incomeToDelete.getAmount());
        accountToUpdate.setBalance(resetBalance);

        accountRepo.save(accountToUpdate);

        incomeRepo.delete(incomeToDelete);


        return true;
    }
}
