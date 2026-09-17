
package com.bookstore.payment;

import com.bookstore.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("CARD")
@Slf4j
public class CardPaymentStrategy implements PaymentStrategy {
    @Override public boolean pay(Order order) {
        log.info("Processing CARD payment for order idempotencyKey={} amount={}", order.getIdempotencyKey(), order.getTotalAmount());
        return true; // integrate with gateway
    }
    @Override public String getType() { return "CARD"; }
}
