import React from "react";
import "./productModal.css";

const ProductModal = ({ product, onClose }) => {
  const handleClickOutside = (e) => {
    if (e.target.className.includes("popup")) {
      onClose();
    }
  };

  return (
    <div
      id="popupItem"
      className="shop__popup popup"
      onClick={handleClickOutside}
    >
      <div className="popup-container">
        <button className="close-button" onClick={onClose}>
          ×
        </button>
        <h3 className="popup__title">{product.name}</h3>
        <p className="popup__desc">{product.description}</p>
        <img src={product.imgSrc} alt={product.name} className="popup__img" />
        <div className="popup__wrapp-btn">
          <p className="popup__price">
            Цена: <span>{product.price}</span>
          </p>
          <button className="popup__btn">Закажи себе такую</button>
        </div>
      </div>
    </div>
  );
};

export default ProductModal;
