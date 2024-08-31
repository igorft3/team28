import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const PrivateRoute = ({ element }) => {
  const navigate = useNavigate();
  const token = localStorage.getItem("authToken");

  useEffect(() => {
    if (token !== "undefined" && token !== "") {
      // navigate("/");
    } else {
      // navigate("/login");
    }
  }, [token, navigate]);

  return token ? element : null;
};

export default PrivateRoute;
