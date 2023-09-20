package com.francis.janaj.financetracker.domain.account.services;

import com.francis.janaj.financetracker.domain.account.exceptions.AccountException;
import com.francis.janaj.financetracker.domain.account.models.Account;
import com.francis.janaj.financetracker.domain.account.repos.AccountRepo;
import com.francis.janaj.financetracker.domain.expense.Expense;
import com.francis.janaj.financetracker.domain.income.Income;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AccountServiceImplTests {

    @MockBean
    private AccountRepo mockAccountRepo;

    @Autowired
    private AccountService accountService;
    private Account inputAccount;
    private Account mockResponseAccount;

    private List<Account> mockAccounts;

    @BeforeEach
    public void setUp() {
        List<Income> incomes = new ArrayList<>();
        List<Expense> expenses = new ArrayList<>();

        inputAccount = new Account("checking", 100L, incomes, expenses);
        mockResponseAccount = new Account("checking", 100L, incomes, expenses);
        mockResponseAccount.setId(1);

        mockAccounts = new ArrayList<>();
        mockAccounts.add(mockResponseAccount);

    }

    @Test
    @DisplayName("Account Service: Create account - success")
    public void createAccountTestSuccess() {
        BDDMockito.doReturn(mockResponseAccount).when(mockAccountRepo).save(ArgumentMatchers.any());
        Account createdAccount = accountService.create(inputAccount);
        Assertions.assertNotNull(createdAccount, "Account should not be null");
        Assertions.assertEquals(createdAccount.getId(), 1);
    }

    @Test
    @DisplayName("Account Service: Get account by id - success")
    public void getAccountByIdSuccess() throws AccountException {
        BDDMockito.doReturn(Optional.of(mockResponseAccount)).when(mockAccountRepo).findById(1);
        Account returnedAccount = accountService.getAccountById(1);
        Assertions.assertEquals(returnedAccount.toString(), mockResponseAccount.toString());
    }

    @Test
    @DisplayName("Account Service: Get account by id - fail")
    public void getAccountByIdFail() throws AccountException {
        BDDMockito.doReturn(Optional.empty()).when(mockAccountRepo).findById(1);
        Assertions.assertThrows(AccountException.class, () -> accountService.getAccountById(1));
    }

    @Test
    @DisplayName("Account Service: Get all accounts - success")
    public void getAllAccountTestSuccess() {
        BDDMockito.doReturn(mockAccounts).when(mockAccountRepo).findAll();
        List<Account> returnedAccounts = accountService.getAllAccounts();
        Assertions.assertEquals(returnedAccounts, mockAccounts);

    }

    @Test
    @DisplayName("Account Service: Update account - success")
    public void updateAccountTestSuccess() throws AccountException {
        BDDMockito.doReturn(Optional.of(mockResponseAccount)).when(mockAccountRepo).findById(1);

        mockResponseAccount.setType("updated checking");

        BDDMockito.doReturn(mockResponseAccount).when(mockAccountRepo).save(ArgumentMatchers.any());

        Account actualAccount = accountService.updateAccount(1, inputAccount);

        Assertions.assertEquals(mockResponseAccount, actualAccount);

    }

    @Test
    @DisplayName("Account Service: Update account - fail")
    public void updateAccountTestFail(){
        BDDMockito.doReturn(Optional.empty()).when(mockAccountRepo).findById(1);
        Assertions.assertThrows(AccountException.class, () -> accountService.updateAccount(1, inputAccount));
    }

    @Test
    @DisplayName("Account Service: Delete account - success")
    public void deleteAccountTestSuccess() throws AccountException {
        BDDMockito.doReturn(Optional.of(mockResponseAccount)).when(mockAccountRepo).findById(1);

        Boolean actualResponse = accountService.deleteAccount(1);

        Assertions.assertTrue(actualResponse);

    }

    @Test
    @DisplayName("Account Service: Delete account - fail")
    public void deleteAccountTestFail(){
        BDDMockito.doReturn(Optional.empty()).when(mockAccountRepo).findById(1);

        Assertions.assertThrows(AccountException.class, ()-> accountService.deleteAccount(1));

    }


}


