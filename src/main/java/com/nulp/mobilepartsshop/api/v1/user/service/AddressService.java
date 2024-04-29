package com.nulp.mobilepartsshop.api.v1.user.service;

import com.nulp.mobilepartsshop.api.v1.user.dto.request.AddressRequest;
import com.nulp.mobilepartsshop.core.entity.user.Address;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    List<Address> getAllAddresses();

    Optional<Address> getAddressById(Long id);

    Optional<Address> getAddressByUserId(Long userId);

    Address createAddress(Long userId, AddressRequest request) throws EntityNotFoundException;

    Optional<Address> updateAddress(Long userId, AddressRequest request);

//    boolean deleteAddress(Long id);

    boolean deleteAddressByUserId(Long userId);
}
