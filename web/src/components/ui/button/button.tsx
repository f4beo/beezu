import styles from "./button.module.css";
import { ReactNode, ButtonHTMLAttributes } from "react";

type ButtonSize = "small" | "medium" | "large";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    outlined?: boolean;
    fullWidth?: boolean;
    children?: ReactNode;
    leftIcon?: ReactNode;
    rightIcon?: ReactNode;
    size?: ButtonSize;
}

export default function Button({ 
    outlined = false,
    fullWidth = false,
    size = "medium",
    leftIcon,
    rightIcon,
    children,
    disabled,
    className,
    ...rest
}: ButtonProps) {
    const classNames = [
        styles.button,
        outlined ? styles.outlined : styles.filled,
        styles[size],
        fullWidth ? styles.fullWidth : "",
        disabled ? styles.disabled : "",
        className,
    ].filter(Boolean).join(" ");

    return (
        <button className={classNames} disabled={disabled} {...rest}>
            {leftIcon && <span>{leftIcon}</span>}
            {children}
            {rightIcon && <span>{rightIcon}</span>}
        </button>   
    );
}
