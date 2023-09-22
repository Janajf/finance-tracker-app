package com.francis.janaj.financetracker.domain.expense.services;

import com.francis.janaj.financetracker.domain.account.exceptions.AccountException;
import com.francis.janaj.financetracker.domain.expense.exceptions.ExpenseException;
import com.francis.janaj.financetracker.domain.expense.models.Expense;

import java.util.List;

public interface ExpenseService {
    Expense createExpense(Expense expense) throws Exception;
    Expense getExpenseById(Integer id) throws ExpenseException;
    List<Expense> getAllExpenses();
    Expense updateExpense(Integer id, Expense expense) throws Exception;
    Boolean deleteExpense(Integer id) throws Exception;

}
