import Sidebar from "@/components/layout/sidebar/sidebar";
import styles from './layout.module.css'

export default function StudentLayout({
  children,
}: Readonly<{
    children: React.ReactNode;
  }>) {
  return (
    <section className={styles.container}>
      <Sidebar />
      <main className={styles.main}>
        {children}
      </main>
    </section>
  );
}
