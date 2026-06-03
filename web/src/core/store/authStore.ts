import { User, UserLogin } from '@/types/types';
import { create } from 'zustand';
import { authService } from '../services/authService';

interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
  isLoading: boolean;

  actions: {
    login: (credentials: UserLogin) => Promise<void>;
    logout: () => void;
    checkAuth: () => Promise<void>;
  };
}

export const useAuthStore = create<AuthState>((set, get) => ({
  user: null,
  isAuthenticated: false,
  isLoading: true,

  actions: {
    login: async (credentials: UserLogin) => {
      try {
        set({ isLoading: true });

        await authService.login(credentials);
        await get().actions.checkAuth();
      } catch (error) {
        set({ isAuthenticated: false, user: null, isLoading: false });
      }
    },

    logout: () => {
      authService.logout();
      set({ isAuthenticated: false, user: null, isLoading: false });
    },

    checkAuth: async () => {
      try {
        set({ isLoading: true });
        const user = await authService.checkAuth();
        set({ isAuthenticated: true, user: user });
      } catch (error) {
        set({ isAuthenticated: false, user: null });
      } finally {
        set({ isLoading: false });
      }
    },
  },
}));
