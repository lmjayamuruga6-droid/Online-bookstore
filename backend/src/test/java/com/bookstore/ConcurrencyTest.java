
package com.bookstore;

import com.bookstore.entity.*;
import com.bookstore.repository.*;
import com.bookstore.service.CartService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.*;

@SpringBootTest
public class ConcurrencyTest {

    @Autowired CartService cartService;
    @Autowired BookRepository bookRepo;
    @Autowired CartRepository cartRepo;
    @Autowired UserRepository userRepo;
    @Autowired OrderRepository orderRepo;

    @Test
    void checkoutShouldBeIdempotentUnderConcurrency() throws Exception {
        // Setup: book stock 5, two users trying checkout same time
        User user = userRepo.save(User.builder().username("concurrent_user").password("hash").build());
        Book book = bookRepo.save(Book.builder().title("Concurrency Book").price(10.0).stock(5).author("A").build());

        // add to cart
        cartService.addToCart(user, book.getId(), 5);

        String idemKey = "test-idem-key-123";
        int threads = 2;
        ExecutorService exec = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);
        Future<?>[] futures = new Future[threads];
        for (int i=0;i<threads;i++) {
            futures[i] = exec.submit(() -> {
                try {
                    cartService.checkout(user, "CARD", idemKey);
                } finally { latch.countDown(); }
            });
        }
        latch.await(10, TimeUnit.SECONDS);

        // Only 1 order should be created due to idempotencyKey + pessimistic lock
        Assertions.assertEquals(1, orderRepo.findAll().stream().filter(o -> idemKey.equals(o.getIdempotencyKey())).count());
        Assertions.assertEquals(0, bookRepo.findById(book.getId()).get().getStock());
    }

    @Test
    void emptyCartCheckoutShouldFail() {
        User user = userRepo.save(User.builder().username("empty_cart_user").password("hash").build());
        Assertions.assertThrows(IllegalStateException.class, () -> cartService.checkout(user, "CARD", null));
    }
}
