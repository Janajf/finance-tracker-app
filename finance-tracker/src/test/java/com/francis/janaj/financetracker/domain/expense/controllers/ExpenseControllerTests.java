package com.francis.janaj.financetracker.domain.expense.controllers;


import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.francis.janaj.financetracker.domain.account.models.Account;
import com.francis.janaj.financetracker.domain.expense.exceptions.ExpenseException;
import com.francis.janaj.financetracker.domain.expense.models.Expense;
import com.francis.janaj.financetracker.domain.expense.services.ExpenseService;
import com.francis.janaj.financetracker.domain.income.models.Income;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ExpenseControllerTests {
    @MockBean
    private ExpenseService mockExpenseService;

    @Autowired
    private MockMvc mockMvc;

    private Expense inputExpense;
    private Expense mockReponseExpense;
    private Account mockResponseAccount;

    private String jsonInputExpense;

    @BeforeEach
    public void setUp() throws Exception {
        List<Income> incomes = new ArrayList<>();
        List<Expense> expenses = new ArrayList<>();

        mockResponseAccount = new Account("checking", 100L,1, incomes, expenses);
        mockResponseAccount.setId(1);

        inputExpense = new Expense(100L, LocalDate.now(), 1);
        mockReponseExpense = new Expense(100L, LocalDate.now(), 1);
        mockReponseExpense.setId(1);

        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonInputExpense = jsonMapper.writeValueAsString(inputExpense);

    }

    @Test
    @DisplayName("Expense post: /expenses - success")
    public void createExpenseSuccess() throws Exception {
        BDDMockito.doReturn(mockReponseExpense).when(mockExpenseService).createExpense(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputExpense))

                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("Get expense by id : expenses/1 - success")
    public void getExpenseByIdSuccess() throws Exception{
        BDDMockito.doReturn(mockReponseExpense).when(mockExpenseService).getExpenseById(any());

        mockMvc.perform(get("/expenses/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.amount", Is.is(100)));

    }

    @Test
    @DisplayName("Get expense by id : expenses/1 - fail")
    public void getExpenseByIdFail() throws Exception{
        BDDMockito.doThrow(new ExpenseException("Expense not found")).when(mockExpenseService).getExpenseById(any());

        mockMvc.perform(get("/expenses/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT  /expense/1 - success")
    public void updateExpenseTestSuccess() throws Exception {
        mockReponseExpense.setAmount(10L);

        BDDMockito.doReturn(mockReponseExpense).when(mockExpenseService).updateExpense(any(),any());

        mockMvc.perform(put("/expenses/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInputExpense))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.amount", Is.is(10)));

    }

    @Test
    @DisplayName("PUT /expense/1 - not found")
    public void putExpenseTestNotFound() throws Exception {
        BDDMockito.doThrow(new ExpenseException("Not found")).when(mockExpenseService).updateExpense(any(),any());

        mockMvc.perform(put("/expenses/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInputExpense))

                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete /expense/1 - success")
    public void deleteExpenseTestSuccess() throws Exception {
        BDDMockito.doReturn(true).when(mockExpenseService).deleteExpense(any());

        mockMvc.perform(delete("/expenses/{id}",1))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Delete /expense/1 - fail")
    public void deleteExpenseTestFail() throws Exception {
        BDDMockito.doThrow(new ExpenseException("Expense not found")).when(mockExpenseService).deleteExpense(any());

        mockMvc.perform(delete("/expenses/{id}",1))
                .andExpect(status().isNotFound());

    }



}
