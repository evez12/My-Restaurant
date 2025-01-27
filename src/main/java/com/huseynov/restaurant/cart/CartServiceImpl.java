package com.huseynov.restaurant.cart;

import com.huseynov.restaurant.customer.data.Customer;
import com.huseynov.restaurant.customer.CustomerService;
import com.huseynov.restaurant.shared.UserOfSendingRequest;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import com.huseynov.restaurant.shared.exception.InvalidRequestException;
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
        log.info("CartServiceImpl::getCart");

        Long id = getCartId();
        if (id == null) {
            log.error("CartServiceImpl::getCart , cart not found for customer, id: {}", myCustomer.getId());
            throw new CustomNotFoundException("Cart not found for customer, id: " + myCustomer.getId());
        }

        log.info("CartServiceImpl::getCartById , id: {}", id);
        return cartRepository
                .findCartByCartId(id)
                .orElseThrow(() -> new CustomNotFoundException("Cart not found, id: " + id));
    }

    @Override
    public Cart saveCart(Cart cart) {
        log.info("CartServiceImpl::saveCart , cart: {}", cart);
        return cartRepository.save(cart);
    }

    @Override
    public void deleteCart() {
        log.info("CartServiceImpl::deleteCart");

        Long id = getCartId();
        if (id == null) {
            log.error("CartServiceImpl::deleteCart , cart not found for customer, id: {}", myCustomer.getId());
            throw new CustomNotFoundException("Cart not found for customer,  id: " + myCustomer.getId());
        }

        log.info("CartServiceImpl::deleteCartById , id: {}", id);
        cartRepository.deleteById(id);
    }

    @Override
    public void clearCart() {
        log.info("CartServiceImpl::clearCart");
        Cart cart = getCart();
        cart.clearCart();
        cartRepository.delete(cart);
    }

    @Override
    public Long getCartId() {
        log.info("CartServiceImpl::getCartId");
        return cartRepository
                .findCartIdByCustomerId(myCustomer.getId())
                .orElse(null);
    }

    /**
     * Checks if the customer request is valid for the cart.
     *
     * @return true if the customer request is valid for the cart, false otherwise
     * @throws CustomNotFoundException if the cart is not found for the customer
     */
    @Override
    public boolean isCustomerRequestValidForCart() {
        log.info("CartServiceImpl::isCustomerRequestInvalidForCart");

        Long id = getCartId();
        if (id == null) {
            log.error("CartServiceImpl::isCustomerRequestInvalidForCart , cart not found for customer, id: {}", myCustomer.getId());
            throw new CustomNotFoundException("Cart not found for customer,  id: " + myCustomer.getId());
        }

        log.info("CartServiceImpl::isCustomerRequestValidForCart , cartId: {}", id);
        return cartRepository.existsCartByCustomerIdAndCartId(myCustomer.getId(), id);
    }

    // if the customer who sent request is not the owner of the cart(for exists cart)
    @Override
    public void checkCustomerForCart() {
        log.info("CartServiceImpl::checkCustomerForCart");
        if (getCartId() != null && !isCustomerRequestValidForCart()) {
            throw new InvalidRequestException("Invalid request for cart ");
        }
    }

}
