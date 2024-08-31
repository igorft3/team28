import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";
// import { useAuth } from "../authContext/authContext";
import OrderModal from "../orderModal/orderModal";
import LogoSVG from "../logoSvg/logoSvg";
import PersonSVG from "../personSvg/personSvg";
import "./header.css";

const Header = () => {
  const [isOrderModalOpen, setIsOrderModalOpen] = useState(false);
  const location = useLocation();

  const isLoginPage = location.pathname === "/login";

  const toggleOrderModal = () => {
    setIsOrderModalOpen(!isOrderModalOpen);
  };

  return (
    <header>
      <div className="header__wrapp-strip">
        <div className="stripe"></div>
        <div className="stripe"></div>
        <div className="stripe"></div>
        <div className="stripe"></div>
        <div className="stripe"></div>
      </div>
      <div className="container header__container">
        <Link to="/" className="header__logo">
          <LogoSVG />
          e-commerce
        </Link>
        {!isLoginPage && (
          <>
            <nav className="header__nav">
              <Link to="/" className="nav__link">
                Каталог
              </Link>
            </nav>
            <div className="header__wrapp">
              <button
                onClick={toggleOrderModal}
                className="header__buck"
                id="billBtn"
              >
                <svg
                  className="cart-svg"
                  width="800px"
                  height="800px"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path d="M6.787 15.981l14.11-1.008L23.141 6H5.345L5.06 4.37a1.51 1.51 0 0 0-1.307-1.23l-2.496-.286-.114.994 2.497.286a.502.502 0 0 1 .435.41l1.9 10.853-.826 1.301A1.497 1.497 0 0 0 6 18.94v.153a1.5 1.5 0 1 0 1 0V19h11.5a.497.497 0 0 1 .356.15 1.502 1.502 0 1 0 1.074-.08A1.497 1.497 0 0 0 18.5 18H6.416a.5.5 0 0 1-.422-.768zM19.5 21a.5.5 0 1 1 .5-.5.5.5 0 0 1-.5.5zm-13 0a.5.5 0 1 1 .5-.5.5.5 0 0 1-.5.5zM21.86 7l-1.757 7.027-13.188.942L5.52 7z" />
                  <path fill="none" d="M0 0h24v24H0z" />
                </svg>
                Корзина<span className="cart-count"></span>
              </button>
              <Link to="/profile" className="header__person-account">
                <PersonSVG />
                Мой аккаунт
              </Link>
            </div>
          </>
        )}
      </div>
      {isOrderModalOpen && <OrderModal onClose={toggleOrderModal} />}
    </header>
  );
};

export default Header;
