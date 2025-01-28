package com.huseynov.restaurant.order;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder();

    List<OrderDTO> getOrders();

    OrderDTO getOrderDTOById(Long id);

    Order getOrderById(Long id);

    OrderDTO cancelOrder(Long id);

    void deleteOrder();


    void checkCustomerForOrder(Long id);
}
