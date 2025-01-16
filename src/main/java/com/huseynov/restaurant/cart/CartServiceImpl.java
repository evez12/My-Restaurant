package com.huseynov.restaurant.cart;

import com.huseynov.restaurant.customer.Customer;
import com.huseynov.restaurant.customer.CustomerService;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final Customer customer; // Customer who sent the request
    private final CustomerService customerService;

    @Override
    public Cart initializeNewCart() {
        log.info("CartServiceImpl::initializeNewCart");
        Cart newCart = new Cart();
        Customer myCustomer = customerService.getCustomerById(customer.getId());
        newCart.setCustomer(myCustomer);
        log.info("New cart created: {}", newCart);
        return cartRepository.save(newCart);
    }


    @Override
    public Cart getCart() {
        Long id = cartRepository
                .findCartIdByCustomerId(customer.getId())
                .orElseThrow(() -> new CustomNotFoundException("Cart not found for customer,  id: " + customer.getId()));
        log.info("CartServiceImpl::getCart , id: {}", id);
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
        Long id = cartRepository
                .findCartIdByCustomerId(customer.getId())
                .orElseThrow(() -> new CustomNotFoundException("Cart not found for customer, id: " + customer.getId()));
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
    public boolean isCustomerRequestValidForCart() {
        Long cartId = cartRepository
                .findCartIdByCustomerId(customer.getId())
                .orElseThrow(() -> new CustomNotFoundException("Cart not found for customer, id: " + customer.getId()));

        log.info("CartServiceImpl::isCustomerRequestValidForCart , cartId: {}", cartId);
        return cartRepository.existsCartByCustomerIdAndCartId(customer.getId(), cartId);
    }

    @Override
    public Long getCartId() {
        log.info("CartServiceImpl::getCartId");
        return cartRepository
                .findCartIdByCustomerId(customer.getId())
                .orElse(null);
    }


}
