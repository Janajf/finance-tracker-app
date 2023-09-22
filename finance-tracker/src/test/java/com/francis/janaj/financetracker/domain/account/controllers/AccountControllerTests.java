package com.francis.janaj.financetracker.domain.account.controllers;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.francis.janaj.financetracker.domain.account.exceptions.AccountException;
import com.francis.janaj.financetracker.domain.account.models.Account;
import com.francis.janaj.financetracker.domain.account.services.AccountService;
import com.francis.janaj.financetracker.domain.expense.models.Expense;
import com.francis.janaj.financetracker.domain.income.Income;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AccountControllerTests {

    @MockBean
    private AccountService mockAccountService;

    @Autowired
    private MockMvc mockMvc;

    private Account inputAccount;
    private Account mockResponseAccount;
    private String jsonInputAccount;
    private List<Account> mockAccounts;

    @BeforeEach
    public void setUp() throws Exception {
        List<Income> incomes = new ArrayList<>();
        List<Expense> expenses = new ArrayList<>();

        inputAccount = new Account("checking", 100L,1, incomes, expenses);
        mockResponseAccount = new Account("checking", 100L,1, incomes, expenses);
        mockResponseAccount.setId(1);

        mockAccounts = new ArrayList<>();
        mockAccounts.add(mockResponseAccount);

        JsonMapper jsonMapper = new JsonMapper();
        jsonInputAccount = jsonMapper.writeValueAsString(inputAccount);
    }

    @Test
    @DisplayName("Account post: /accounts - success")
    public void createAccountSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseAccount).when(mockAccountService).createAccount(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputAccount))

                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", Is.is("checking")));

    }

    @Test
    @DisplayName("Get account by id: /accounts/1 - found")
    public void getAccountByIdSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseAccount).when(mockAccountService).getAccountById(1);

        mockMvc.perform(get("/accounts/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.type", is("checking")));
    }

    @Test
    @DisplayName("Get account by id: /accounts/1 - fail")
    public void getAccountByIdFail() throws Exception {
        BDDMockito.doThrow(new AccountException("Account not found")).when(mockAccountService).getAccountById(1);

        mockMvc.perform(get("/account/{id}",1))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("PUT /accounts/1 - success")
    public void updateAccountTestSuccess() throws Exception {
        mockResponseAccount.setType("updated checking");

        BDDMockito.doReturn(mockResponseAccount).when(mockAccountService).updateAccount(any(),any());

        mockMvc.perform(put("/accounts/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputAccount))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.type", is("updated checking")));
    }

    @Test
    @DisplayName("PUT /accounts/1 - fail")
    public void updateAccountTestFail() throws Exception {
        BDDMockito.doThrow(new AccountException("Account not found")).when(mockAccountService).updateAccount(any(),any());

        mockMvc.perform(put("/accounts/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputAccount))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /accounts/1 - success")
    public void deleteAccountTestSuccess() throws Exception {
        BDDMockito.doReturn(true).when(mockAccountService).deleteAccount(any());

        mockMvc.perform(delete("/accounts/{id}",1))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("DELETE /accounts/1 - fail")
    public void deleteAccountTestFail() throws Exception {
        BDDMockito.doThrow(new AccountException("Account not found")).when(mockAccountService).deleteAccount(any());

        mockMvc.perform(delete("/accounts/{id}",1))
                .andExpect(status().isNotFound());

    }




}
