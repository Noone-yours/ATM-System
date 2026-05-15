import React from 'react';

const Keypad = ({ onNumber, onClear, onEnter, isLoginView }) => {
  const keys = [1, 2, 3, 4, 5, 6, 7, 8, 9, 'CLEAR', 0, 'ENTER'];

  return (
    <div className="mt-8 grid grid-cols-3 gap-2 max-w-[300px] mx-auto">
      {keys.map((val) => (
        <button
          key={val}
          onClick={() => {
            if (typeof val === 'number') onNumber(val);
            else if (val === 'CLEAR') onClear();
            else if (val === 'ENTER') onEnter();
          }}
          className={`p-4 rounded shadow-inner font-bold text-xl active:scale-95 transition-transform ${
            val === 'CLEAR' ? 'bg-red-800 text-white' : 
            val === 'ENTER' ? 'bg-green-800 text-white' : 
            'bg-gray-700 text-gray-300'
          }`}
        >
          {val}
        </button>
      ))}
    </div>
  );
};

export default Keypad;
