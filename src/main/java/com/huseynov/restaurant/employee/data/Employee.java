package com.huseynov.restaurant.employee.data;

import com.huseynov.restaurant.shared.model.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

// For N + 1 query problem

@NamedEntityGraph(
        name = "employee-detail-role-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "roles"),
                @NamedAttributeNode(value = "employeeDetail")
        }
)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String surname;

    @NaturalId
    String email;
    String password;

    @Version
    Long version;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    Set<Role> roles;

    @OneToOne(mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    EmployeeDetail employeeDetail;

    public Employee(Long id, String email,
                    String name, String surname) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }


    public void setEmployeeDetail(EmployeeDetail employeeDetail) {
        this.employeeDetail = employeeDetail;
        if (employeeDetail != null) {
            employeeDetail.setEmployee(this);
        }
    }

    public Set<Role> addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
        return roles;
    }


    public boolean isDisabled() {
        return !this.getEmployeeDetail().isEnabled();
    }

    public boolean isEnabled() {
        return this.getEmployeeDetail().isEnabled();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", employeeDetail"+employeeDetail + '\''+
                '}';
    }
}
