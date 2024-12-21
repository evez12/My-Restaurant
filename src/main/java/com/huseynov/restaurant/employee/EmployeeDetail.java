package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.enums.Gender;
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
    boolean enabled=Boolean.TRUE;

    @Enumerated(EnumType.STRING)
    Gender gender=Gender.UNDEFINED;
    BigDecimal salary = BigDecimal.ZERO;

    @Column(name = "phone_number")
    String phoneNumber;

    @Version
    Long version;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    Employee employee;


    @Override
    public String toString() {
        return "EmployeeDetail{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", enabled=" + enabled +
                ", gender=" + gender +
                ", salary=" + salary +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
