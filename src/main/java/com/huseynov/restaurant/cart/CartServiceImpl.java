package com.huseynov.restaurant.cart;

import com.huseynov.restaurant.cart.data.CartRepository;
import com.huseynov.restaurant.customer.CustomerService;
import com.huseynov.restaurant.customer.data.Customer;
import com.huseynov.restaurant.shared.UserOfSendingRequest;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserOfSendingRequest myCustomer; // Customer who sent the request
    private final CustomerService customerService;

    @Override
    public Cart initializeNewCart() {
        log.info("CartServiceImpl::initializeNewCart");
        Cart newCart = new Cart();
        Customer customer = customerService.getCustomerById(myCustomer.getId());
        newCart.setCustomer(customer);
        log.info("New cart created: {}", newCart);
        return cartRepository.save(newCart);
    }


    @Override
    public Cart getCart() {
        log.info("CartServiceImpl::getCart execution started");
        Long customerId = myCustomer.getId();
        log.info("The customerId: {}", customerId);
        Cart cart;
        Long cartId = getCartId();
        try {
            cart = getCartById(cartId);
        } catch (CustomNotFoundException e) {
            log.error("CartServiceImpl::getCart , cart not found, cartId: {}, customerId: {}", cartId, customerId);
            throw e;
        }
        log.info("CartServiceImpl::getCart execution ended");
        return cart;
    }

    @Override
    public Cart getCartById(Long id) {
        log.info("CartServiceImpl:getCartById execution started, cartId: {}", id);
        Cart cart;
        try {
            cart = cartRepository
                    .findCartByCartId(id)
                    .orElseThrow(
                            () -> new CustomNotFoundException("Cart not found , id:" + id)
                    );
        } catch (CustomNotFoundException e) {
            log.error("CartServiceImpl::getCart , cart not found , id: {}", id);
            throw e;
        }
        log.info("CartServiceImpl::getCartById cart: {}, CartItems: {}", cart, cart.getItems());
        log.info("CartServiceImpl::getCartById execution ended");
        return cart;
    }

    @Override
    public Cart saveCart(Cart cart) {
        log.info("CartServiceImpl::saveCart , cart: {}", cart);
        return cartRepository.save(cart);
    }

    @Override
    public void deleteCart() {
        log.info("CartServiceImpl::deleteCart execution started");
        deleteCartById(getCartId());
        log.info("CartServiceImpl::deleteCart ended");
    }

    @Override
    public void deleteCartById(Long id) {
        log.info("CartServiceImpl::deleteCartById execution started, cart id: {}", id);

        if (id == null) {
            log.error("CartServiceImpl::deleteCartById , cart not found for customer, id: {}", myCustomer.getId());
            throw new CustomNotFoundException("Cart not found for customer, id: " + myCustomer.getId());
        }
        clearCartById(id); // Clear the cart before deleting
        cartRepository.deleteById(id);
        log.info("CartServiceImpl::deleteCartById ended");
    }

    @Override
    public void clearCart() {
        log.info("CartServiceImpl::clearCart execution started");
        Cart cart = getCart();
        cart.clearCart();
        log.info("CartServiceImpl::clearCart execution ended");
    }

    @Override
    public void clearCartById(Long id) {
        log.info("CartServiceImpl::clearCartById execution started");
        Cart cart = getCartById(id);
        cart.clearCart();
        log.info("CartServiceImpl::clearCartById execution ended");
    }


    @Override
    public Long getCartId() {
        Long cartId;
        try {
            log.info("CartServiceImpl::getCartId execution started");
            cartId = cartRepository
                    .findCartIdByCustomerId(myCustomer.getId())
                    .orElse(null);
        } catch (Exception e) {
            log.error("Exception occurred while during get cartId from Database, customer id: {}, Exception message: {}"
                    , myCustomer.getId(), e.getMessage());
            throw new CartServiceException("Exception occurred fetch cartId from Database, customer id: " + myCustomer.getId());
        }
        log.info("CartServiceImpl::getCartId execution ended, cartId: {}", cartId);
        return cartId;
    }

    /**
     * Checks if the customer request is valid for the cart.
     *
     * @return true if the customer request is valid for the cart, false otherwise
     * @throws CustomNotFoundException if the cart is not found for the customer
     */
    @Override
    public boolean isCustomerRequestValidForCart() {
        log.info("CartServiceImpl::isCustomerRequestInvalidForCart execution started");

        Long id = getCartId();
        if (id == null) {
            log.error("CartServiceImpl::isCustomerRequestInvalidForCart , cart not found for customer, id: {}", myCustomer.getId());
            throw new CustomNotFoundException("Cart not found for customer,  id: " + myCustomer.getId());
        }

        log.info("CartServiceImpl::isCustomerRequestValidForCart , cartId: {}", id);
        log.info("CartServiceImpl::isCustomerRequestValidForCart execution ended");
        return cartRepository.existsCartByCustomerIdAndCartId(myCustomer.getId(), id);
    }

    // if the customer who sent request is not the owner of the cart(for exists cart)
    @Override
    public void checkCustomerForCart() {
        log.info("CartServiceImpl::checkCustomerForCart execution started");
        if (getCartId() == null) {
            throw new CustomNotFoundException("Cart not found for customer, id: " + myCustomer.getId());
        }
        log.info("CartServiceImpl::checkCustomerForCart execution ended, Successfully check");
    }

}
