import { Card } from '@/components/ui/card';
import { Activity } from '@/types/types';
import styles from './taskCard.module.css';
import HexagonCheckbox from '../hex-checkbox/hexCheckbox';

interface TaskCardProps {
  task: Activity;
}

export default function TaskCard({ task }: TaskCardProps) {
  const date = new Date(task.deadline || '');

  const formattedDeadline = new Intl.DateTimeFormat('pt-BR', {
    day: '2-digit',
    month: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false,
  })
    .format(date)
    .replace(', ', ' - ');

  return (
    <Card className={styles.container}>
      <HexagonCheckbox />
      <Card.Content className={styles.infoWrapper}>
        <h3 className={styles.taskTitle}>{task.title}</h3>
        <div className={styles.bottomInfo}>
          <span className={styles.bottomItem}>
            {task.type
              ? {
                  EXERCISE: 'Exercício',
                  PROJECT: 'Projeto',
                  EXAM: 'Prova',
                }[task.type] || task.type
              : 'Atividade'}
          </span>
          {task.deadline && (
            <span className={styles.bottomItem}>| {formattedDeadline}</span>
          )}
        </div>
      </Card.Content>
    </Card>
  );
}
