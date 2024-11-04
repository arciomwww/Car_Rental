package com.example.CarRental.controller;

import com.example.CarRental.dto.CarModelDto;
import com.example.CarRental.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
@Validated
@RestController
@RequestMapping("/api/car-models")
public class CarModelController {
    private final CarModelService carModelService;

    @Autowired
    public CarModelController(CarModelService carModelService) {
        this.carModelService = carModelService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createCarModel(@Valid @RequestBody CarModelDto carModelDto) {
        CarModelDto created = carModelService.createCarModel(carModelDto);
        return ResponseEntity.ok(created);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<CarModelDto> updateCarModel(@Valid @PathVariable Long id, @RequestBody CarModelDto carModelDto) {
        return ResponseEntity.ok(carModelService.updateCarModel(id, carModelDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCarModel(@PathVariable Long id) {
        carModelService.deleteCarModel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CarModelDto> getCarModelById(@PathVariable Long id) {
        return ResponseEntity.ok(carModelService.getCarModelById(id));
    }
}


