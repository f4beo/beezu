import Button from "@/components/ui/button/button";
import styles from './logoutbutton.module.css'

export default function LogoutButton() {
  return(
    <Button outlined className={styles.logoutButton}> Sair </Button>
  )
}
