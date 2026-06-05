'use client';

import { useDisciplineStore } from '@/core/store/disciplineStore';
import React from 'react';
import Image from 'next/image';
import { useEffect } from 'react';
import styles from './page.module.css';
// import TaskList from '@/components/layout/tasklist/tasklist';

interface PageProps {
  params: Promise<{ id: number }>;
}

export default function DisciplineDetailPage({ params }: PageProps) {
  const { isLoading, error, findById, currentDiscipline } =
    useDisciplineStore();
  const { id } = React.use(params);

  useEffect(() => {
    findById(id);
  }, [id, findById]);

  if (isLoading) return <h1>Loading details from store...</h1>;
  if (error) return <h1>Error: {error}</h1>;
  if (!currentDiscipline) return <h1>Nenhuma disciplina encontrada.</h1>;
  return (
    <div className={styles.container}>
      <div className={styles.topSection}>
        <div className={styles.iconTitle}>
          <Image src="/books.svg" alt="icone" width={50} height={50} />
          <h1 className={styles.disciplineName}>{currentDiscipline.name}</h1>
        </div>
        <h2 className={styles.subtitle}>Prof. {currentDiscipline.professor}</h2>
      </div>
      <div className={styles.taskSection}>
        <div className={styles.taskSectionHeader}>
          <h3 className={styles.taskSectionHeaderTitle}>Atividades</h3>
        </div>
        {/* <TaskList tasks={currentTasks}> */}
      </div>
    </div>
  );
}
