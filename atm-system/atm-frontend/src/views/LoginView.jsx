import React from 'react';
import { CreditCard } from 'lucide-react';

const LoginView = ({ pin, message, onLogin }) => {
  return (
    <div className="text-center space-y-6">
      <CreditCard size={64} className="mx-auto animate-pulse" />
      <h2 className="text-2xl font-bold tracking-widest uppercase">{message}</h2>
      <div className="flex justify-center space-x-4">
        {[...Array(4)].map((_, i) => (
          <div key={i} className={`w-12 h-16 border-2 border-atm-green flex items-center justify-center text-4xl ${pin.length > i ? 'bg-atm-green text-atm-black' : ''}`}>
            {pin.length > i ? '*' : ''}
          </div>
        ))}
      </div>
      <p className="text-sm opacity-60">ENTER PIN USING KEYPAD BELOW</p>
      {pin.length === 4 && (
        <button onClick={onLogin} className="mt-4 bg-atm-green text-atm-black px-8 py-2 font-bold hover:bg-white transition-colors">
          PROCESS PIN
        </button>
      )}
    </div>
  );
};

export default LoginView;
