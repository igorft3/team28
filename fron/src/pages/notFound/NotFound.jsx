import React from "react";
import { Link } from "react-router-dom";
import "./notFound.css";

const NotFound = () => {
  return (
    <div className="not-found">
      <h1>404 - Страница не найдена</h1>
      <p>Извиние мы не смогли...</p>
      <Link to="/about">Вернуться назад</Link>
    </div>
  );
};

export default NotFound;
