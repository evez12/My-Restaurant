package com.huseynov.restaurant.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CustomerDTO {

    Long id;
    String name;
    String surname;
    String email;
    String phoneNumber;
    boolean enabled;
    String address;
    String gender;
    String role;


}
