import React from 'react';

const ReceiptDisplay = ({ receipt, onContinue }) => {
  return (
    <div className="text-center space-y-4 w-full">
      <h2 className="text-xl font-bold">TRANSACTION COMPLETE</h2>
      <pre className="bg-atm-dark border border-atm-green p-4 text-left font-mono text-sm leading-tight inline-block mx-auto max-w-full">
        {receipt}
      </pre>
      <div>
        <button onClick={onContinue} className="bg-atm-green text-atm-black px-12 py-2 font-bold uppercase hover:bg-white">CONTINUE</button>
      </div>
    </div>
  );
};

export default ReceiptDisplay;
