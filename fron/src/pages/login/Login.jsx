import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../components/authContext/authContext";
import axios from "axios";

import "./login.css";

const Login = () => {
  const [formData, setFormData] = useState({
    login: "",
    password: "",
    confirmPassword: "",
    email: "",
    firstName: "",
    lastName: "",
  });

  const [errors, setErrors] = useState({
    passwordMatch: "",
    requiredFields: {
      firstName: "",
      lastName: "",
      email: "",
    },
    general: "",
  });

  const [isAuth, setIsAuth] = useState(true);
  const { login } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    // Обновление ошибок при изменении состояния формы
    const { password, confirmPassword, firstName, lastName, email } = formData;
    const newErrors = { ...errors };

    if (!isAuth && password !== confirmPassword) {
      newErrors.passwordMatch = "Пароли не совпадают";
    } else {
      newErrors.passwordMatch = "";
    }

    if (!isAuth) {
      newErrors.requiredFields = {
        firstName: firstName ? "" : "Имя обязательно для заполнения",
        lastName: lastName ? "" : "Фамилия обязательна для заполнения",
        email: email ? "" : "Email обязателен для заполнения",
      };
    }

    setErrors(newErrors);
  }, [formData, isAuth]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const { login: userLogin, password } = formData;

    const requestData = {
      username: userLogin,
      password: password,
    };

    try {
      const response = await axios.post(
        "http://10.4.56.79:8082/api/all/login",
        requestData
      );

      if (response.status === 200) {
        const token = response.data;
        localStorage.setItem("authToken", token);
        login(token);
        navigate("/");
      } else {
        setErrors((prevErrors) => ({
          ...prevErrors,
          general: response.data.message || "Ошибка авторизации",
        }));
      }
    } catch (error) {
      console.error("Login error:", error);
      setErrors((prevErrors) => ({
        ...prevErrors,
        general: "Ошибка соединения с сервером",
      }));
    }
  };

  const handleToggle = () => {
    setIsAuth((prevState) => !prevState);
  };

  return (
    <div className="login__container container">
      <form method="post" className="login__form" onSubmit={handleSubmit}>
        {!isAuth && (
          <>
            <label htmlFor="firstName">
              <p className="label-field">Имя</p>
              <input
                name="firstName"
                type="text"
                className="login__input"
                value={formData.firstName}
                onChange={handleChange}
              />
              {errors.requiredFields.firstName && (
                <p className="error">{errors.requiredFields.firstName}</p>
              )}
            </label>
          </>
        )}

        {!isAuth && (
          <>
            <label htmlFor="lastName">
              <p className="label-field">Фамилия</p>
              <input
                name="lastName"
                type="text"
                className="login__input"
                value={formData.lastName}
                onChange={handleChange}
              />
              {errors.requiredFields.lastName && (
                <p className="error">{errors.requiredFields.lastName}</p>
              )}
            </label>
          </>
        )}
        <label htmlFor="login">
          <p className="label-field">Логин</p>
          <input
            name="login"
            type="text"
            className="login__input"
            value={formData.login}
            onChange={handleChange}
          />
        </label>

        {!isAuth && (
          <label htmlFor="email">
            <p className="label-field">Емаил</p>
            <input
              name="email"
              type="email"
              className="login__input"
              value={formData.email}
              onChange={handleChange}
            />
            {errors.requiredFields.email && (
              <p className="error">{errors.requiredFields.email}</p>
            )}
          </label>
        )}

        <label htmlFor="password">
          <p className="label-field">Пароль</p>
          <input
            name="password"
            type="password"
            className="login__input"
            value={formData.password}
            onChange={handleChange}
          />
        </label>

        {!isAuth && (
          <>
            <label htmlFor="confirmPassword">
              <p className="label-field">Повторите пароль</p>
              <input
                name="confirmPassword"
                type="password"
                className="login__input"
                value={formData.confirmPassword}
                onChange={handleChange}
              />
              {errors.passwordMatch && (
                <p className="error">{errors.passwordMatch}</p>
              )}
            </label>
          </>
        )}

        {errors.general && <p className="error">{errors.general}</p>}

        <button className="login__btn" type="submit">
          {isAuth ? "Войти" : "Зарегистрироваться"}
        </button>
      </form>
      <button
        onClick={handleToggle}
        className={`login__btn ${isAuth ? "tab-active" : ""}`}
      >
        {isAuth ? "Регистрация" : "Авторизация"}
      </button>
    </div>
  );
};

export default Login;
