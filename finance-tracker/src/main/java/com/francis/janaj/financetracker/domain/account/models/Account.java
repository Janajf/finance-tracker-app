package com.francis.janaj.financetracker.domain.account.models;

import com.francis.janaj.financetracker.domain.expense.models.Expense;
import com.francis.janaj.financetracker.domain.income.models.Income;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue
    private Integer id;
    private String type;
    private BigDecimal balance;
    @Column(name= "user_id")
    private Integer userId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private List<Income> incomes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private List<Expense> expenses;

    public Account(String type, BigDecimal balance, Integer userId, List<Income> incomes, List<Expense> expenses) {
        this.type = type;
        this.balance = balance;
        this.userId = userId;
        this.incomes = incomes;
        this.expenses = expenses;
    }
}
