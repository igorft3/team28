import React, { useState } from "react";
import ProductModal from "../productModal/ProductModal";
import { useCart } from "../cartContext/CartContext";
import "./productCard.css";

const ProductCard = ({ product }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { addItemToCart } = useCart();

  const handleBuyClick = () => {
    addItemToCart(product);
  };

  const toggleModal = () => {
    setIsModalOpen(!isModalOpen);
  };
  return (
    <>
      <li className="shop__item">
        <img
          src={product.imgSrc}
          className="shop__img"
          alt={product.name}
          onClick={toggleModal}
        />
        <h3 className="shop__subtitle">{product.name}</h3>
        <p className="shop__desc">{product.description}</p>
        <p className="shop__price">Цена: {product.price}</p>
        <button className="shop__btn" onClick={handleBuyClick}>
          Купить
        </button>
      </li>
      {isModalOpen && <ProductModal product={product} onClose={toggleModal} />}
    </>
  );
};

export default ProductCard;
