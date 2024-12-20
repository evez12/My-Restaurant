package com.huseynov.restaurant.order.repo;

import com.huseynov.restaurant.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
