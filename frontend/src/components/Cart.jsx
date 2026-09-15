import React, { useContext, useState } from 'react';
import { CartContext } from '../context/CartContext';
import { updateCartItem, removeFromCart, checkout } from '../services/api';

const Cart = () => {
    const { cart, refreshCart, userId } = useContext(CartContext);
    const [orderSummary, setOrderSummary] = useState(null);
    const [checkingOut, setCheckingOut] = useState(false);

    const handleQuantity = async (itemId, newQty) => {
        try {
            if(newQty <= 0) {
                await removeFromCart(itemId);
            } else {
                await updateCartItem(itemId, newQty);
            }
            await refreshCart();
        } catch(e) { alert(e.response?.data?.message || "Update failed"); }
    };

    const handleRemove = async (itemId) => {
        await removeFromCart(itemId);
        await refreshCart();
    };

    const handleCheckout = async () => {
        setCheckingOut(true);
        try {
            const res = await checkout(userId);
            setOrderSummary(res.data);
            await refreshCart();
        } catch(e) { alert(e.response?.data?.message || "Checkout failed"); }
        finally { setCheckingOut(false); }
    };

    const total = cart.reduce((sum, item) => sum + item.book.price * item.quantity, 0);

    return (
        <div className="cart">
            <h2>🛒 Shopping Cart ({cart.length} items)</h2>
            {cart.length === 0 && <p>Cart is empty. Add books from above.</p>}
            {cart.map(item => (
                <div key={item.id} className="cart-item" style={{display:'flex', justifyContent:'space-between', marginBottom:'10px'}}>
                    <div>
                        <b>{item.book.title}</b> - ${item.book.price} x {item.quantity}
                    </div>
                    <div>
                        <button onClick={() => handleQuantity(item.id, item.quantity -1)}>-</button>
                        <span style={{margin:'0 8px'}}>{item.quantity}</span>
                        <button onClick={() => handleQuantity(item.id, item.quantity +1)}>+</button>
                        <button onClick={() => handleRemove(item.id)} style={{background:'#d32f2f', marginLeft:'10px'}}>Remove</button>
                    </div>
                </div>
            ))}
            {cart.length > 0 && (
                <>
                    <h3>Total: ${total.toFixed(2)}</h3>
                    <button onClick={handleCheckout} disabled={checkingOut} style={{background:'#2e7d32', padding:'10px 20px'}}>
                        {checkingOut ? 'Processing...' : 'Checkout'}
                    </button>
                </>
            )}
            {orderSummary && (
                <div className="order-summary">
                    <h2>✅ Order Successful</h2>
                    <p><b>Order ID:</b> {orderSummary.orderId}</p>
                    <p><b>Date:</b> {orderSummary.orderDate}</p>
                    <p><b>Items:</b> {orderSummary.items.length}</p>
                    <p><b>Total Paid:</b> ${orderSummary.total.toFixed(2)}</p>
                </div>
            )}
        </div>
    );
};

export default Cart;