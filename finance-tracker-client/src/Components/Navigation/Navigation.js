import React from 'react'
import styled from 'styled-components'
import { avatar } from '../../utils/Icons'
import { menuItems } from '../../utils/menuItems'
import { signout } from '../../utils/Icons'

function Navigation({active, setActive}) {
  return (
    <NavStyled>
        <div className='user-container'>
            <div className='avatar' style={{color: "white", width: "25px", height: "25px",fontSize:"45px"}} >{avatar}</div>
            <div className="text">
                <h2 style={{color:"white"}}>Janaj</h2>
                <p style={{color:"white"}}>Your Finances</p>
            </div>
        </div>
        
        <ul className='menu-items'>
            {menuItems.map((item) => {
                return <li style={{color:"white"}}
                key={item.id}
                onClick={() => setActive(item.id)}
                className={active === item.id ? 'active': ''}
                >
                    {item.icon}
                    <span>{item.title}</span>
                </li>
            })}
        </ul>

        <div className='bottom-nav'>
            <li style={{color:"white"}}>
                {signout} Sign Out
            </li>
        </div>

    </NavStyled>
  )
}
const NavStyled = styled.nav`
padding: 2rem 1.5rem;
    width: 300px;
    height: 100%;
    background: rgba(14, 12, 19, 1);
    backdrop-filter: blur(4.5px);
    border-radius: 32px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    gap: 2rem;
    .user-container{
        height: 100px;
        display: flex;
        align-items: center;
        gap: 1rem;

    }

    .menu-items{
        flex: 1;
        display: flex;
        flex-direction: column;
        li{
            display: grid;
            grid-template-columns: 40px auto;
            align-items: center;
            margin: .6rem 0;
            font-weight: 500;
            cursor: pointer;
            transition: all .4s ease-in-out;
            color: rgba(34, 34, 96, .6);
            padding-left: 1rem;
            position: relative;

        }
    }

    .active{
        color: rgba(63, 92, 156, 1) !important;
        i{
            color: rgba(63, 92, 156, 1) !important;
        }
        &::before{
            content: "";
            position: absolute;
            left: 0;
            top: 0;
            width: 4px;
            height: 100%;
            background: rgba(63, 92, 156, 1);
            border-radius: 0 10px 10px 0;
        }
`;
export default Navigation