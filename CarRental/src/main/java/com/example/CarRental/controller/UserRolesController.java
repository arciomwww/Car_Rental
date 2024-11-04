package com.example.CarRental.controller;

import com.example.CarRental.dto.UserRolesDto;
import com.example.CarRental.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/user-roles")
public class UserRolesController {

    @Autowired
    private UserRolesService userRolesService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/read/all")
    public ResponseEntity<Page<UserRolesDto>> getAllUserRoles(Pageable pageable) {
        Page<UserRolesDto> userRolesPage = userRolesService.getAllUserRoles(pageable);
        return ResponseEntity.ok(userRolesPage);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/read/{id}")
    public ResponseEntity<UserRolesDto> getUserRoleById(@PathVariable Long id) {
        UserRolesDto userRole = userRolesService.getUserRoleById(id);
        return userRole != null ? ResponseEntity.ok(userRole) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserRolesDto> updateUserRole(@Valid @PathVariable Long id, @RequestBody UserRolesDto userRolesDto) {
        UserRolesDto updatedUserRole = userRolesService.updateUserRole(id, userRolesDto);
        return updatedUserRole != null ? ResponseEntity.ok(updatedUserRole) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        boolean deleted = userRolesService.deleteUserRole(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
