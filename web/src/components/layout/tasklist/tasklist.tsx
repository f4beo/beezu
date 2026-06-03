import { Activity } from '@/types/types';
import TaskCard from '../task/taskCard';
import styles from './tasklist.module.css';

interface TaskListProps {
  tasks: Activity[];
}
export default function TaskList({ tasks }: TaskListProps) {
  return (
    <ul className={styles.container}>
      {tasks.map((task: Activity) => (
        <li key={task.id}>
          <TaskCard task={task} className={styles.taskItem} />
        </li>
      ))}
    </ul>
  );
}
