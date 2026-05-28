
import CardAuth from "@/components/ui/cardAuth/cardAuth";
import styles from "./page.module.css";
import Button from "@/components/ui/button/button";

export default function SignPage() {
    return ( 
        <>  
            <CardAuth> 
                <div className={styles.inputGroup}>
                    <label>Nome Completo:</label>
                    <input className={styles.input} type="text" /> 
                </div>


                <div className={styles.inputGroup}>
                    <label>E-mail:</label>
                    <input className={styles.input} type="email" /> 
                </div>

                <div className={styles.inputGroup}>
                    <label>Senha:</label>
                    <input className={styles.input} type="password"/>
                </div>

                <Button className={styles.botao}>
                    Entrar
                </Button>
               
            </CardAuth>
        </>
    )

 }