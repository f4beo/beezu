'use client';

import styles from './page.module.css';
import DisciplineList from '@/components/ui/disciplineList/DisciplineList';
import TaskList from '@/components/layout/tasklist/tasklist';
import { Activity } from '@/types/types';
import { useDisciplineStore } from '@/core/store/disciplineStore';
import { useEffect } from 'react';
import Link from 'next/link';

export default function Dashboard() {
  const { isLoading, error, listAll, disciplines } = useDisciplineStore();

  useEffect(() => {
    listAll();
  }, [listAll]);

  if (isLoading) return <div> Loading...</div>;
  if (error) return <div>Error: {error}</div>;
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
  ];
  return (
    <>
      <h1 className={styles.title}>Dashboard</h1>
      <div className={styles.container}>
        <div className={styles.disciplineSection}>
          <h2 className={styles.subtitle}>
            <Link href={'disciplinas/'}>Minhas Disciplinas →</Link>
          </h2>
          <DisciplineList disciplines={disciplines} grid />
        </div>
        <div className={styles.taskSection}>
          <h2 className={styles.subtitle}>
            <Link href={'atividades/'}>Próximas Atividades →</Link>
          </h2>
          <TaskList tasks={tasks} />
        </div>
      </div>
    </>
  );
}
