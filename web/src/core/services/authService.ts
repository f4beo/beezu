import { api } from './api';
import { UserLogin } from '@/types/types';
import { UserRegister } from '@/types/types';
import Cookies from 'js-cookie';

export const authService = {
  login: async (credentials: UserLogin) => {
    try {
      const response = await api.post('/auth/login', credentials);
      if (response.data.token) {
        Cookies.set('token', response.data.token, { expires: 7 });
      }
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  register: async (data: UserRegister) => {
    try {
      const response = await api.post('/auth/register', data);
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  logout: () => {
    Cookies.remove('token');
    // TODO: Rota de logout (quando tiver)
  },

  checkAuth: async () => {
    try {
      const response = await api.get('/users/me');
      return response.data;
    } catch (error) {
      Cookies.remove('token');
      throw error;
    }
  },
};
