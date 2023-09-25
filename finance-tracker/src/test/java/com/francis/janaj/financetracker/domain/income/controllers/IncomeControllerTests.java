package com.francis.janaj.financetracker.domain.income.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.francis.janaj.financetracker.domain.account.models.Account;
import com.francis.janaj.financetracker.domain.expense.exceptions.ExpenseException;
import com.francis.janaj.financetracker.domain.expense.models.Expense;
import com.francis.janaj.financetracker.domain.income.exceptions.IncomeException;
import com.francis.janaj.financetracker.domain.income.models.Income;
import com.francis.janaj.financetracker.domain.income.services.IncomeService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class IncomeControllerTests {
    @MockBean
    private IncomeService mockIncomeService;
    @Autowired
    private MockMvc mockMvc;

    private Income inputIncome;
    private Income mockResponseIncome;
    private Account mockResponseAccount;
    private String jsonInputIncome;

    @BeforeEach
    public void setUp() throws Exception {
        List<Income> incomes = new ArrayList<>();
        List<Expense> expenses = new ArrayList<>();

        mockResponseAccount = new Account("checking", new BigDecimal(100),1, incomes, expenses);
        mockResponseAccount.setId(1);

        inputIncome = new Income(new BigDecimal(100), LocalDate.now(), 1);
        mockResponseIncome = new Income(new BigDecimal(100), LocalDate.now(), 1);
        mockResponseIncome.setId(1);

        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonInputIncome = jsonMapper.writeValueAsString(inputIncome);
    }

    @Test
    @DisplayName("Income post: /incomes - success")
    public void createIncomeSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseIncome).when(mockIncomeService).createIncome(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/incomes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInputIncome))

                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("Get income by id : incomes/1 - success")
    public void getIncomeByIdSuccess() throws Exception{
        BDDMockito.doReturn(mockResponseIncome).when(mockIncomeService).getIncomeById(any());

        mockMvc.perform(get("/incomes/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.amount", Is.is(100)));

    }

    @Test
    @DisplayName("Get Income by id : Incomes/1 - fail")
    public void getIncomeByIdFail() throws Exception{
        BDDMockito.doThrow(new IncomeException("Income not found")).when(mockIncomeService).getIncomeById(any());

        mockMvc.perform(get("/incomes/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT  /income/1 - success")
    public void updateIncomeTestSuccess() throws Exception {
        mockResponseIncome.setAmount(new BigDecimal(10));

        BDDMockito.doReturn(mockResponseIncome).when(mockIncomeService).updateIncome(any(),any());

        mockMvc.perform(put("/incomes/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInputIncome))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.amount", Is.is(10)));

    }

    @Test
    @DisplayName("PUT /income/1 - not found")
    public void putIncomeTestNotFound() throws Exception {
        BDDMockito.doThrow(new IncomeException("Not found")).when(mockIncomeService).updateIncome(any(),any());

        mockMvc.perform(put("/incomes/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInputIncome))

                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete /income/1 - success")
    public void deleteIncomeTestSuccess() throws Exception {
        BDDMockito.doReturn(true).when(mockIncomeService).deleteIncome(any());

        mockMvc.perform(delete("/incomes/{id}",1))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Delete /income/1 - fail")
    public void deleteIncomeTestFail() throws Exception {
        BDDMockito.doThrow(new IncomeException("Income not found")).when(mockIncomeService).deleteIncome(any());

        mockMvc.perform(delete("/incomes/{id}",1))
                .andExpect(status().isNotFound());

    }

}
