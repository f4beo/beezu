import Button from '../button/button';
import styles from './disciplineCard.module.css';
import Image from 'next/image';

type DisciplineCardProps = {
    name: string;
    professor: string;
    discipline_code: string;
}



export default function DisciplineCard({ name, professor, discipline_code }: DisciplineCardProps) {
    return(
        <div className={styles.card}>
            <div className={styles.aboutDiscipline}>
                <div className={styles.iconTitle}>
                    <Image src="/books.svg" alt="icone" width={25} height={25} />
                    <h3 className={styles.disciplineName}>{name}</h3>
                </div>

                <p className={styles.professor}>Prof.{professor}</p>
                <p className={styles.semestre}>{discipline_code}</p>

                <Button className={styles.enterButton}>Entrar</Button>
            </div>
        </div>
    );
}