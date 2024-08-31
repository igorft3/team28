import React from "react";

const UserProfile = (userInfo) => {
  return (
    <ul className="profile__list-user tabs">
      <li className="profile__item-user">
        <h3 className="profile__user-label">Логин</h3>
        <p className="profile__user-field">{userInfo.username}</p>
      </li>
      <li className="profile__item-user">
        <h3 className="profile__user-label">Емаил</h3>
        <p className="profile__user-field">{userInfo.email}</p>
      </li>
      <li className="profile__item-user">
        <h3 className="profile__user-label">Имя</h3>
        <p className="profile__user-field">{userInfo.firstName}</p>
      </li>
      <li className="profile__item-user">
        <h3 className="profile__user-label">Фамилия</h3>
        <p className="profile__user-field">{userInfo.lastName}</p>
      </li>
      <li className="profile__item-user">
        <h3 className="profile__user-label">Баланс колешелька</h3>
        <p className="profile__user-field">{userInfo.balance} y.e.</p>
      </li>
      <li className="profile__item-user">
        <h3 className="profile__user-label">Роль</h3>
        <p className="profile__user-field">{userInfo.userRole}</p>
      </li>
    </ul>
  );
};

export default UserProfile;
