import React from 'react';
import CRTFrame from './components/common/CRTFrame';
import Keypad from './components/common/Keypad';
import ReceiptDisplay from './components/atm/ReceiptDisplay';
import LoginView from './views/LoginView';
import DashboardView from './views/DashboardView';
import TransactionView from './views/TransactionView';
import { useATM } from './hooks/useATM';

const App = () => {
  const {
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
  } = useATM();

  return (
    <CRTFrame>
      {/* SCREEN AREA */}
      <div className="relative bg-black border-4 border-gray-900 rounded-lg p-6 min-h-[400px] flex flex-col items-center justify-center glow-text border-double">
        
        {/* TOP BAR */}
        <div className="absolute top-0 left-0 w-full p-2 bg-atm-green-glow text-atm-black text-xs font-bold flex justify-between px-4">
          <span>ATM SYSTEM v1.1.0</span>
          <span>{new Date().toLocaleDateString()}</span>
        </div>

        {/* DYNAMIC VIEWS */}
        {view === 'login' && (
          <LoginView pin={pin} message={message} onLogin={handleLogin} />
        )}

        {view === 'dashboard' && (
          <DashboardView message={message} onNavigate={navigateTo} onExit={logout} />
        )}

        {(view === 'withdraw' || view === 'deposit') && (
          <TransactionView 
            type={view} 
            amount={amount} 
            message={message === 'PLEASE INSERT YOUR PIN' ? '' : message} 
            onCancel={() => navigateTo('dashboard')}
            onConfirm={view === 'withdraw' ? handleWithdraw : handleDeposit}
          />
        )}

        {view === 'receipt' && (
          <ReceiptDisplay receipt={receipt} onContinue={() => navigateTo('dashboard')} />
        )}
      </div>

      {/* KEYPAD AREA */}
      <Keypad 
        onNumber={handleKeypadNumber} 
        onClear={clearKeypad} 
        onEnter={view === 'login' ? handleLogin : () => {}} 
      />
    </CRTFrame>
  );
};

export default App;
