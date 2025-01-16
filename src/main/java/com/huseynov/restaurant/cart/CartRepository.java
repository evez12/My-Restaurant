package com.huseynov.restaurant.cart;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(value = "cart-with-items-and-product", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Cart> findCartByCartId(Long cartId);

    boolean existsCartByCustomerIdAndCartId(Long customerId, Long cartId);

    @Query("SELECT c.cartId FROM Cart c WHERE c.customer.id = :customerId")
    Optional<Long> findCartIdByCustomerId(Long customerId);

}
