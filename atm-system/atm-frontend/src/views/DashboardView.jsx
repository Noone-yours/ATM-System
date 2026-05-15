import React from 'react';
import { LogOut, ArrowDownToLine, ArrowUpFromLine } from 'lucide-react';

const DashboardView = ({ message, onNavigate, onExit }) => {
  return (
    <div className="w-full h-full flex flex-col items-center space-y-8">
      <h2 className="text-2xl font-bold uppercase border-b-2 border-atm-green pb-2 w-full text-center">{message}</h2>
      
      <div className="grid grid-cols-2 gap-8 w-full">
        <button onClick={() => onNavigate('withdraw')} className="flex items-center justify-center space-x-2 border-2 border-atm-green p-4 hover:bg-atm-green hover:text-atm-black transition-all">
          <ArrowUpFromLine size={24} />
          <span className="font-bold">WITHDRAW</span>
        </button>
        <button onClick={() => onNavigate('deposit')} className="flex items-center justify-center space-x-2 border-2 border-atm-green p-4 hover:bg-atm-green hover:text-atm-black transition-all">
          <ArrowDownToLine size={24} />
          <span className="font-bold">DEPOSIT</span>
        </button>
        <button onClick={onExit} className="flex items-center justify-center space-x-2 border-2 border-red-900 p-4 hover:bg-red-900 text-red-500 hover:text-white transition-all">
          <LogOut size={24} />
          <span className="font-bold">EXIT</span>
        </button>
      </div>
    </div>
  );
};

export default DashboardView;
