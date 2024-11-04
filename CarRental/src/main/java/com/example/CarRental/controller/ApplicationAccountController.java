package com.example.CarRental.controller;

import com.example.CarRental.dto.ApplicationAccountDto;
import com.example.CarRental.service.ApplicationAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
@Validated
@Slf4j
@RestController
@RequestMapping("/api/application-accounts")
public class ApplicationAccountController {

    private final ApplicationAccountService applicationAccountService;

    @Autowired
    public ApplicationAccountController(ApplicationAccountService applicationAccountService) {
        this.applicationAccountService = applicationAccountService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApplicationAccountDto> createApplicationAccount(@Valid @RequestBody ApplicationAccountDto applicationAccountDto) {
        ApplicationAccountDto createdAccount = applicationAccountService.createApplicationAccount(applicationAccountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/read/{id}")
    public ResponseEntity<ApplicationAccountDto> getApplicationAccount(@PathVariable Long id) {
        ApplicationAccountDto account = applicationAccountService.getApplicationAccount(id);
        return ResponseEntity.ok(account);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationAccountDto> updateApplicationAccount(@Valid @PathVariable Long id, @RequestBody ApplicationAccountDto applicationAccountDto) {
        ApplicationAccountDto updatedAccount = applicationAccountService.updateApplicationAccount(id, applicationAccountDto);
        return ResponseEntity.ok(updatedAccount);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteApplicationAccount(@PathVariable Long id) {
        applicationAccountService.deleteApplicationAccount(id);
        return ResponseEntity.noContent().build();
    }
}
