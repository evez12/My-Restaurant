package com.huseynov.restaurant.employee;

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
public class EmployeeDetailsServiceImpl implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Employee employee;
        log.info("loadUserByUsername called with: {}", email);
        try {
            employee = employeeRepository.findEmployeeByEmail(email)
                    .orElseThrow(() -> new EmployeeNotFoundException("Invalid email or password"));
        } catch (EmployeeNotFoundException e) {
            log.warn("Not found employee with email: {}", email);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while trying to find the employee by email: {}, {}", email, e.getMessage());
            throw new EmployeeServiceException("An error occurred while trying to find the employee by email", e);
        }

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
