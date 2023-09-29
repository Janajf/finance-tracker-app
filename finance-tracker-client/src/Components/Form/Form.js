import React, { useState } from 'react'
import styled from 'styled-components'
import DatePicker from 'react-datepicker'
import "react-datepicker/dist/react-datepicker.css";
import { useGlobalContext } from '../../context/globalContext';
import Button from '../Button/Button';
import { plus } from '../../utils/Icons';


function Form() {
    const {addIncome,getIncomes, setError} = useGlobalContext()
    const [inputState, setInputState] = useState({
        amount: '',
        date: '',
        accountId: ''
    })

    const { amount, date, accountId } = inputState;

    const handleInput = name => e => {
        setInputState({...inputState, [name]: e.target.value})
        
    }

    const handleSubmit = e => {
        e.preventDefault()
        addIncome(inputState)
        setInputState({
            amount: '',
            date: '',
            accountId: '',
        })
        
    }
  return (
    <FormStyled onSubmit={handleSubmit}>
        <div className='input-control'>
            <input 
            type="text"
            value={amount}
            name={"amount"}
            placeholder='Amount'
            onChange={handleInput('amount')}
            />
        </div>
        <div className="input-control">
                <DatePicker 
                    id='date'
                    placeholderText='Date'
                    selected={date}
                    dateFormat="MM/dd/yyyy"
                    onChange={(date) => {
                        setInputState({...inputState, date: date})
                    }}
                />
            </div>
            <div className='input-control'>
            <input 
            type="text"
            value={accountId}
            name={"accountId"}
            placeholder='AccountId'
            onChange={handleInput('accountId')}
            />
        </div>
        <div className='submit-btn'>
        <Button 
                    name={'Add Income'}
                    icon={plus}
                    bPad={'.8rem 1.6rem'}
                    bRad={'30px'}
                    bg={'var(--color-accent'}
                    color={'#fff'}
                />
        </div>

    
    </FormStyled>
  )
}

const FormStyled = styled.form`
display: flex;
    flex-direction: column;
    gap: 2rem;
    input, textarea, select{
        font-family: inherit;
        font-size: inherit;
        outline: none;
        border: none;
        padding: .5rem 1rem;
        border-radius: 5px;
        border: 2px solid #fff;
        background: transparent;
        resize: none;
        box-shadow: 0px 1px 15px rgba(0, 0, 0, 0.06);
        color: rgba(255, 255, 255, 1);
        &::placeholder{
            color: rgba(255, 255, 255, 1);
        }
    }
    .input-control{
        input{
            width: 100%;
        }
    }

    .submit-btn{
        button{
            background: rgba(63, 92, 156, 1) !important;
            box-shadow: 0px 1px 15px rgba(0, 0, 0, 0.06);
            &:hover{
                background: var(--color-green) !important;
            }
        }
    }

`;
export default Form