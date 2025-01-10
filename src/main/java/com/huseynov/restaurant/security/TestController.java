package com.huseynov.restaurant.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello everyone";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin page";
    }
    @GetMapping("/customer")
    public String customer() {
        return "Customer page";
    }

    @GetMapping("/manager")
    public String manager() {
        return "Manager page";
    }

    @GetMapping("/employee")
    public String employee() {
        return "Employee page";
    }
}
