package com.huseynov.restaurant.shared;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserOfSendingRequest {
    private Long id;
    private String email;
    private String name;

    public UserOfSendingRequest(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserOfSendingRequest{" +
                "id='" + id + '\'' +
                ", email=" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
