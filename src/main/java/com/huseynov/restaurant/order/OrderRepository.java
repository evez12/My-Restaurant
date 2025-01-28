package com.huseynov.restaurant.order;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(value = "order-with-items-and-customer", type = EntityGraph.EntityGraphType.FETCH)
    Optional<List<Order>> findOrdersByCustomerId(Long customerId);

    @EntityGraph(value = "order-with-items-and-customer", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Order> findOrderById(Long id);

    /**
     * Checks if an order exists for a given order ID and customer ID.
     *
     * @param orderId    the ID of the order
     * @param customerId the ID of the customer
     * @return true if the order exists for the given order ID and customer ID, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Order o WHERE o.id = :orderId AND o.customer.id = :customerId")
    boolean existsOrderByOrderIdAndCustomerId(@Param("orderId") Long orderId,
                                              @Param("customerId") Long customerId);


}
