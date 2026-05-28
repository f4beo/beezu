import { paths, components } from '.';

export type User = components['schemas']['UserResponseDTO'];
export type UserRegister =
  paths['/auth/register']['post']['requestBody']['content']['application/json'];
export type UserLogin =
  paths['/auth/login']['post']['requestBody']['content']['application/json'];

export type Activity = components['schemas']['ActivityResponseDTO'];
export type CreateActivity =
  paths['/activities/{disciplineId}']['post']['requestBody']['content']['application/json'];

export type Discipline = components['schemas']['DisciplineResponseDTO'];
export type CreateDiscipline =
  paths['/disciplines']['post']['requestBody']['content']['application/json'];

export type Hive = components['schemas']['HiveResponseDTO'];
