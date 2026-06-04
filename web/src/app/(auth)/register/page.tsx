'use client';

import CardAuth from '@/components/ui/cardAuth/cardAuth';
import styles from './page.module.css';
import Button from '@/components/ui/button/button';
import { useAuth } from '@/core/hooks/useAuth';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { UserLogin } from '@/types/types';
import { authService } from '@/core/services/authService';

export default function LoginPage() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
  });
  const [errors, setErrors] = useState({
    name: '',
    email: '',
    password: '',
    others: '',
  });

  const validateFields = () => {
    let newErrors = { name: '', email: '', password: '', others: '' };
    let isValid = true;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!formData.name.trim()) {
      newErrors.name = 'O campo nome é obrigatório.';
      isValid = false;
    }

    if (!formData.email.trim()) {
      newErrors.email = 'O campo e-mail é obrigatório.';
      isValid = false;
    } else if (!emailRegex.test(formData.email)) {
      newErrors.email = 'Insira um formato de e-mail válido.';
      isValid = false;
    }

    if (!formData.password.trim()) {
      newErrors.password = 'O campo senha é obrigatório.';
      isValid = false;
    }

    setErrors(newErrors);

    return isValid;
  };

  const router = useRouter();

  const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    setErrors({ name: '', email: '', password: '', others: '' });
    try {
      validateFields();
      await authService.register(formData);
      router.push('/login');
    } catch (err) {
      setErrors((prev) => ({
        ...prev,
        others: 'Falha no registro. Verifique suas credenciais.',
      }));
      console.log(err);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const name = e.target.name as keyof UserLogin;
    const { value } = e.target;
    console.log(e.target.value);

    setFormData({ ...formData, [name]: value });
    if (errors[name]) setErrors({ ...errors, [name]: '' });
  };

  return (
    <>
      <CardAuth>
        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.inputGroup}>
            <label>Nome:</label>
            <input
              className={styles.input}
              name="name"
              type="name"
              onChange={handleChange}
            />
            {errors.name && (
              <span className={styles.errorSpan}>{errors.name}</span>
            )}
          </div>

          <div className={styles.inputGroup}>
            <label>E-mail:</label>
            <input
              className={styles.input}
              name="email"
              type="email"
              onChange={handleChange}
            />
            {errors.email && (
              <span className={styles.errorSpan}>{errors.email}</span>
            )}
          </div>

          <div className={styles.inputGroup}>
            <label>Senha:</label>
            <input
              className={styles.input}
              name="password"
              type="password"
              onChange={handleChange}
            />
            {errors.password && (
              <span className={styles.errorSpan}>{errors.password}</span>
            )}
          </div>

          <Button className={styles.botao} size={'large'}>
            Entrar
          </Button>
          {errors.others && (
            <span className={styles.errorSpan}>{errors.others}</span>
          )}
        </form>
        <span className={styles.authRedirect}>
          Já tem uma conta? <a href="/login">Entrar</a>
        </span>
      </CardAuth>
    </>
  );
}
