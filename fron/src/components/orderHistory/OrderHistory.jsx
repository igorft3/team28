import React from "react";

const OrderHistory = ({ products }) => {
  return (
    <ul className="profile__list-history tabs">
      {products.map((product, index) => (
        <li key={index} className="profile__item-history">
          <ul className="profile__list-product">
            <li className="profile__item-product">
              <h3 className="profile__history-name">Фотография товара</h3>
              <img src={product.imgSrc} alt={`Image ${index}`} />
            </li>
            <li className="profile__item-product">
              <h3 className="profile__history-name">Название</h3>
              <p className="profile__history-value">{product.name}</p>
            </li>
            <li className="profile__item-product">
              <h3 className="profile__history-name">Цена</h3>
              <p className="profile__history-value">{product.price}</p>
            </li>
            <li className="profile__item-product">
              <h3 className="profile__history-name">Тип</h3>
              <p className="profile__history-value">{product.genre}</p>
            </li>
          </ul>
        </li>
      ))}
    </ul>
  );
};

export default OrderHistory;
