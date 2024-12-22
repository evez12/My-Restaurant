package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.security.role.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

// For N + 1 query problem
@NamedEntityGraphs(value = {
        @NamedEntityGraph(
                name = "employee-detail",
                attributeNodes = @NamedAttributeNode("employeeDetail")
        )
})
class Employee {
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

    public void setEmployeeDetail(EmployeeDetail employeeDetail) {
        this.employeeDetail = employeeDetail;
        if (employeeDetail != null) {
            employeeDetail.setEmployee(this);
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
