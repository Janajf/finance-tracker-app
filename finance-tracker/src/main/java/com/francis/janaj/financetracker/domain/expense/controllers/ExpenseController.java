package com.francis.janaj.financetracker.domain.expense.controllers;

import com.francis.janaj.financetracker.domain.expense.exceptions.ExpenseException;
import com.francis.janaj.financetracker.domain.expense.models.Expense;
import com.francis.janaj.financetracker.domain.expense.services.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    private ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("")
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) throws Exception{
        Expense savedExpense = expenseService.createExpense(expense);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Expense>> getAllExpenses(){
        List<Expense> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id){
        try{
            Expense expense = expenseService.getExpenseById(id);
            return new ResponseEntity<>(expense, HttpStatus.OK);
        } catch(ExpenseException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Integer id, @RequestBody Expense expense){
        try{
            Expense updatedExpense = expenseService.updateExpense(id, expense);
            return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
        } catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Integer id){
        try{
            expenseService.deleteExpense(id);
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
