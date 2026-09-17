
package com.bookstore.repository;

import com.bookstore.entity.Cart;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Cart c where c.user.id = :userId")
    Optional<Cart> findByUserIdForUpdate(Long userId);

    Optional<Cart> findByUserUsername(String username);
}
