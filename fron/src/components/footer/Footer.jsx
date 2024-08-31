import React from "react";
import LogoSVG from "../logoSvg/logoSvg";
import "./footer.css";

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer__container container">
        <a href="index.html" className="footer__logo">
          <LogoSVG />
          e-commerce
        </a>
        <div className="footer__contact-us">
          {/* <a href="" className="footer__link">
            FolowwMe
          </a>
          <a href="" className="footer__link">
            SendMeMessage
          </a> */}
        </div>
      </div>
      <div className="footer__wrapp-strip">
        <div className="stripe"></div>
        <div className="stripe"></div>
        <div className="stripe"></div>
        <div className="stripe"></div>
        <div className="stripe"></div>
      </div>
    </footer>
  );
};

export default Footer;
