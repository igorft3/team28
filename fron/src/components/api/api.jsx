import axios from "axios";

export const API_URL_8082 = "http://10.4.56.79:8082";
export const API_URL_8083 = "http://10.4.56.79:8083";
export const API_URL_8084 = "http://10.4.56.79:8084";

export const API_URL = "http://10.4.56.79";
// докер +

const api = axios.create({
  withCredentials: false,
  baseURL: API_URL,
  headers: {
    token: localStorage.getItem("authToken"),
  },
});

const api8082 = axios.create({
  withCredentials: false,
  baseURL: API_URL_8082,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("authToken")}`,
  },
});

const api8083 = axios.create({
  withCredentials: false,
  baseURL: API_URL_8083,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("authToken")}`,
  },
});

const api8084 = axios.create({
  withCredentials: false,
  baseURL: API_URL_8084,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("authToken")}`,
  },
});

export { api, api8082, api8083, api8084 };
