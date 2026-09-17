
package com.bookstore.payment;

import com.bookstore.entity.Order;

public interface PaymentStrategy {
    boolean pay(Order order);
    String getType();
}
