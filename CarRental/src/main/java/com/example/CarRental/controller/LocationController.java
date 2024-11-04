package com.example.CarRental.controller;

import com.example.CarRental.dto.LocationDto;
import com.example.CarRental.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/locations")
@Validated
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createLocation(@Valid @RequestBody LocationDto locationDto) {
        try {
            locationService.createLocation(locationDto);
            return ResponseEntity.ok("Location created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/read/all")
    public ResponseEntity<Page<LocationDto>> getAllLocations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<LocationDto> locations = locationService.getAllLocations(page, size);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id) {
        LocationDto locationDto = locationService.getLocationById(id);
        return new ResponseEntity<>(locationDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<LocationDto> updateLocation(@Valid @PathVariable Long id, @RequestBody  LocationDto locationDto) {
        LocationDto updatedLocation = locationService.updateLocation(id, locationDto);
        return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/read/city/{city}")
    public ResponseEntity<List<LocationDto>> getLocationsByCity(@PathVariable String city) {
        List<LocationDto> locations = locationService.getLocationsByCity(city);
        log.info("Retrieved locations in city: {}", city);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/read/country/{country}")
    public ResponseEntity<List<LocationDto>> getLocationsByCountry(@PathVariable String country) {
        List<LocationDto> locations = locationService.getLocationsByCountry(country);
        log.info("Retrieved locations in country: {}", country);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/read/location-by-car/{carId}")
    public ResponseEntity<LocationDto> getLocationByCarId(@PathVariable Long carId) {
        LocationDto locationDto = locationService.getLocationByCarId(carId);
        return new ResponseEntity<>(locationDto, HttpStatus.OK);
    }

}
