/**
 * axios setup to use mock service
 */

import axios from "axios";

const API_ENDPOINT = import.meta.env.VITE_API_URL;
const axiosServices = axios.create({
  baseURL: API_ENDPOINT,
  headers: {
    "Content-Type": "application/json"
  }
});

// interceptor for http
axiosServices.interceptors.response.use(
  (response) => response,
  (error) =>
    Promise.reject((error.response && error.response.data) || "Wrong Services")
);

export default axiosServices;
