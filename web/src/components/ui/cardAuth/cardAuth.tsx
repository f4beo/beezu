import styles from './cardAuth.module.css';
import Image from 'next/image';
import imagemLogo from '../../../../public/beezu-logo-secondary.svg';
import { Children } from 'react';

type CardAuthProps = {
  children: React.ReactNode;
};

export default function CardAuth({ children }: CardAuthProps) {
  return (
    <div className={styles.container}>
      '{' '}
      <div className={styles.cardAuth}>
        <Image src={imagemLogo} alt="Logo da Beezu" className="logoBeezu" />
        {children}'
      </div>
    </div>
  );
}
