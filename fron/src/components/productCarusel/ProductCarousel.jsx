import React, { useEffect, useRef } from "react";
import "./productCarousel.css";

const ProductCarousel = ({ items, speed = 10 }) => {
  const carouselRef = useRef(null);

  useEffect(() => {
    const carousel = carouselRef.current;
    let animation;

    const startAnimation = () => {
      carousel.style.animation = `scroll ${speed}s linear infinite`;
    };

    const stopAnimation = () => {
      carousel.style.animationPlayState = "paused";
    };

    const resumeAnimation = () => {
      carousel.style.animationPlayState = "running";
    };

    carousel.addEventListener("mouseenter", stopAnimation);
    carousel.addEventListener("mouseleave", resumeAnimation);

    startAnimation();

    return () => {
      carousel.removeEventListener("mouseenter", stopAnimation);
      carousel.removeEventListener("mouseleave", resumeAnimation);
    };
  }, [speed]);

  return (
    <ul ref={carouselRef} className="carousel-list">
      {items.map((item, index) => (
        <li key={index} className="carousel-item">
          <img src={item.imgSrc} alt={`carousel-item-${index}`} />
        </li>
      ))}
    </ul>
  );
};

export default ProductCarousel;
