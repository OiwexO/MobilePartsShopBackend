package com.nulp.mobilepartsshop.core.service.user;

import com.nulp.mobilepartsshop.api.v1.user.dto.request.AddressRequest;
import com.nulp.mobilepartsshop.api.v1.user.service.AddressService;
import com.nulp.mobilepartsshop.core.entity.user.Address;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.repository.user.AddressRepository;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Optional<Address> getAddressByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        Address address = user.getAddress();
        if (address == null) {
            return Optional.empty();
        }
        return Optional.of(address);
    }

    @Override
    public Address createAddress(Long userId, AddressRequest request) throws EntityNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException();
        }
        User user = optionalUser.get();
        Address address = Address.builder()
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .state(request.getState())
                .city(request.getCity())
                .street(request.getStreet())
                .buildingNumber(request.getBuildingNumber())
                .build();
        Address savedAddress = addressRepository.save(address);
        user.setAddress(savedAddress);
        userRepository.save(user);
        return savedAddress;
    }

    @Override
    public Optional<Address> updateAddress(Long userId, AddressRequest request) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        Address address = user.getAddress();
        if (address == null) {
            return Optional.empty();
        }
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setState(request.getState());
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setBuildingNumber(request.getBuildingNumber());
        Address savedAddress = addressRepository.save(address);
        user.setAddress(savedAddress);
        userRepository.save(user);
        return Optional.of(savedAddress);
    }

    @Override
    public boolean deleteAddressByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        Address address = user.getAddress();
        user.setAddress(null);
        userRepository.save(user);
        addressRepository.delete(address);
        return true;
    }
}
