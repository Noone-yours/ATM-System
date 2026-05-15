import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
});

export const atmApi = {
  login: (accountNumber, pin) => api.post('/auth/login', { accountNumber, pin }),
  withdraw: (accountNumber, amount) => api.post('/withdraw', { accountNumber, amount }),
  deposit: (accountNumber, amount) => api.post('/deposit', { accountNumber, amount }),
};
