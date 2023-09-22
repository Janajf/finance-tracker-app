package com.francis.janaj.financetracker.domain.income.repos;

import com.francis.janaj.financetracker.domain.income.models.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepo extends JpaRepository<Income, Integer> {
}
