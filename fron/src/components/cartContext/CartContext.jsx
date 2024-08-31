import React, { createContext, useState, useContext, useEffect } from "react";
import { api, api8082, api8083, api8084 } from "../api/api";

const CartContext = createContext(null);

export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState([]);
  const [walletBalance, setWalletBalance] = useState(0);
  const [isAuthorized, setIsAuthorized] = useState(false);
  const [userRole, setUserRole] = useState(null);
  const [userInfo, setUserInfo] = useState({
    userId: null,
    username: null,
    email: null,
    firstName: null,
    lastName: null,
    userRole: null,
    balance: null,
  });
  const [orders, setOrders] = useState([]);

  const checkAndSetToken = () => {
    const token = localStorage.getItem("authToken");
    if (token && token.trim() !== "") {
      api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
      setIsAuthorized(true);
    } else {
      setIsAuthorized(false);
      delete api.defaults.headers.common["Authorization"];
    }
  };

  const fetchUserData = async () => {
    checkAndSetToken();
    try {
      const res = await api8082.get("/api/current-user-info");
      const data = res.data;
      setUserInfo({
        userId: data.userId,
        username: data.username,
        email: data.email,
        firstName: data.firstName,
        lastName: data.lastName,
        userRole: data.userRole,
        balance: data.balance,
      });
      setWalletBalance(data.balance);
      setUserRole(data.userRole);
    } catch (err) {
      console.error("Ошибка при получении данных пользователя", err);
    }
  };

  const fetchCart = async () => {
    checkAndSetToken();
    try {
      const cartResponse = await api8083.get("/api/get-current-user-cart");
      setCart(cartResponse.data.cartItems);
    } catch (err) {
      console.error("Ошибка загрузки корзины", err);
    }
  };

  const fetchWalletBalance = async () => {
    checkAndSetToken();
    try {
      const balanceResponse = await api8082.get("/api/current-user-info");
      setWalletBalance(balanceResponse.data.balance);
    } catch (err) {
      console.error("Ошибка загрузки баланса", err);
    }
  };

  const fetchCartBalance = async () => {
    checkAndSetToken();
    try {
      await Promise.all([fetchCart(), fetchWalletBalance()]);
    } catch (err) {
      console.error("Ошибка при загрузке корзины и баланса", err);
    }
  };

  const placeOrder = async () => {
    checkAndSetToken();
    try {
      const res = await api8082.post("/api/place-order");
      if (res.status === 200) {
        setCart([]); // Очищаем корзину после успешного заказа
        fetchCartBalance(); // Обновляем корзину и баланс
        return true;
      } else {
        return false;
      }
    } catch (err) {
      console.error("Ошибка при оформлении заказа", err);
      return false;
    }
  };

  const addItemToCart = async (productId) => {
    checkAndSetToken();
    try {
      const res = await api8082.post(`/api/add-product/${productId}`);
      if (res.status === 200) {
        setCart(res.data.items); // Обновляем корзину с полученными данными
      } else {
        console.error("Ошибка при добавлении товара в корзину", res);
      }
    } catch (err) {
      console.error("Ошибка при добавлении товара в корзину", err);
    }
  };

  const fetchUserRole = async () => {
    checkAndSetToken();
    try {
      const res = await api8082.get("/api/current-user-info");
      setUserRole(res.data.userRole);
    } catch (err) {
      console.error("Ошибка при получении роли", err);
    }
  };

  const fetchOrders = async () => {
    checkAndSetToken();
    try {
      const res = await api8084.get("/api/get-by-current-user");
      if (res.status === 200) {
        setOrders(res.data);
      }
    } catch (err) {
      console.error("Ошибка при загрузке заказов", err);
    }
  };

  useEffect(() => {
    fetchUserRole();
    fetchOrders();
    fetchUserData();
  }, []);

  return (
    <CartContext.Provider
      value={{
        cart,
        setCart,
        walletBalance,
        isAuthorized,
        placeOrder,
        addItemToCart,
        userRole,
        fetchCartBalance,
        userInfo,
        orders,
        fetchCart,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => useContext(CartContext);
