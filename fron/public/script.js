const listItems = document.querySelectorAll(".shop__item");
const popupItem = document.getElementById("popupItem");

listItems.forEach((item) => {
  item.addEventListener("click", function () {
    const title = item.querySelector(".shop__subtitle").textContent;
    const desc = item.querySelector(".shop__desc").textContent;
    const imgSrc = item.querySelector(".shop__img").src;

    // Установка данных в popup
    popupItem.querySelector(".popup__title").textContent = title;
    popupItem.querySelector(".popup__desc").textContent = desc;
    popupItem.querySelector(".popup__img").src = imgSrc;

    // Открытие popup
    popupItem.classList.add("visible");
    document.body.style.overflow = "hidden";
  });
});

function closePopupItem() {
  popupItem.classList.remove("visible");
  document.body.style.overflow = "";
}

// Закрытие popup при клике за его пределами
popupItem.addEventListener("click", function (event) {
  if (event.target === popupItem) {
    closePopupItem();
  }
});

// popupCart
const billBtn = document.getElementById("billBtn");
const popupCart = document.getElementById("popupCart");

billBtn.addEventListener("click", function () {
  popupCart.classList.toggle("visible");
});

function closePopupCart() {
  popupCart.classList.remove("visible");
}

// Закрытие popup при клике за его пределами
popupCart.addEventListener("click", function (event) {
  if (event.target === popupCart) {
    closePopupCart();
  }
});
