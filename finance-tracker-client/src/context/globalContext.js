import React, { useContext, useState } from "react"
import axios from 'axios'

const BASE_URL = "http://localhost:8080/"

const GlobalContext = React.createContext()

export const GlobalProvider = ({children}) =>{
    const [incomes, setIncomes] = useState([])
    const [expenses, setExpenses] = useState([])
    const [error, setError] = useState(null)

// income
    const addIncome = async (income) =>{
        const response = await axios.post(`${BASE_URL}incomes`,income)
            .catch((err) =>{
                setError(err.response.data.message
                    )
            })
            getIncomes()
    }

    const getIncomes = async () => {
        const response = await axios.get(`${BASE_URL}incomes`)
        setIncomes(response.data)
        console.log(response.data)
    }

    const deleteIncome = async (id) =>{
        const res = await axios.delete(`${BASE_URL}incomes/${id}`)
        getIncomes()
    }

    const totalIncome = () => {
        let totalIncome = 0;
        incomes.forEach((income) =>{
            totalIncome = totalIncome + income.amount
        })

        return totalIncome;
    }
    console.log(totalIncome())
//expense
    const addExpense = async (expense) =>{
        const response = await axios.post(`${BASE_URL}expenses`,expense)
            .catch((err) =>{
                setError(err.response.data.message
                    )
            })
            getExpenses()
    }
    
    const getExpenses = async () => {
        const response = await axios.get(`${BASE_URL}expenses`)
        setExpenses(response.data)
        console.log(response.data)
    }

    const deleteExpense = async (id) =>{
        const res = await axios.delete(`${BASE_URL}expenses/${id}`)
        getExpenses()
    }

    const totalExpense = () => {
        let totalExpense = 0;
        expenses.forEach((expense) =>{
            totalExpense = totalExpense + expense.amount
        })

        return totalExpense;
    }
    console.log(totalExpense())

    const totalBalance = () => {
        return totalIncome() - totalExpense()
    }

    const transactionHistory = () => {
        const history = [...incomes, ...expenses]
        history.sort((a, b) => {
            return new Date(b.createdAt) - new Date(a.createdAt)
        })

        return history.slice(0, 3)
    }




    return(
        <GlobalContext.Provider value={{
            addIncome,
            getIncomes,
            incomes,
            deleteIncome,
            totalIncome,
            expenses,
            addExpense,
            getExpenses,
            deleteExpense,
            totalExpense,
            totalBalance,
            transactionHistory
            }}>
            {children}
        </GlobalContext.Provider>


    )
}

export const useGlobalContext = () =>{
    return useContext(GlobalContext)
}