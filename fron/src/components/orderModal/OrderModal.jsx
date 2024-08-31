import React, { useState, useEffect } from "react";
import axios from "axios";
import { useCart } from "../cartContext/CartContext";
import PersonSVG from "../personSvg/personSvg";
import "./orderModal.css";

const OrderModal = ({ onClose }) => {
  const { setCart, walletBalance, placeOrder, token } = useCart();
  const [cartItems, setCartItems] = useState([]);
  const [totalAmount, setTotalAmount] = useState(0);

  useEffect(() => {
    document.body.style.overflow = "hidden";
    return () => {
      document.body.style.overflow = "";
    };
  }, []);

  useEffect(() => {
    const loadCart = async () => {
      try {
        const res = await axios.get("/api/get-current-user-cart", {
          baseURL: "http://10.4.56.79:8083",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("authToken")}`,
          },
        });
        const data = res.data;
        setCartItems(data.cartItems);
        setTotalAmount(data.totalPrice);
      } catch (err) {
        console.warn("Ошибка при загрузке корзины", err);
      }
    };
    loadCart();
  }, [token]);

  const handleIncrease = async (productid, genre) => {
    try {
      const res = await axios.post(
        `http://10.4.56.79:8083/api/products/remove-product/${productid}/1`,
        // { productid, genre },
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      const data = res.data;
      setCartItems(data.cartItems);
      setTotalAmount(data.totalPrice);
    } catch (err) {
      console.warn("Ошибка при увеличении количества товара", err);
    }
  };

  const handleDecrease = async (productid) => {
    try {
      const res = await axios.post(
        `http://10.4.56.79:8083/api/products/add-product/${productid}/1`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      ); // Изменение колчества товара +
      const data = res.data;
      setCartItems(data.cartItems);
      setTotalAmount(data.totalPrice);
    } catch (err) {
      console.warn("Ошибка при уменьшении количества товара", err);
    }
  };

  const handleDelete = async (productid) => {
    try {
      const res = await axios.post(
        `http://10.4.56.79:8082/api/remove-product/all/${productid}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      ); // Удаление количества товара +
      const data = res.data;
      setCartItems(data.cartItems); // Проверить передачу элементов
      setTotalAmount(data.totalPrice); // Проверить передачу элементов
    } catch (err) {
      console.warn("Ошибка при удалении товара", err);
    }
  };

  const handleOrder = async () => {
    if (cartItems.length === 0) {
      alert("Корзина пустая, добавьте товары");
      return;
    }

    try {
      const res = await placeOrder(); // Запрос
      if (res.status === 200) {
        onClose();
        setCart([]);
      } else {
        alert("Недостаточно средств для оформления заказа");
      }
    } catch (err) {
      console.warn("Ошибка при оформлении товара", err);
    }
  };

  return (
    <div id="popupCart" className="shop__popup popup" onClick={onClose}>
      <div className="popup-container" onClick={(e) => e.stopPropagation()}>
        <button className="close-button" onClick={onClose}>
          ×
        </button>
        <div className="popup__wrapp">
          <p className="popup__buck">
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
            Корзина
          </p>
          <button className="popup__delete" onClick={() => setCart([])}>
            Очистить корзину
          </button>
          <p className="popup__bill wallet">
            <PersonSVG />
            Баланс: {walletBalance.toFixed(2)}
          </p>
        </div>
        {cartItems.length > 0 ? (
          <ul className="bill__list">
            {cartItems.map((product) => (
              <li key={product.productId} className="bill__item">
                <img
                  src={product.imgSrc}
                  alt={product.name}
                  className="bill__img"
                />
                <p className="bill__name">{product.name}</p>
                <div className="bill__count-wrapp">
                  <button
                    className="bill__btn-dec"
                    onClick={() => handleDecrease(product.productId)}
                  >
                    -
                  </button>
                  <span className="bill__count">{product.quantity}</span>
                  <button
                    className="bill__btn-inc"
                    onClick={() => handleIncrease(product.productId)}
                  >
                    +
                  </button>
                </div>
                <p className="bill__name">
                  {(product.price * product.quantity).toFixed(2)}
                </p>
                <button
                  className="btn__delete"
                  onClick={() => handleDelete(product.productId)}
                >
                  X
                </button>
              </li>
            ))}
          </ul>
        ) : (
          <p>Корзина пуста...</p>
        )}
        <div className="bill__wrapp">
          <span className="bill__result">
            Итого: <span>{totalAmount.toFixed(2)}</span>
          </span>
          <button
            className="bill__btn-order"
            disabled={totalAmount > walletBalance || cartItems.length === 0}
            onClick={handleOrder}
          >
            Оформить заказ
          </button>
          {totalAmount > walletBalance && (
            <p className="error-text ">
              Недостаточно средств на вашем счету для оформления заказа.
            </p>
          )}
        </div>
      </div>
    </div>
  );
};

export default OrderModal;
