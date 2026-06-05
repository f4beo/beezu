'use client';

import Button from '../button/button';
import styles from './disciplineCard.module.css';
import Image from 'next/image';
import { Discipline } from '@/types/types';
import { Card } from '../card';
import { useRouter } from 'next/navigation';

export default function DisciplineCard({
  id,
  name,
  professor,
  disciplineCode,
}: Discipline) {
  const router = useRouter();
  const handleClick = () => {
    console.log(id);
    router.push(`/disciplinas/${id}`);
  };
  return (
    <Card className={styles.card}>
      <div className={styles.bannerSkeleton} />
      <Card.Content className={styles.aboutDiscipline}>
        <div className={styles.topSection}>
          <div className={styles.iconTitle}>
            <Image src="/books.svg" alt="icone" width={25} height={25} />
            <h3 className={styles.disciplineName}>{name}</h3>
          </div>
          <p className={styles.professor}>Prof.{professor}</p>
        </div>
        <p className={styles.code}>{disciplineCode}</p>

        <Button className={styles.enterButton} onClick={handleClick}>
          Entrar
        </Button>
      </Card.Content>
    </Card>
  );
}
