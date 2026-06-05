import { api } from './api';
import { Discipline, CreateDiscipline } from '@/types/types';

export const disciplineService = {
  listAll: async () => {
    const res = await api.get('/disciplines');
    return res.data;
  },

  create: async (payload: CreateDiscipline) => {
    const res = await api.post('/disciplines', payload);
    return res.data;
  },

  findById: async ({ id }: Discipline) => {
    const res = await api.get(`/disciplines/${id}`);
    return res.data;
  },

  delete: async ({ id }: Discipline) => {
    const res = await api.delete(`/disciplines/${id}`);
    return res.data;
  },

  // TODO: Revisar essa logica
  // update: async ({ id, name, description, professor }: Discipline) => {
  //   const res = await api.patch(`/disciplines/${id}`, {
  //     name,
  //     description,
  //     professor,
  //   });
  //   return res;
  // },
};
