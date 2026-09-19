
package com.bookstore.payment;

import com.bookstore.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("UPI")
@Slf4j
public class UpiPaymentStrategy implements PaymentStrategy {
    @Override public boolean pay(Order order) {
        log.info("Processing UPI payment for order idempotencyKey={} amount={}", order.getIdempotencyKey(), order.getTotalAmount());
        return true;
    }
    @Override public String getType() { return "UPI"; }
}
