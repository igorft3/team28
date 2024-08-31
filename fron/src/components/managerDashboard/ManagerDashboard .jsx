import React, { useState } from "react";

const ManagerDashboard = () => {
  const [activeTab, setActiveTab] = useState("profile");
  const [activeTabManager, setActiveTabManager] = useState("create");

  const handelTabManageClick = (tabName) => {
    setActiveTabManager(tabName);
  };
  return (
    <>
      <ul className="profile__manager-list">
        <li
          className="profile__manager-item"
          onClick={() => handelTabManageClick("create")}
        >
          Создание товара
        </li>
        <li
          className="profile__manager-item"
          onClick={() => handelTabManageClick("change")}
        >
          Изменение товара
        </li>
        <li
          className="profile__manager-item"
          onClick={() => handelTabManageClick("delete")}
        >
          Удаление товара
        </li>
      </ul>

      <div className="manage__container">
        {activeTabManager === "create" && (
          <>
            <ul className="profile__list-create tabs">
              <li className="profile__item-create">
                <label className="profile__create-label">
                  Название товара
                  <input type="text" />
                </label>
              </li>
              <li className="profile__item-create">
                <label className="profile__create-label">
                  Описание товара
                  <input type="text" />
                </label>
              </li>
              <li className="profile__item-create">
                <label className="profile__create-label">
                  Цена товара
                  <input type="text" />
                </label>
              </li>
              <li className="profile__item-create">
                <label className="profile__create-label">
                  Загрузка изображения
                  <input type="file" className="file" />
                </label>
              </li>
              <li className="profile__item-create">
                <label className="profile__create-label">
                  Категория товара
                  <input type="text" />
                </label>
              </li>
              <li className="profile__item-create">
                <label className="profile__create-label">
                  Статус товара
                  <input type="text" />
                </label>
              </li>
            </ul>
          </>
        )}
        {/* {activeTabManager === "change" && <></>} */}
        <div className="manager__create"></div>
      </div>
    </>
  );
};

export default ManagerDashboard;
