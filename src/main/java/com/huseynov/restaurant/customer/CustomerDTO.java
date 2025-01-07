package com.huseynov.restaurant.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huseynov.restaurant.shared.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
class CustomerDTO {

    String name;
    String surname;
    String email;
    Gender gender;
    String phoneNumber;
    String address;
}
