import React from 'react';
import { CartProvider } from './context/CartContext';
import BookList from './components/BookList';
import Cart from './components/Cart';
import Auth from './components/Auth';

function App() {
    return (
        <CartProvider>
            <div className="App">
                <div className="header">
                    <h1>📚 Online Bookstore Kata</h1>
                    <span>React + Spring Boot 17 + TDD</span>
                </div>
                <Auth />
                <BookList />
                <Cart />
            </div>
        </CartProvider>
    );
}
export default App;