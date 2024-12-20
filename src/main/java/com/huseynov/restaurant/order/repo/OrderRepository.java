package com.huseynov.restaurant.order.repo;

import com.huseynov.restaurant.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
