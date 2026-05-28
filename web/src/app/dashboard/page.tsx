import Sidebar from '@/components/layout/sidebar/sidebar';
import styles from './page.module.css';
import DisciplineCard from '@/components/ui/disciplineCard/disciplineCard';




export default function Dashboard() {

    


    return (
        <>

            <Sidebar />
            <div className={styles.layout}>
                
                
                
                <h1 className={styles.title}>Dashboard</h1>
                <main className={styles.container}>
                    <h2 className={styles.subtitle}>Minhas <br /> Disciplinas</h2>
                    <div className={styles.disciplineList}>

                       

                        <DisciplineCard
                            name="Matemática"
                            professor="Dr. João Silva" 
                            discipline_code="MAT101"
                            />

                        <DisciplineCard
                            name="Matemática"
                            professor="Dr. João Silva" 
                            discipline_code="MAT101"
                            />
                    
                    
                    
                    </div>
                </main>
                
                <section>

                </section>
            </div>
        </>        

    );
}