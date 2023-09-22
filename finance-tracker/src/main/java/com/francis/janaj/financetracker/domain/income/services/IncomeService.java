package com.francis.janaj.financetracker.domain.income.services;


import com.francis.janaj.financetracker.domain.income.exceptions.IncomeException;
import com.francis.janaj.financetracker.domain.income.models.Income;
import java.util.List;

public interface IncomeService {
    Income createIncome(Income income) throws Exception;
    Income getIncomeById(Integer id) throws IncomeException;
    List<Income> getAllIncomes();
    Income updateIncome(Integer id, Income income) throws Exception;
    Boolean deleteIncome(Integer id) throws Exception;
}
