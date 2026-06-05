'use client';

import DisciplineList from '@/components/ui/disciplineList/DisciplineList';
import styles from './page.module.css';
import { useEffect } from 'react';
import { useDisciplineStore } from '@/core/store/disciplineStore';

export default function DisciplinesPage() {
  const { isLoading, error, listAll, disciplines } = useDisciplineStore();

  useEffect(() => {
    listAll();
  }, [listAll]);

  if (isLoading) return <div> Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className={styles.main}>
      <h1 className={styles.title}> Minhas Disciplinas </h1>
      <DisciplineList grid disciplines={disciplines} />
    </div>
  );
}
