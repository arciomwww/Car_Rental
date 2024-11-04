package com.example.CarRental.controller;

import com.example.CarRental.dto.RentalDto;
import com.example.CarRental.service.RentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/rentals")
@Validated
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRental(@Valid @RequestBody RentalDto rentalDto) {
        try {
            rentalService.createRental(rentalDto);
            return ResponseEntity.ok("Rental created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<RentalDto> updateRental(@Valid @PathVariable Long id, @RequestBody RentalDto rentalDto) {
        RentalDto updateRental = rentalService.updateRental(id, rentalDto);
        return new ResponseEntity<>(updateRental, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/read/all")
    public ResponseEntity<Page<RentalDto>> getAllRentals(@PageableDefault(size = 10) Pageable pageable) {
        Page<RentalDto> rentals = rentalService.getAllRentals(pageable);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/read/{id}")
    public ResponseEntity<RentalDto> getRentalById(@PathVariable Long id) {
        RentalDto rentalDto = rentalService.getRentalById(id);
        return new ResponseEntity<>(rentalDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user/{userId}/history")
    public List<RentalDto> getUserRentalHistory(@PathVariable Long userId) {
        return rentalService.getUserRentalHistory(userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/car/{carId}")
    public ResponseEntity<List<RentalDto>> getRentalsByCarId(@PathVariable Long carId, Pageable pageable) {
        List<RentalDto> rentals = rentalService.getRentalsByCarId(carId, pageable);
        return ResponseEntity.ok(rentals);
    }
}
