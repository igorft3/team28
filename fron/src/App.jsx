import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/header/Header";
import Footer from "./components/footer/Footer";
// import About from "./pages/about/About";
import Shop from "./pages/shop/Shop";
import Profile from "./pages/profile/Profile";
import Login from "./pages/login/login";
import NotFound from "./pages/notFound/NotFound";
import { AuthProvider } from "./components/authContext/authContext";
import { CartProvider } from "./components/cartContext/CartContext";
import PrivateRoute from "./components/privateRoute/PrivateRoute";
import "./normalize.css";
import "./App.css";

const App = () => {
  return (
    <AuthProvider>
      <CartProvider>
        <Router>
          <Header />
          <Routes>
            <Route path="/" element={<PrivateRoute element={<Shop />} />} />
            {/* <Route path="/shop" element={<PrivateRoute element={<Shop />} />} /> */}
            <Route
              path="/profile"
              element={<PrivateRoute element={<Profile />} />}
            />
            <Route path="/login" element={<Login />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
          <Footer />
        </Router>
      </CartProvider>
    </AuthProvider>
  );
};

export default App;
