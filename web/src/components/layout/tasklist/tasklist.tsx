import { Activity } from '@/types/types';
import TaskCard from '../task/taskCard';

interface TaskListProps {
  tasks: Activity[];
}
export default function TaskList({ tasks }: TaskListProps) {
  return (
    <ul>
      {tasks.map((task: Activity) => (
        <li key={task.id}>
          <TaskCard task={task} />{' '}
        </li>
      ))}
    </ul>
  );
}
