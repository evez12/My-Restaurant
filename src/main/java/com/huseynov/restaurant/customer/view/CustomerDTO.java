package com.huseynov.restaurant.customer.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
    List<String> role;
    String gender;

}
