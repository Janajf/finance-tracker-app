package com.francis.janaj.financetracker.domain.expense.services;

import com.francis.janaj.financetracker.domain.account.exceptions.AccountException;
import com.francis.janaj.financetracker.domain.account.models.Account;
import com.francis.janaj.financetracker.domain.account.repos.AccountRepo;
import com.francis.janaj.financetracker.domain.expense.exceptions.ExpenseException;
import com.francis.janaj.financetracker.domain.expense.models.Expense;
import com.francis.janaj.financetracker.domain.expense.repos.ExpenseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{
    private static Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    private ExpenseRepo expenseRepo;
    private AccountRepo accountRepo;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepo expenseRepo, AccountRepo accountRepo) {
        this.expenseRepo = expenseRepo;
        this.accountRepo = accountRepo;
    }

    @Override
    public Expense createExpense(Expense expense) throws Exception {
        Integer accountId = expense.getAccountId();

        Optional<Account> accountOptional = accountRepo.findById(accountId);

        if(accountOptional.isEmpty()){
            logger.error("Account with id {} does not exist", accountId);
            throw new AccountException("Account not found");
        }

        Account accountToUpdate = accountOptional.get();
        Long newBalance = accountToUpdate.getBalance() - expense.getAmount();

        accountToUpdate.setBalance(newBalance);

        accountRepo.save(accountToUpdate);

        return expenseRepo.save(expense);
    }

    @Override
    public Expense getExpenseById(Integer id) throws ExpenseException {
        Optional<Expense> expenseOptional = expenseRepo.findById(id);
        if(expenseOptional.isEmpty()){
            logger.error("Expense with id {} does not exist",id);
            throw new ExpenseException("Expense not found");
        }
        return expenseOptional.get();
    }

    @Override
    public List<Expense> getAllExpenses() {
        return (List)expenseRepo.findAll();
    }

    @Override
    public Expense updateExpense(Integer id, Expense expense) throws Exception {
        Integer accountId = expense.getAccountId();

        Optional<Account> accountOptional = accountRepo.findById(accountId);

        if(accountOptional.isEmpty()){
            logger.error("Account with id {} does not exist", accountId);
            throw new AccountException("Account not found");
        }

        Account accountToUpdate = accountOptional.get();

        Optional<Expense> expenseOptional = expenseRepo.findById(id);

        if(expenseOptional.isEmpty()){
            logger.error("Expense with id {} does not exist",id);
            throw new ExpenseException("Expense not found");
        }

        // remove old expense from account balance
        Expense oldExpense = expenseOptional.get();

        Long resetBalance = accountToUpdate.getBalance() + oldExpense.getAmount();
        accountToUpdate.setBalance(resetBalance);

        accountRepo.save(accountToUpdate);

        // reduce balance by new expense amount

        accountToUpdate = accountOptional.get();
        Long newBalance = accountToUpdate.getBalance() - expense.getAmount();

        accountToUpdate.setBalance(newBalance);

        accountRepo.save(accountToUpdate);

        return expenseRepo.save(expense);
    }

    @Override
    public Boolean deleteExpense(Integer id) throws Exception {
        Optional<Expense> expenseOptional = expenseRepo.findById(id);

        if(expenseOptional.isEmpty()){
            logger.error("Expense with id {} does not exist",id);
            throw new Exception("Expense not found");
        }

        Integer accountId = expenseOptional.get().getAccountId();

        Optional<Account> accountOptional = accountRepo.findById(accountId);

        if(accountOptional.isEmpty()){
            logger.error("Account with id {} does not exist", accountId);
            throw new Exception("Account not found");
        }

        Account accountToUpdate = accountOptional.get();

        // remove expense from account balance
        Expense expenseToDelete = expenseOptional.get();

        Long resetBalance = accountToUpdate.getBalance() + expenseToDelete.getAmount();
        accountToUpdate.setBalance(resetBalance);

        accountRepo.save(accountToUpdate);

        expenseRepo.delete(expenseToDelete);


        return true;
    }
}
