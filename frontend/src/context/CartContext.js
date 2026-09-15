import React, { createContext, useState, useEffect } from 'react';
import { getCart } from '../services/api';

export const CartContext = createContext();

export const CartProvider = ({ children }) => {
    const [cart, setCart] = useState([]);
    const userId = "guest-user-1"; // Kata simplified user - In real app from JWT

    const refreshCart = async () => {
        try {
            const res = await getCart(userId);
            setCart(res.data);
        } catch(e) { console.error(e); }
    };

    useEffect(() => { refreshCart(); }, []);

    return (
        <CartContext.Provider value={{ cart, refreshCart, userId }}>
            {children}
        </CartContext.Provider>
    );
};