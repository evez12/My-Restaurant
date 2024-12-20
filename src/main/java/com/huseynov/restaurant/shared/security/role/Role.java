package com.huseynov.restaurant.shared.security.role;

import com.huseynov.restaurant.shared.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter()
@Setter()
@NoArgsConstructor()
@AllArgsConstructor()
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    UserRole name;

    public Role(UserRole name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name.toString() +
                '}';
    }
}
