package com.nulp.mobilepartsshop.api.v1.manufacturer.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.request.CreateManufacturerRequest;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.request.GetManufacturerRequest;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.request.UpdateManufacturerRequest;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.response.CreateManufacturerResponse;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.response.GetManufacturerResponse;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.response.UpdateManufacturerResponse;
import com.nulp.mobilepartsshop.api.v1.manufacturer.service.ManufacturerService;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerLogoStoreException;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Optional;
import java.nio.file.Path;

@RestController
@RequestMapping(ManufacturerController.MAPPING)
@RequiredArgsConstructor
public class ManufacturerController {

    public static final String MAPPING = ApiConstants.GLOBAL_MAPPING + "/manufacturers";

    private final ManufacturerService manufacturerService;

    @GetMapping()
    public ResponseEntity<GetManufacturerResponse> getManufacturer(@RequestBody GetManufacturerRequest request) throws MalformedURLException {
        Optional<Manufacturer> optionalManufacturer = manufacturerService.getManufacturerById(request.getId());
        if (optionalManufacturer.isPresent()) {
            Manufacturer manufacturer = optionalManufacturer.get();
            Path logoPath = Paths.get(manufacturer.getLogo().getFilepath()).toAbsolutePath();
            return ResponseEntity.ok(GetManufacturerResponse.builder()
                    .id(manufacturer.getId())
                    .name(manufacturer.getName())
                    .logoUrl(new UrlResource(logoPath.toUri()).toString())
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GetManufacturerResponse());
        }

    }

    @PostMapping()
    public ResponseEntity<CreateManufacturerResponse> createManufacturer(@ModelAttribute CreateManufacturerRequest request) {
        try {
            final Manufacturer manufacturer = manufacturerService.createManufacturer(request);
            final CreateManufacturerResponse response = CreateManufacturerResponse.builder()
                    .id(manufacturer.getId())
                    .filepath(manufacturer.getLogo().getFilepath())
                    .build();
            return ResponseEntity.ok(response);
        } catch (ManufacturerLogoStoreException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CreateManufacturerResponse());
        }
    }

    @PutMapping()
    public ResponseEntity<UpdateManufacturerResponse> updateManufacturer(@ModelAttribute UpdateManufacturerRequest request) {
        try {
            Manufacturer manufacturer = manufacturerService.updateManufacturer(
                    request.getId(),
                    request.getName(),
                    request.getLogo()
            );
            final UpdateManufacturerResponse response = UpdateManufacturerResponse.builder()
                    .id(manufacturer.getId())
                    .name(manufacturer.getName())
                    .logoUrl(manufacturer.getLogo().getFilepath())
                    .build();
            return ResponseEntity.ok(response);
        } catch (ManufacturerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UpdateManufacturerResponse());
        } catch (ManufacturerLogoStoreException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UpdateManufacturerResponse());
        }
    }

}