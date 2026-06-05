import { Discipline } from '@/types/types';
import DisciplineCard from '../disciplineCard/disciplineCard';
import styles from './DisciplineList.module.css';

interface DisciplineListProps {
  disciplines: Discipline[];
  grid?: boolean;
}

export default function DisciplineList({
  disciplines,
  grid = false,
}: DisciplineListProps) {
  return (
    <ul className={grid ? styles.disciplineGrid : styles.disciplineList}>
      {disciplines.map((discipline) => {
        return (
          <li key={discipline.id}>
            <DisciplineCard
              id={discipline.id}
              name={discipline.name}
              professor={discipline.professor}
              disciplineCode={discipline.disciplineCode}
            />
          </li>
        );
      })}
    </ul>
  );
}
