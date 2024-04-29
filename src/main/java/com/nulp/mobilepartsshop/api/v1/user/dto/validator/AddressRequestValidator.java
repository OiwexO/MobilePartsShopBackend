package com.nulp.mobilepartsshop.api.v1.user.dto.validator;

import com.nulp.mobilepartsshop.api.utils.RequestValidator;
import com.nulp.mobilepartsshop.api.v1.user.dto.request.AddressRequest;

public class AddressRequestValidator extends RequestValidator<AddressRequest> {

    public AddressRequestValidator() {}

    @Override
    public boolean isValidRequest(AddressRequest request) {
        final Integer postalCode = request.getPostalCode();
        if (postalCode == null || postalCode <= 0) {
            return false;
        }
        final String country = request.getCountry();
        final String state = request.getState();
        final String city = request.getCity();
        final String street = request.getStreet();
        final String buildingNumber = request.getBuildingNumber();
        return isValidString(country) && isValidString(state) && isValidString(city) && isValidString(street) && isValidString(buildingNumber);
    }
}
