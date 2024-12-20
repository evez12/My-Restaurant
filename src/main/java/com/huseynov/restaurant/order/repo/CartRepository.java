package com.huseynov.restaurant.order.repo;

import com.huseynov.restaurant.order.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
