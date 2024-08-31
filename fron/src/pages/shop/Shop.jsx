import React, { useState } from "react";
import ProductCard from "../../components/productCard/ProductCard";
import { products } from "../../data";
import "./shop.css";

const Shop = () => {
  const [selectGenry, setSelectGenry] = useState("all");
  const [sortOrder, setSortOrder] = useState("asc");
  const [currentPage, setCurrentPage] = useState(1);
  const [searchTerm, setSearchTerm] = useState("");
  const itemsPage = 20;

  const filterProduct = products.filter((product) => {
    const matchesGenry =
      selectGenry === "all" ? true : product.genre === selectGenry;
    const matchesSearchTerm = product.name
      .toLowerCase()
      .includes(searchTerm.toLowerCase());
    return matchesGenry && matchesSearchTerm;
  });

  const sortedProducts = filterProduct.sort((a, b) =>
    sortOrder === "asc" ? a.price - b.price : b.price - a.price
  );

  const indexLastItem = currentPage * itemsPage;
  const indexFirstItem = indexLastItem - itemsPage;
  const currentProducts = sortedProducts.slice(
    indexFirstItem,
    indexFirstItem + itemsPage
  );

  const totalPages = Math.ceil(sortedProducts.length / itemsPage);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <section className="shop">
      <div className="shop__container container">
        <div className="shop__wrapp-text"></div>

        <ul className="shop__list-filter">
          <li className="shop__item__filter">
            <div className="shop__filter-wrapp">
              <label className="shop__filter-name">
                Категория
                <select
                  className="shop__filter-select"
                  value={selectGenry}
                  onChange={(e) => setSelectGenry(e.target.value)}
                >
                  <option value="all">Все</option>
                  <option value="merch">Мерч</option>
                  <option value="tech">Орг-техника</option>
                </select>
              </label>
            </div>
          </li>
          <li className="shop__item__filter">
            <div className="shop__filter-wrapp">
              <label className="shop__filter-name">
                Цена
                <select
                  className="shop__filter-select"
                  value={sortOrder}
                  onChange={(e) => setSortOrder(e.target.value)}
                >
                  <option value="asc">По возрастанию</option>
                  <option value="desc">По убыванию</option>
                </select>
              </label>
            </div>
          </li>
          <li className="shop__item__filter">
            <div className="shop__filter-wrapp">
              <label className="shop__filter-name">
                <input
                  type="search"
                  className="shop__filter-field"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)} // Обновление состояния при вводе текста
                  placeholder="Поиск товара.."
                />
              </label>
            </div>
          </li>
        </ul>
        <ul className="shop__list">
          {currentProducts.map((product) => (
            <ProductCard key={product.productid} product={product} />
          ))}
        </ul>
        <div className="pagination">
          {[...Array(totalPages).keys()].map((number) => (
            <button
              key={number + 1}
              onClick={() => paginate(number + 1)}
              className={`pagination-btn ${
                currentPage === number + 1 ? "active" : ""
              }`}
            >
              {number + 1}
            </button>
          ))}
        </div>
      </div>
    </section>
  );
};

export default Shop;
