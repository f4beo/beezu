'use client';

import { useState } from 'react';
import Image from 'next/image';
import styles from './hexCheckbox.module.css';

interface HexagonCheckboxProps {
  onChange?: (checked: boolean) => void;
  size?: number;
}

export default function HexagonCheckbox({
  onChange,
  size = 36,
}: HexagonCheckboxProps) {
  const [isChecked, setIsChecked] = useState(false);

  const handleToggle = () => {
    setIsChecked(!isChecked);
    if (onChange) onChange(!isChecked);
  };

  return (
    <label className={styles.label}>
      <input
        type="checkbox"
        className={styles.inputHidden}
        checked={isChecked}
        onChange={handleToggle}
      />

      <div className={styles.hexagonContainer}>
        <Image
          src={isChecked ? '/hex-checked.svg' : '/hex-unchecked.svg'}
          alt={isChecked ? 'Hexágono marcado' : 'Hexágono desmarcado'}
          width={size}
          height={size}
          priority
          className={styles.image}
        />
      </div>
    </label>
  );
}
