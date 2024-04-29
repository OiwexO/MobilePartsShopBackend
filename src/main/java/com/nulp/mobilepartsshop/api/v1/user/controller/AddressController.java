package com.nulp.mobilepartsshop.api.v1.user.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.user.dto.mapper.AddressMapper;
import com.nulp.mobilepartsshop.api.v1.user.dto.request.AddressRequest;
import com.nulp.mobilepartsshop.api.v1.user.dto.response.AddressResponse;
import com.nulp.mobilepartsshop.api.v1.user.dto.validator.AddressRequestValidator;
import com.nulp.mobilepartsshop.api.v1.user.service.AddressService;
import com.nulp.mobilepartsshop.core.entity.user.Address;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AddressController.MAPPING)
@RequiredArgsConstructor
public class AddressController {

    public static final String MAPPING = ApiConstants.USER_MAPPING_V1 + "/addresses";

    private final AddressService addressService;

    private final AddressRequestValidator requestValidator = new AddressRequestValidator();

    private final AddressMapper mapper = new AddressMapper();

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        List<AddressResponse> responseList = mapper.toResponseList(addresses);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddress(@PathVariable Long addressId) {
        if (!requestValidator.isValidId(addressId)) {
            return ResponseEntity.badRequest().build();
        }
        return addressService.getAddressById(addressId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<AddressResponse> getAddressByUserId(@PathVariable Long userId) {
        if (!requestValidator.isValidId(userId)) {
            return ResponseEntity.badRequest().build();
        }
        return addressService.getAddressByUserId(userId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressResponse> createAddress(
            @PathVariable Long userId,
            @RequestBody AddressRequest request
    ) {
        if (!requestValidator.isValidId(userId) || !requestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            final Address address = addressService.createAddress(userId, request);
            final AddressResponse response = mapper.toResponse(address);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            // invalid userId
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long userId,
            @RequestBody AddressRequest request
    ) {
        if (!requestValidator.isValidId(userId) || !requestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        return addressService.updateAddress(userId, request)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @DeleteMapping("/{addressId}")
//    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
//        if (!requestValidator.isValidId(addressId)) {
//            return ResponseEntity.badRequest().build();
//        }
//        if (addressService.deleteAddress(addressId)) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteAddressByUserId(@PathVariable Long userId) {
        if (!requestValidator.isValidId(userId)) {
            return ResponseEntity.badRequest().build();
        }
        if (addressService.deleteAddressByUserId(userId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
