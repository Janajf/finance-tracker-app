package com.francis.janaj.financetracker.domain.income.services;

import com.francis.janaj.financetracker.domain.account.exceptions.AccountException;
import com.francis.janaj.financetracker.domain.account.models.Account;
import com.francis.janaj.financetracker.domain.account.repos.AccountRepo;
import com.francis.janaj.financetracker.domain.expense.models.Expense;
import com.francis.janaj.financetracker.domain.income.exceptions.IncomeException;
import com.francis.janaj.financetracker.domain.income.models.Income;
import com.francis.janaj.financetracker.domain.income.repos.IncomeRepo;
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
public class IncomeServiceImplTests {
    @MockBean
    private IncomeRepo mockIncomeRepo;

    @MockBean
    private AccountRepo mockAccountRepo;

    @Autowired
    private IncomeService incomeService;

    private Income inputIncome;
    private Income mockResponseIncome;
    private Account mockResponseAccount;

    @BeforeEach
    public void setUp(){
        List<Income> incomes = new ArrayList<>();
        List<Expense> expenses = new ArrayList<>();

        mockResponseAccount = new Account("checking", new BigDecimal(100),1, incomes, expenses);
        mockResponseAccount.setId(1);

        inputIncome = new Income(new BigDecimal(100), LocalDate.now(), 1);
        mockResponseIncome = new Income(new BigDecimal(100), LocalDate.now(), 1);
        mockResponseIncome.setId(1);
    }

    @Test
    @DisplayName("Income service: create income - success")
    public void createIncomeTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockResponseAccount)).when(mockAccountRepo).findById(any());
        BDDMockito.doReturn(mockResponseIncome).when(mockIncomeRepo).save(any());

        Income createdIncome = incomeService.createIncome(inputIncome);
        Assertions.assertNotNull(createdIncome, "Should not be null");
        Assertions.assertEquals(createdIncome.getId(),1);

    }

    @Test
    @DisplayName("Income service: create income - fail")
    public void createIncomeTestFail() throws Exception {
        BDDMockito.doReturn(Optional.empty()).when(mockAccountRepo).findById(any());
        Assertions.assertThrows(AccountException.class, () -> incomeService.createIncome(inputIncome));

    }

    @Test
    @DisplayName("Income service: get income by id - success")
    public void getIncomeByIdTestSuccess() throws IncomeException {
        BDDMockito.doReturn(Optional.of(mockResponseIncome)).when(mockIncomeRepo).findById(any());
        Income returnedIncome = incomeService.getIncomeById(1);
        Assertions.assertEquals(returnedIncome.toString(),mockResponseIncome.toString());

    }

    @Test
    @DisplayName("Income service: get Income by id - fail")
    public void getIncomeByIdTestFail(){
        BDDMockito.doReturn(Optional.empty()).when(mockIncomeRepo).findById(any());

        Assertions.assertThrows(IncomeException.class, ()-> incomeService.getIncomeById(1));
    }

    @Test
    @DisplayName("Income service: update income - success")
    public void updateIncomeTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockResponseAccount)).when(mockAccountRepo).findById(any());
        BDDMockito.doReturn(Optional.of(mockResponseIncome)).when(mockIncomeRepo).findById(any());
        mockResponseIncome.setAmount(new BigDecimal(15));

        BDDMockito.doReturn(mockResponseIncome).when(mockIncomeRepo).save(ArgumentMatchers.any());

        Income acutalIncome = incomeService.updateIncome(1, inputIncome);

        Assertions.assertEquals(mockResponseIncome,acutalIncome);
    }

    @Test
    @DisplayName("Income service: delete income - success")
    public void deleteIncomeTestSuccess() throws Exception {
        BDDMockito.doReturn(Optional.of(mockResponseIncome)).when(mockIncomeRepo).findById(any());
        BDDMockito.doReturn(Optional.of(mockResponseAccount)).when(mockAccountRepo).findById(any());

        Boolean actualResponse = incomeService.deleteIncome(1);
        Assertions.assertTrue(actualResponse);

    }

    @Test
    @DisplayName("Income service: delete income - income not found")
    public void deleteIncomeTestIncomeNotFound(){
        BDDMockito.doReturn(Optional.empty()).when(mockIncomeRepo).findById(any());
        Assertions.assertThrows(Exception.class, ()-> incomeService.deleteIncome(1));
    }

    @Test
    @DisplayName("Income service: delete income - account not found")
    public void deleteIncomeTestAccountNotFound(){
        BDDMockito.doReturn(Optional.empty()).when(mockAccountRepo).findById(any());
        Assertions.assertThrows(Exception.class, ()-> incomeService.deleteIncome(1));
    }


}
