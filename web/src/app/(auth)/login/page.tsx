'use client';

import CardAuth from '@/components/ui/cardAuth/cardAuth';
import styles from './page.module.css';
import Button from '@/components/ui/button/button';
import { useAuth } from '@/core/hooks/useAuth';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { UserLogin } from '@/types/types';

export default function LoginPage() {
  const [formData, setFormData] = useState({ email: '', password: '' });
  const [errors, setErrors] = useState({ email: '', password: '', others: '' });

  const validateFields = () => {
    console.trace('de onde fui chamada?');
    let newErrors = { email: '', password: '', others: '' };
    let isValid = true;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    console.log(formData);

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

  const { login, isLoading } = useAuth();
  const router = useRouter();

  const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    setErrors({ email: '', password: '', others: '' });
    try {
      console.log(errors);
      validateFields();
      console.log(errors);
      await login(formData);
      router.push('/dashboard');
    } catch (err) {
      setErrors((prev) => ({
        ...prev,
        others: 'Falha no login. Verifique suas credenciais.',
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

          <Button className={styles.botao}>Entrar</Button>
          {errors.others && (
            <span className={styles.errorSpan}>{errors.others}</span>
          )}
        </form>
      </CardAuth>
    </>
  );
}
