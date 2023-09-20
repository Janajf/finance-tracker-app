package com.francis.janaj.financetracker.domain.expense;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

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
    private Long amount;
    private Date date;
    @Column(name= "account_id")
    private Integer accountId;
}
