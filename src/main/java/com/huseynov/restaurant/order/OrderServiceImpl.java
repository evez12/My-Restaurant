package com.huseynov.restaurant.order;

import com.huseynov.restaurant.cart.Cart;
import com.huseynov.restaurant.cart.CartService;
import com.huseynov.restaurant.cart.CartServiceException;
import com.huseynov.restaurant.cart.data.CartItem;
import com.huseynov.restaurant.customer.CustomerService;
import com.huseynov.restaurant.shared.UserOfSendingRequest;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import com.huseynov.restaurant.shared.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final CartService cartService;
    private final CustomerService customerService;
    private final UserOfSendingRequest myCustomer; // Customer who sent the request

    @Override
    public OrderDTO createOrder() {
        Long customerId = myCustomer.getId();
        try {
            log.info("OrderServiceImpl:createOrder execution started, customer id: {}", customerId);
            Cart cart = cartService.getCart();
            Order order = setResultsToOrder(cart);
            order.setCustomer(
                    customerService.getCustomerById(customerId)
            );
            log.info("OrderServiceImpl:createOrder order: {}", order);

            Order savedOrder = orderRepository.save(order);
            log.info("OrderServiceImpl:createOrder Saved order: {}", savedOrder);
            cartService.deleteCart(); // Delete the cart after the order is created

            log.info("OrderServiceImpl:createOrder execution ended");
            return modelMapper.map(savedOrder, OrderDTO.class);

        } catch (Exception e) {
            log.error("Exception occurred while during created order, customer id: {}, Exception message: {}", customerId, e.getMessage());
            throw new OrderServiceException("Exception occurred during created bew order, customer id: " + customerId);
        }
    }

    @Override
    public List<OrderDTO> getOrders() {
        Long customerId = myCustomer.getId();
        try {
            log.info("OrderServiceImpl:getOrder execution started, customer id: {}", customerId);
            List<Order> orders = orderRepository.findOrdersByCustomerId(customerId).orElseThrow(
                    () -> new CustomNotFoundException("Order not found for customer id: " + customerId)
            );
            if (orders.isEmpty()) {
                throw new CustomNotFoundException("Order not found for customer id: " + customerId);
            }
            log.info("OrderServiceImpl:getOrder get order: {}", orders);
            log.info("OrderServiceImpl:getOrder execution ended");

            return orders.stream()
                    .map(order -> modelMapper.map(order, OrderDTO.class))
                    .toList();

        } catch (OrderServiceException e) {
            log.error("Exception occurred while during get order, customer id: {}, Exception message: {} ", customerId, e.getMessage());
            throw new OrderServiceException("Exception occurred during get order, customer id: " + customerId);
        }
    }

    @Override
    public Order getOrderById(Long id) {
        log.info("OrderServiceImpl:getOrderById execution started, orderId: {}", id);
        Order order;
        try {
            order = orderRepository.findOrderById(id).orElseThrow(
                    () -> new CustomNotFoundException("Order not found, id: " + id)
            );
        } catch (CustomNotFoundException e) {
            log.error("CustomNotFoundException occurred while during get order, REASON not found order; orderId: {}, Exception message: {}"
                    , id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while during get order by id : {}, Exception message: {}", id, e.getMessage());
            throw new CartServiceException("Exception occurred fetch order from Database, orderId: " + id);
        }
        log.info("OrderServiceImpl:getOrderById execution started, order: {}", order);
        return order;
    }

    @Override
    public OrderDTO getOrderDTOById(Long id) {
        log.info("OrderServiceImpl:getOrderDTOById execution started, orderId: {}", id);
        OrderDTO orderDTO;
        try {
            orderDTO = modelMapper.map(getOrderById(id), OrderDTO.class);
        } catch (CustomNotFoundException e) {
            log.error("Exception occurred while during get order, REASON not found order; orderId: {}, Exception message: {}"
                    , id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while during get order by id: {}, Exception message: {}", id, e.getMessage());
            throw new CartServiceException("Exception occurred fetch order from Database, orderId: " + id);
        }
        log.info("OrderServiceImpl:getOrderDTOById execution ended");
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(Long orderId) {
        log.info("OrderServiceImpl:cancelOrder execution started, orderId: {}", orderId);

        Order order = getOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        log.info("OrderServiceImpl:cancelOrder execution ended, orderId: {}", orderId);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public void deleteOrder(Long id) {
        // TODO: Delete cart implement
    }

    /**
     * Checks if the customer associated with the current request is valid for the given order.
     *
     * @param orderId the ID of the order to check
     * @throws InvalidRequestException if the order does not belong to the customer
     */
    @Override
    public void checkCustomerForOrder(Long orderId) {
        try {
            Long customerId = myCustomer.getId();
            log.info("OrderServiceImpl:checkCustomerForOrder execution started, orderId: {} customer id: {}", orderId, customerId);
            if (!orderRepository.existsOrderByOrderIdAndCustomerId(orderId, customerId)) {
                log.error("Error occurred while checking customer for order, orderId: {}, customerId: {}", orderId, customerId);
                throw new InvalidRequestException("Invalid request for order, id: " + orderId);
            }
            log.info("OrderServiceImpl:checkCustomerForOrder execution ended");
        } catch (InvalidRequestException e) {
            log.error("Exception occurred while checking customer for order,REASON invalid order orderId: {}, Exception message: {}"
                    , orderId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while checking customer, orderId: {}, Exception message: {}"
                    , orderId, e.getMessage());
            throw new OrderServiceException("Exception occurred while checking customer for order, orderId: " + orderId);
        }
    }

    private Order setResultsToOrder(Cart cart) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setCreatedDate(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalAmount());

        Set<OrderItem> orderItems = getOrderItems(cart.getItems(), order);
        order.setOrderItems(orderItems);
        return order;
    }

    private Set<OrderItem> getOrderItems(Set<CartItem> cartItems, Order order) {
        return cartItems
                .stream()
                .map(cartItem -> new OrderItem(order, cartItem.getUnitPrice(), cartItem.getProduct(), cartItem.getQuantity()))
                .collect(Collectors.toSet());
    }

}
