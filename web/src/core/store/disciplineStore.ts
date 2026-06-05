import { create } from 'zustand';
import { disciplineService } from '../services/disciplineService';
import { CreateDiscipline, Discipline } from '@/types/types';

// Define the shape of your store's state
interface DisciplineState {
  disciplines: Discipline[];
  currentDiscipline: Discipline | null;
  isLoading: boolean;
  error: string | null;

  // Actions
  listAll: () => Promise<void>;
  findById: (id: number) => Promise<void>;
  createDiscipline: (data: CreateDiscipline) => Promise<boolean>;
  // updateDiscipline: (id: number, data: CreateDiscipline) => Promise<boolean>;
  // deleteDiscipline: (id: number) => Promise<boolean>;
  clearError: () => void;
}

export const useDisciplineStore = create<DisciplineState>((set, get) => ({
  disciplines: [],
  currentDiscipline: null,
  isLoading: false,
  error: null,

  clearError: () => set({ error: null }),

  listAll: async () => {
    set({ isLoading: true, error: null });
    try {
      const data = await disciplineService.listAll();
      set({ disciplines: data, isLoading: false });
    } catch (err: any) {
      set({
        error: err.message || 'Failed to fetch disciplines',
        isLoading: false,
      });
    }
  },

  findById: async (id) => {
    set({ isLoading: true, error: null });
    try {
      const data = await disciplineService.findById({ id });
      set({ currentDiscipline: data, isLoading: false });
    } catch (err: any) {
      set({
        error: err.message || 'Failed to fetch discipline details',
        isLoading: false,
      });
    }
  },

  createDiscipline: async (disciplineData) => {
    set({ isLoading: true, error: null });
    try {
      const newDiscipline = await disciplineService.create(disciplineData);
      set((state) => ({
        disciplines: [...state.disciplines, newDiscipline],
        isLoading: false,
      }));
      return true;
    } catch (err: any) {
      set({
        error: err.message || 'Failed to create discipline',
        isLoading: false,
      });
      return false;
    }
  },

  // updateDiscipline: async (id, updateData) => {
  //   set({ isLoading: true, error: null });
  //   try {
  //     const updated = await disciplineService.update(id, updateData);
  //     set((state) => ({
  //       disciplines: state.disciplines.map((d) => (d.id === id ? updated : d)),
  //       currentDiscipline:
  //         state.currentDiscipline?.id === id
  //           ? updated
  //           : state.currentDiscipline,
  //       isLoading: false,
  //     }));
  //     return true;
  //   } catch (err: any) {
  //     set({
  //       error: err.message || 'Failed to update discipline',
  //       isLoading: false,
  //     });
  //     return false;
  //   }
  // },
}));
