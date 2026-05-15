import React from 'react';
import { Banknote } from 'lucide-react';

const TransactionView = ({ type, amount, message, onCancel, onConfirm }) => {
  return (
    <div className="text-center space-y-6 w-full max-w-xs">
      <Banknote size={64} className="mx-auto" />
      <h2 className="text-2xl font-bold uppercase">{type} AMOUNT</h2>
      <div className="text-5xl font-bold border-b-2 border-atm-green p-4 bg-atm-dark">
        ${amount || '0'}
      </div>
      <div className="flex space-x-4">
        <button onClick={onCancel} className="flex-1 border border-atm-green p-2 hover:bg-atm-green hover:text-atm-black uppercase font-bold">CANCEL</button>
        <button onClick={onConfirm} className="flex-1 bg-atm-green text-atm-black p-2 hover:bg-white uppercase font-bold">CONFIRM</button>
      </div>
      <p className="text-red-500 text-sm font-bold">{message}</p>
    </div>
  );
};

export default TransactionView;
