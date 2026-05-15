import { useState } from 'react';
import { atmApi } from '../api/atmApi';

export const useATM = () => {
  const [view, setView] = useState('login'); // login, dashboard, withdraw, deposit, receipt
  const [accountNumber, setAccountNumber] = useState('123456789');
  const [pin, setPin] = useState('');
  const [message, setMessage] = useState('PLEASE INSERT YOUR PIN');
  const [receipt, setReceipt] = useState(null);
  const [amount, setAmount] = useState('');

  const handleLogin = async () => {
    try {
      const response = await atmApi.login(accountNumber, pin);
      if (response.status === 200) {
        setView('dashboard');
        setMessage('WELCOME TO ATM SYSTEM');
      }
    } catch (error) {
      setMessage('INVALID PIN. TRY AGAIN.');
      setPin('');
    }
  };

  const handleWithdraw = async () => {
    try {
      const response = await atmApi.withdraw(accountNumber, parseFloat(amount));
      setReceipt(response.data);
      setView('receipt');
      setAmount('');
    } catch (error) {
      setMessage(error.response?.data || 'WITHDRAWAL FAILED');
    }
  };

  const handleDeposit = async () => {
    try {
      const response = await atmApi.deposit(accountNumber, parseFloat(amount));
      setReceipt(response.data);
      setView('receipt');
      setAmount('');
    } catch (error) {
      setMessage('DEPOSIT FAILED');
    }
  };

  const handleKeypadNumber = (num) => {
    if (view === 'login') {
      if (pin.length < 4) setPin(prev => prev + num);
    } else if (view === 'withdraw' || view === 'deposit') {
      setAmount(prev => prev + num);
    }
  };

  const clearKeypad = () => {
    if (view === 'login') setPin('');
    else setAmount('');
  };

  const navigateTo = (newView) => {
    setView(newView);
    if (newView === 'dashboard') setMessage('WELCOME TO ATM SYSTEM');
  };

  const logout = () => {
    setView('login');
    setPin('');
    setMessage('PLEASE INSERT YOUR PIN');
  };

  return {
    view,
    pin,
    message,
    receipt,
    amount,
    handleLogin,
    handleWithdraw,
    handleDeposit,
    handleKeypadNumber,
    clearKeypad,
    navigateTo,
    logout
  };
};
