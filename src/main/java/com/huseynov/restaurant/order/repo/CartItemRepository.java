package com.huseynov.restaurant.order.repo;

import com.huseynov.restaurant.order.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
