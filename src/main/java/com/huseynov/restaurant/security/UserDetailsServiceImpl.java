package com.huseynov.restaurant.security;

import com.huseynov.restaurant.customer.Customer;
import com.huseynov.restaurant.customer.CustomerRepository;
import com.huseynov.restaurant.employee.Employee;
import com.huseynov.restaurant.employee.EmployeeRepository;
import com.huseynov.restaurant.shared.exception.CustomAuthException;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import com.huseynov.restaurant.shared.model.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Getter
    private Customer myCustomer;

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.info("CustomerDetailServiceImpl::loadUserByUsername called with: {}", email);
        try {
            if (email.endsWith("@restaurant.com")) {
                log.info("Employee send request");
                return getEmployee(email);
            } else {
                log.info("Customer send request");
                return getCustomer(email);
            }
        } catch (CustomNotFoundException e) {
            log.warn("Not found customer with email: {}", email);
            throw e;
        }
        catch (RuntimeException e) {
            log.error("An error occurred while trying to find the customer by email: {}, {}", email, e.getMessage());
            throw new CustomAuthException("An error occurred while trying to find the customer by email", e);
        }

    }

    private User getCustomer(String email) {
        log.info("CustomerDetailServiceImpl::getCustomer called with: {}", email);
        Customer customer = customerRepo
                .findCustomerByEmail(email)
                .orElseThrow(() -> new CustomNotFoundException("Invalid email or password"));

        log.info("CustomerDetailServiceImpl::getCustomer, customer: {}", customer);
        myCustomer = customer; // set the customer id to the field (Customer who sent the request)
        return new User(customer.getEmail(),
                customer.getPassword(),
                mapRolesToAuthorities(customer.getRoles())
        );
    }

    private User getEmployee(String email) {
        Employee employee = employeeRepo
                .findEmployeeByEmail(email)
                .orElseThrow(() -> new CustomNotFoundException("Invalid email or password"));
        return new User(employee.getEmail(),
                employee.getPassword(),
                mapRolesToAuthorities(employee.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        roles.forEach(role -> log.info("role: {}", role));
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

}
