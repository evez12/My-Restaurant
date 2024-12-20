package com.huseynov.restaurant.shared.security.role;

import com.huseynov.restaurant.shared.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByName(UserRole name);
}
