import axios from 'axios';
const API = axios.create({ baseURL: 'http://localhost:8080/api', timeout: 5000 });

export const getBooks = () => API.get('/books');
export const getBook = (id) => API.get(`/books/${id}`);
export const getCart = (userId) => API.get(`/cart/${userId}`);
export const addToCart = (userId, bookId, qty=1) => API.post(`/cart/${userId}/add/${bookId}?quantity=${qty}`);
export const updateCartItem = (itemId, qty) => API.put(`/cart/update/${itemId}?quantity=${qty}`);
export const removeFromCart = (itemId) => API.delete(`/cart/remove/${itemId}`);
export const checkout = (userId) => API.post(`/cart/${userId}/checkout`);
export const login = (data) => API.post('/auth/login', data);
export const register = (data) => API.post('/auth/register', data);

API.interceptors.response.use(
  res => res,
  err => {
    const msg = err.response?.data?.message || err.message;
    console.error('API Error:', msg);
    return Promise.reject(err);
  }
);