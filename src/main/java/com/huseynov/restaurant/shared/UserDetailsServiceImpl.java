package com.huseynov.restaurant.shared;

import com.huseynov.restaurant.customer.data.Customer;
import com.huseynov.restaurant.customer.data.MyCustomerRepository;
import com.huseynov.restaurant.employee.data.Employee;
import com.huseynov.restaurant.employee.data.EmployeeRepository;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import com.huseynov.restaurant.shared.exception.InvalidRequestException;
import com.huseynov.restaurant.shared.model.Role;
import lombok.Getter;
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

    private final MyCustomerRepository customerRepo;
    private final EmployeeRepository employeeRepo;

    @Getter
    private UserOfSendingRequest myUser;

    @Override
    public UserDetails loadUserByUsername(String email) throws InvalidRequestException, CustomNotFoundException {
        log.info("UserDetailsServiceImpl::loadUserByUsername called with: {}", email);
        try {
            if (email.endsWith("@restaurant.com")) {
                log.info("Employee send request");
                return getEmployee(email);
            } else {
                log.info("Customer send request");
                return getCustomer(email);
            }
        } catch (CustomNotFoundException | InvalidRequestException e) {
            log.warn("Not found customer with email: {}, message: {}, Exception name: {}", email, e.getMessage(), e.getClass().getName());
            throw new InvalidRequestException(e.getMessage());
        }

    }

    private User getCustomer(String email) {
        log.info("UserDetailsServiceImpl::getCustomer called with: {}", email);
        Customer customer = customerRepo
                .findCustomerWithRolesByEmail(email)
                .orElseThrow(() -> new InvalidRequestException("Invalid email or password"));

        if (customer.isDisabled()) {
            log.error("UserDetailsServiceImpl::getCustomer, customer is not enabled, email: {}", email);
            throw new InvalidRequestException("Account is disabled or deleted");
        }

        log.info("UserDetailsServiceImpl::getCustomer, customer: {}", customer);

        setMyUser(customer.getId(), customer.getEmail(), customer.getName());

        return new User(customer.getEmail(),
                customer.getPassword(),
                mapRolesToAuthorities(customer.getRoles())
        );
    }

    private User getEmployee(String email) {
        Employee employee = employeeRepo
                .findEmployeeByEmail(email)
                .orElseThrow(() -> new CustomNotFoundException("Invalid email or password"));

        log.info("UserDetailsServiceImpl::getEmployee, email: {}", email);
        if (employee.isDisabled()) {
            log.error("UserDetailsServiceImpl::getEmployee, employee is not enabled, email: {}", email);
            throw new CustomNotFoundException("Account is disabled or deleted");
        }

        setMyUser(employee.getId(), employee.getEmail(), employee.getName());
        return new User(employee.getEmail(),
                employee.getPassword(),
                mapRolesToAuthorities(employee.getRoles())
        );
    }

    // Set the user who sent the request
    private void setMyUser(Long id, String email, String name) {
        myUser = new UserOfSendingRequest(id, email, name);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        roles.forEach(role -> log.info("role: {}", role));
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

}
