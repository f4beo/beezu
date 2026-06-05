import Button from '@/components/ui/button/button';
import styles from './logoutbutton.module.css';
import { authService } from '@/core/services/authService';

export default function LogoutButton() {
  const handleOnClick = () => {
    authService.logout();
    window.location.href = '/login';
  };
  return (
    <Button outlined className={styles.logoutButton} onClick={handleOnClick}>
      Sair
    </Button>
  );
}
