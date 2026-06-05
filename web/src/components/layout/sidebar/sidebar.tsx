'use client';

import LogoutButton from '../logout-button/logoutbutton';
import styles from './sidebar.module.css';
import { usePathname } from 'next/navigation';
import Link from 'next/link';

export default function Sidebar() {
  const pathname = usePathname();

  const navLinks = [
    { name: 'Dashboard', href: '/dashboard' },
    { name: 'Atividades', href: '/atividades' },
    { name: 'Calendário', href: '/calendario' },
    { name: 'Disciplinas', href: '/disciplinas' },
  ];

  return (
    <aside className={styles.sidebarContainer}>
      <div className={styles.topSection}>
        <img
          src="/beezu-logo-secondary.svg"
          alt="beezu"
          style={{
            width: '100%',
            padding: '0 2rem 1rem',
            borderBottom: 'solid 2px var(--primary-600)',
          }}
        />
        <div className={styles.beeSection}></div>
      </div>
      <nav className={styles.sidebarItems}>
        {navLinks.map((link) => {
          const isActive = pathname === link.href;
          return (
            <Link
              href={link.href}
              key={link.name}
              className={`${isActive ? styles.sidebarItemActive : styles.sidebarItem}`}
            >
              {link.name}
            </Link>
          );
        })}
      </nav>
      <div className={styles.bottomItems}>
        <Link href="/" key="configurações" className={styles.sidebarItem}>
          Configurações
        </Link>
        <span className={styles.divider}></span>
        <LogoutButton />
      </div>
    </aside>
  );
}
