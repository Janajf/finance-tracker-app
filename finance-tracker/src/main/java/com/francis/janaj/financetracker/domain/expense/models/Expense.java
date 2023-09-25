package com.francis.janaj.financetracker.domain.expense.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Expense {
    @Id
    @GeneratedValue
    private Integer id;
    private BigDecimal amount;
    private LocalDate date;
    @Column(name= "account_id")
    private Integer accountId;

    public Expense(BigDecimal amount, LocalDate date, Integer accountId) {
        this.amount = amount;
        this.date = date;
        this.accountId = accountId;
    }
}
