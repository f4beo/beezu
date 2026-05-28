import styles from './card.module.css';
import { ReactNode } from 'react';

export interface CardProps {
  children: ReactNode;
  className?: string;
}

export interface CardHeaderProps {
  title?: string;
  children?: ReactNode;
  clearBackground?: boolean;
  className?: string;
}

export interface CardContentProps {
  children: ReactNode;
  noPadding?: boolean;
  className?: string;
}

export interface CardFooterProps {
  children: ReactNode;
  className?: string;
}

export interface CardDividerProps {
  className?: string;
}

export function Card({ children, className = '' }: CardProps) {
  return <div className={`${styles.card} ${className}`}>{children}</div>;
}

export function CardHeader({
  title,
  children,
  clearBackground = false,
  className = '',
}: CardHeaderProps) {
  return (
    <div
      className={`${styles.header} ${clearBackground ? styles.headerNoBackground : ''} ${className}`}
    >
      {title && <h3 className={styles.headerTitle}>{title}</h3>}
      {children}
    </div>
  );
}

export function CardContent({
  children,
  noPadding = false,
  className = '',
}: CardContentProps) {
  return (
    <div
      className={`${styles.content} ${noPadding ? styles.contentNoPadding : ''} ${className}`}
    >
      {children}
    </div>
  );
}

export function CardFooter({ children, className = '' }: CardFooterProps) {
  return <div className={`${styles.footer} ${className}`}>{children}</div>;
}

export function CardDivider({ className = '' }: CardDividerProps) {
  return <div className={`${styles.divider} ${className}`} />;
}

export default Object.assign(Card, {
  Header: CardHeader,
  Content: CardContent,
  Footer: CardFooter,
  Divider: CardDivider,
});
