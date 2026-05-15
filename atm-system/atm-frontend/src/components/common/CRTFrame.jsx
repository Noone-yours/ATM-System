import React from 'react';

const CRTFrame = ({ children }) => {
  return (
    <div className="min-h-screen bg-atm-black flex items-center justify-center p-4 py-12 md:py-4">
      <div className="crt-overlay"></div>
      <div className="scanline"></div>
      
      {/* ATM MACHINE OUTER FRAME */}
      <div className="relative w-full max-w-2xl bg-atm-dark border-8 border-gray-800 rounded-xl shadow-2xl p-8">
        {children}
      </div>
    </div>
  );
};

export default CRTFrame;
