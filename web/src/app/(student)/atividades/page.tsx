import TaskList from '@/components/layout/tasklist/tasklist';
import { Activity } from '@/types/types';
import styles from './page.module.css';

export default function ActivitiesPage() {
  const tasks: Activity[] = [
    {
      id: 1,
      title: 'TypeScript API Setup',
      description: 'Create models and define endpoints.',
      type: 'PROJECT',
      createdAt: new Date().toISOString(),
      deadline: '2026-06-05T23:59:59Z',
    },
    {
      id: 2,
      title: 'Database Schema Lab',
      description: 'Solve relations exercises using PostgreSQL syntax.',
      type: 'EXERCISE',
      createdAt: new Date().toISOString(),
      deadline: '2026-06-06T12:00:00Z',
    },
    {
      id: 3,
      title: 'Midterm Backend Exam',
      description:
        'Complete theoretical and practical server development test.',
      type: 'EXAM',
      createdAt: new Date().toISOString(),
      deadline: '2026-06-08T09:30:00Z',
    },
    {
      id: 4,
      title: 'Auth Implementation',
      description: 'Integrate JWT tokens and route guards.',
      type: 'PROJECT',
      createdAt: new Date().toISOString(),
      deadline: '2026-06-12T18:00:00Z',
    },
    {
      id: 5,
      title: 'Algorithms Homework',
      description: 'Implement search algorithms and evaluate space complexity.',
      type: 'EXERCISE',
      createdAt: new Date().toISOString(),
      deadline: '2026-06-13T23:59:59Z',
    },
    {
      id: 6,
      title: 'UI Component Library',
      description: 'Publish internal button and input design tokens.',
      type: 'PROJECT',
      createdAt: new Date().toISOString(),
      deadline: '2026-06-15T20:00:00Z',
    },
    {
      id: 7,
      title: 'Final Architecture Test',
      description: 'Answer system design questions under exam conditions.',
      type: 'EXAM',
      createdAt: new Date().toISOString(),
      deadline: '2026-06-17T15:00:00Z',
    },
    {
      id: 8,
      title: 'React State Drill',
      description: 'Practice prop drilling fixes using Context API.',
      type: 'EXERCISE',
      createdAt: new Date().toISOString(),
      deadline: '2026-06-19T17:00:00Z',
    },
    {
      id: 9,
      title: 'Unit Testing Challenge',
      description: 'Write Jest mocks for helper validation functions.',
      type: 'EXERCISE',
      createdAt: new Date().toISOString(),
      deadline: '2026-06-22T23:59:59Z',
    },
    {
      id: 10,
      title: 'DevOps Deployment Exam',
      description:
        'Configure and fix CI/CD pipelines in the sandbox environment.',
      type: 'EXAM',
      createdAt: new Date().toISOString(),
      deadline: '2026-06-25T14:00:00Z',
    },
  ];
  return (
    <section className={styles.container}>
      <h1 className={styles.title}>Minhas Atividades</h1>
      <div className={styles.taskListWrapper}>
        <TaskList tasks={tasks} />
      </div>
    </section>
  );
}
