package com.francis.janaj.financetracker.domain.income.controllers;

import com.francis.janaj.financetracker.domain.expense.controllers.ExpenseController;
import com.francis.janaj.financetracker.domain.expense.exceptions.ExpenseException;
import com.francis.janaj.financetracker.domain.expense.models.Expense;
import com.francis.janaj.financetracker.domain.income.exceptions.IncomeException;
import com.francis.janaj.financetracker.domain.income.models.Income;
import com.francis.janaj.financetracker.domain.income.services.IncomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/incomes") public class IncomeController {
    private final Logger logger = LoggerFactory.getLogger(IncomeController.class);

    private IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("")
    public ResponseEntity<Income> createIncome(@RequestBody Income income) throws Exception{
        Income savedIncome = incomeService.createIncome(income);
        return new ResponseEntity<>(savedIncome, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Income>> getAllIncomes(){
        List<Income> incomes = incomeService.getAllIncomes();
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id){
        try{
            Income income = incomeService.getIncomeById(id);
            return new ResponseEntity<>(income, HttpStatus.OK);
        } catch(IncomeException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIncome(@PathVariable Integer id, @RequestBody Income income){
        try{
            Income updatedIncome = incomeService.updateIncome(id, income);
            return new ResponseEntity<>(updatedIncome, HttpStatus.OK);
        } catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable Integer id){
        try{
            incomeService.deleteIncome(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
