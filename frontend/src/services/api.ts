import axios from "axios";

export const userApi = axios.create({
  baseURL: "http://localhost:8081/api/v1",
});

export const rentalApi = axios.create({
  baseURL: "http://localhost:8082/api/v1",
});
