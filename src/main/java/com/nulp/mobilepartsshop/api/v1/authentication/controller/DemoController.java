package com.nulp.mobilepartsshop.api.v1.authentication.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DemoController.MAPPING)
public class DemoController {

    public static final String MAPPING = ApiConstants.GLOBAL_MAPPING + "/demo";

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
