import React, { useEffect } from 'react'
import styled from "styled-components";
import { InnerLayout } from '../../styles/Layouts';
import { useGlobalContext } from '../../context/globalContext';
import Form from '../Form/Form';
import IncomeItem from '../IncomeItem/IncomeItem';
import ExpenseItem from './ExpenseItem';
import ExpenseForm from './ExpenseForm.js'
function Expense() {
  const {addIncome,expenses,getExpenses,deleteExpense,totalExpense} = useGlobalContext()

  useEffect(() =>{
    getExpenses()
}, [])


  return (
    <ExpenseStyled>
      <InnerLayout>
      <h1>Expenses</h1>
      <h2 className="total-income">Total Expense: <span style={{color: '#db8e1f'}}>-${totalExpense()}</span></h2>
      <div className='income-content'>
        <div className='form-container'>
          <ExpenseForm/>
          </div>
          <div className='incomes'>
            {expenses.map((expense) =>{
              const {id, amount, date, accountId} = expense;
              return <ExpenseItem
              key={id}
              id = {id}
              amount ={amount}
              date={date}
              accountId={accountId}
              indicatorColor="var(--color-green)"
              deleteItem={deleteExpense}
              />
            })}
        </div>
      </div>
        </InnerLayout>
        </ExpenseStyled>
  )
}

const ExpenseStyled = styled.div`
color: rgba(255, 255, 255, 1);
display: flex;
overflow: auto;
.total-income{
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(22, 21, 28, 1) ;
  box-shadow: 0px 1px 15px rgba(0, 0, 0, 0.06);
  border-radius: 20px;
  padding: 1rem;
  margin: 1rem 0;
  font-size: 2rem;
  gap: .5rem;
  span{
      font-size: 2.5rem;
      font-weight: 800;
      color: var(--color-green);
  }
}

.income-content{
  display: flex;
  gap: 2rem;
  .incomes{
      flex: 1;
  }
}
`;

export default Expense