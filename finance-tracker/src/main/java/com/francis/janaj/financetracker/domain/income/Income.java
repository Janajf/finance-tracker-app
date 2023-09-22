package com.francis.janaj.financetracker.domain.income;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Income {
    @Id
    @GeneratedValue
    private Integer id;
    private Long amount;
    private LocalDate date;
    @Column(name= "account_id")
    private Integer accountId;
}
