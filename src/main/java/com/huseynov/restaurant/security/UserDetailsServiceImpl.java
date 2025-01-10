package com.huseynov.restaurant.security;

import com.huseynov.restaurant.customer.Customer;
import com.huseynov.restaurant.customer.CustomerRepository;
import com.huseynov.restaurant.customer.CustomerServiceException;
import com.huseynov.restaurant.employee.Employee;
import com.huseynov.restaurant.employee.EmployeeRepository;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import com.huseynov.restaurant.shared.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepo;
    private final EmployeeRepository employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.info("CustomerDetailServiceImpl::loadUserByUsername called with: {}", email);
        try {
            if (email.endsWith("@restaurant.com")) {
                Employee employee = employeeRepo
                        .findEmployeeByEmail(email)
                        .orElseThrow(() -> new CustomNotFoundException("Invalid email or password"));
                return new User(employee.getEmail(),
                        employee.getPassword(),
                        mapRolesToAuthorities(employee.getRoles())
                );
            } else {
                Customer customer = customerRepo
                        .findCustomerByEmail(email)
                        .orElseThrow(() -> new CustomNotFoundException("Invalid email or password"));
                return new User(customer.getEmail(),
                        customer.getPassword(),
                        mapRolesToAuthorities(customer.getRoles())
                );
            }
        } catch (CustomNotFoundException e) {
            log.warn("Not found customer with email: {}", email);
            throw e;
        } catch (RuntimeException e) {
            log.error("An error occurred while trying to find the customer by email: {}, {}", email, e.getMessage());
            throw new CustomerServiceException("An error occurred while trying to find the customer by email", e);
        }

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        roles.forEach(role -> log.info("role: {}", role));
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

}
