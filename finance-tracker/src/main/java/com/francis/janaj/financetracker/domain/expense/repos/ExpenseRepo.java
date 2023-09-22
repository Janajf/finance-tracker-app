package com.francis.janaj.financetracker.domain.expense.repos;

import com.francis.janaj.financetracker.domain.expense.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepo extends JpaRepository<Expense, Integer> {

}
