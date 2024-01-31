package com.nulp.mobilepartsshop.api.v1.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {
    @GetMapping("/customer")
    public ResponseEntity<String> helloCustomer() {
        return ResponseEntity.ok("Hello Customer");
    }

    @GetMapping("/staff")
    public ResponseEntity<String> helloStaff() {
        return ResponseEntity.ok("Hello Staff");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("Hello Admin");
    }
}
