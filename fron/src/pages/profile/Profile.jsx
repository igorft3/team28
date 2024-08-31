import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { products } from "../../data";
import { useCart } from "../../components/cartContext/CartContext";
import "./profile.css";
import ManagerDashboard from "../../components/managerDashboard/ManagerDashboard ";
import OrderHistory from "../../components/orderHistory/OrderHistory";
import UserProfile from "../../components/userProfile/UserProfile";

const Profile = () => {
  const [userRole, setUserRole] = useState("ROLE_ADMIN");
  const [hasToken, setHasToken] = useState(true);
  const [activeTab, setActiveTab] = useState("profile");
  const [activeTabManager, setActiveTabManager] = useState("create");
  const { userInfo, orders } = useCart();
  const navigate = useNavigate();

  const handleTabClick = (tabName) => {
    setActiveTab(tabName);
  };

  const handleDeleteToken = () => {
    setHasToken(!hasToken);
    if (hasToken) {
      localStorage.removeItem("authToken");
      navigate("/login");
    }
  };
  return (
    <section className="profile__container container">
      <ul className="profile__list nav">
        <li
          onClick={() => handleTabClick("profile")}
          className={`profile__item ${
            activeTab === "profile" ? "tab-active" : ""
          }`}
        >
          Мой профиль
        </li>
        <li
          onClick={() => handleTabClick("history")}
          className={`profile__item ${
            activeTab === "history" ? "tab-active" : ""
          }`}
        >
          История заказов
        </li>
        {(userRole === "ROLE_ADMIN" || userRole === "ROLE_MANAGER") && (
          <li
            onClick={() => handleTabClick("manager")}
            className={`profile__item ${
              activeTab === "controlGoods" ? "tab-active" : ""
            }`}
          >
            Управление товарами
          </li>
        )}
        {(userRole === "ROLE_ADMIN" || userRole === "ROLE_MANAGER") && (
          <li
            onClick={() => handleTabClick("manager")}
            className={`profile__item ${
              activeTab === "controlOrders" ? "tab-active" : ""
            }`}
          >
            Управление заказами
          </li>
        )}
        {(userRole === "ROLE_ADMIN" || userRole === "ROLE_MANAGER") && (
          <li
            onClick={() => handleTabClick("manager")}
            className={`profile__item ${
              activeTab === "controlUsers" ? "tab-active" : ""
            }`}
          >
            Управление пользователя
          </li>
        )}
        <li className="profile__item" onClick={handleDeleteToken}>
          Выйти
        </li>
      </ul>
      <div className="profile__content">
        {activeTab === "profile" && <UserProfile userInfo={userInfo} />}
        {activeTab === "history" && <OrderHistory products={products} />}
        {activeTab === "manager" && <ManagerDashboard />}
      </div>
    </section>
  );
};

export default Profile;
