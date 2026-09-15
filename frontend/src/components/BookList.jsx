import React, { useEffect, useState, useContext } from 'react';
import { getBooks, addToCart } from '../services/api';
import { CartContext } from '../context/CartContext';

const BookList = () => {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { refreshCart, userId } = useContext(CartContext);

    useEffect(() => {
        getBooks()
            .then(res => setBooks(res.data))
            .catch(() => setError("Failed to load books. Is backend running on 8080?"))
            .finally(() => setLoading(false));
    }, []);

    const handleAdd = async (bookId) => {
        try {
            await addToCart(userId, bookId, 1);
            await refreshCart();
        } catch(e) {
            alert(e.response?.data?.message || "Failed to add to cart");
        }
    };

    if(loading) return <p>Loading books...</p>;
    if(error) return <p style={{color:'red'}}>{error}</p>;

    return (
        <div>
            <h2>Available Books ({books.length})</h2>
            <div className="book-grid">
                {books.map(book => (
                    <div key={book.id} className="book-card">
                        <h3>{book.title}</h3>
                        <p><i>by {book.author}</i></p>
                        <p><b>${book.price}</b></p>
                        <p>ISBN: {book.isbn}</p>
                        <p>Stock: {book.stock}</p>
                        <button onClick={() => handleAdd(book.id)} disabled={book.stock === 0}>
                            {book.stock === 0 ? 'Out of Stock' : 'Add to Cart'}
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default BookList;