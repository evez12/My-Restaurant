package com.huseynov.restaurant.model.user;

import com.huseynov.restaurant.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "employee_detail")
public class EmployeeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String address;
    boolean enabled;

    @Enumerated(EnumType.STRING)
    Gender gender;
    BigDecimal salary = BigDecimal.ZERO;

    @Column(unique = true, name = "phone_number")
    String phoneNumber;

    @OneToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    Employee employee;

}
