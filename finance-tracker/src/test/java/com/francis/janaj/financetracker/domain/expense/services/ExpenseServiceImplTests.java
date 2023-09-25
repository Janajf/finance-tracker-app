package com.francis.janaj.financetracker.domain.expense.services;

import com.francis.janaj.financetracker.domain.account.exceptions.AccountException;
import com.francis.janaj.financetracker.domain.account.models.Account;
import com.francis.janaj.financetracker.domain.account.repos.AccountRepo;
import com.francis.janaj.financetracker.domain.expense.exceptions.ExpenseException;
import com.francis.janaj.financetracker.domain.expense.models.Expense;
import com.francis.janaj.financetracker.domain.expense.repos.ExpenseRepo;
import com.francis.janaj.financetracker.domain.income.models.Income;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ExpenseServiceImplTests {

    @MockBean
    private ExpenseRepo mockExpenseRepo;

    @MockBean
    private AccountRepo mockAccountRepo;

    @Autowired
    private ExpenseService expenseService;

    private Expense inputExpense;
    private Expense mockResponseExpense;
    private Account mockResponseAccount;

    @BeforeEach
    public void setUp(){
        List<Income> incomes = new ArrayList<>();
        List<Expense> expenses = new ArrayList<>();

        mockResponseAccount = new Account("checking", new BigDecimal(100),1, incomes, expenses);
        mockResponseAccount.setId(1);

        inputExpense = new Expense(new BigDecimal(100), LocalDate.now(), 1);
        mockResponseExpense = new Expense(new BigDecimal(100), LocalDate.now(), 1);
        mockResponseExpense.setId(1);

    }

    @Test
    @DisplayName("Expense service: create expense - success")
    public void createExpenseTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockResponseAccount)).when(mockAccountRepo).findById(any());

        BDDMockito.doReturn(mockResponseExpense).when(mockExpenseRepo).save(any());

        Expense createdExpense = expenseService.createExpense(inputExpense);

        Assertions.assertNotNull(createdExpense, "Should not be null");
        Assertions.assertEquals(createdExpense.getId(), 1);

    }
    @Test
    @DisplayName("Expense service: create expense - fail")
    public void createExpenseTestFail(){
        BDDMockito.doReturn(Optional.empty()).when(mockAccountRepo).findById(any());
        Assertions.assertThrows(AccountException.class, () -> expenseService.createExpense(inputExpense));
    }

    @Test
    @DisplayName("Expense service: get expense by id - success")
    public void getExpenseByIdTestSuccess() throws ExpenseException{
        BDDMockito.doReturn(Optional.of(mockResponseExpense)).when(mockExpenseRepo).findById(any());
        Expense returnedExpense = expenseService.getExpenseById(1);
        Assertions.assertEquals(returnedExpense.toString(), mockResponseExpense.toString());

    }

    @Test
    @DisplayName("Expense service: get expense by id - fail")
    public void getExpenseByIdTestFail(){
        BDDMockito.doReturn(Optional.empty()).when(mockExpenseRepo).findById(any());

        Assertions.assertThrows(ExpenseException.class, ()-> expenseService.getExpenseById(1));
    }

    @Test
    @DisplayName("Expense service: update expense - success")
    public void updateExpenseTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockResponseAccount)).when(mockAccountRepo).findById(any());
        BDDMockito.doReturn(Optional.of(mockResponseExpense)).when(mockExpenseRepo).findById(any());
        mockResponseExpense.setAmount(new BigDecimal(15));

        BDDMockito.doReturn(mockResponseExpense).when(mockExpenseRepo).save(ArgumentMatchers.any());

        Expense acutalExpense = expenseService.updateExpense(1, inputExpense);

        Assertions.assertEquals(mockResponseExpense,acutalExpense);
    }

    @Test
    @DisplayName("Expense service: delete expense - success")
    public void deleteExpenseTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockResponseExpense)).when(mockExpenseRepo).findById(any());
        BDDMockito.doReturn(Optional.of(mockResponseAccount)).when(mockAccountRepo).findById(any());

        Boolean actualResponse = expenseService.deleteExpense(1);
        Assertions.assertTrue(actualResponse);

    }

    @Test
    @DisplayName("Expense service: delete expense - expense not found")
    public void deleteExpenseTestExpenseNotFound(){
        BDDMockito.doReturn(Optional.empty()).when(mockExpenseRepo).findById(any());
        Assertions.assertThrows(Exception.class, ()-> expenseService.deleteExpense(1));
    }

    @Test
    @DisplayName("Expense service: delete expense - account not found")
    public void deleteExpenseTestAccountNotFound(){
        BDDMockito.doReturn(Optional.empty()).when(mockAccountRepo).findById(any());
        Assertions.assertThrows(Exception.class, ()-> expenseService.deleteExpense(1));
    }


}
