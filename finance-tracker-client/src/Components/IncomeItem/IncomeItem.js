import React from 'react'
import styled from 'styled-components'
import { bank, calender, dollar, piggy, trash } from '../../utils/Icons';
import Button from '../Button/Button';
import { dateFormat } from '../../utils/date.Format';

function IncomeItem({
    id,
    amount,
    date,
    accountId,
    deleteItem,
    indicatorColor
    
}) {
    
  return (
    <IncomeItemStyled indicator={indicatorColor}>
        <div className='icon' style={{ color: 'white' }}>
            {piggy}

        </div>
        <div className='content'>
            <h5 style={{ color: 'white' }}>Income</h5>
            <div className='inner-content'>
                <div className='text' style={{ color: 'white' }}>
                    <p>{dollar} {amount}</p>
                    <p>{calender} {dateFormat(date)}</p>
                    <p>{bank} {accountId}</p>
                </div>
                <div className="btn-con">
                        <Button 
                            icon={trash}
                            bPad={'1rem'}
                            bRad={'50%'}
                            bg={'#DA1818'}
                            color={'#fff'}
                            iColor={'#fff'}
                            hColor={'var(--color-green)'}
                            onClick={() => deleteItem(id)}
                        />
                </div>
            </div>
        </div>
    </IncomeItemStyled>
  )
}

const IncomeItemStyled = styled.div`
    background: rgba(22, 21, 28, 1);
    box-shadow: 0px 1px 15px rgba(0, 0, 0, 0.06);
    border-radius: 20px;
    padding: 1rem;
    margin-bottom: 1rem;
    display: flex;
    align-items: center;
    gap: 1rem;
    width: 100%;
    color: #222260;

    .icon{
        width: 80px;
        height: 80px;
        border-radius: 20px;
        background: green;
        display: flex;
        align-items: center;
        justify-content: center;
        i{
            font-size: 2.6rem;
        }
    }

    .content{
        flex: 1;
        display: flex;
        flex-direction: column;
        gap: .2rem;
        h5{
            font-size: 1.3rem;
            padding-left: 2rem;
            position: relative;
            &::before{
                content: '';
                position: absolute;
                left: 0;
                top: 50%;
                transform: translateY(-50%);
                width: .8rem;
                height: .8rem;
                border-radius: 50%;
                background: ${props => props.indicator};
            }
        }

        .inner-content{
            display: flex;
            justify-content: space-between;
            align-items: center;
            .text{
                display: flex;
                align-items: center;
                gap: 1.5rem;
                color: #f56692;
                p{
                    display: flex;
                    align-items: center;
                    gap: 0.5rem;
                    opacity: 0.8;
                }
            }
        }
    }

`;

export default IncomeItem