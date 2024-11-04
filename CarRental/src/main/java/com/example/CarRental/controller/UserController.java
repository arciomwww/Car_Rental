package com.example.CarRental.controller;

import com.example.CarRental.dto.UserDto;
import com.example.CarRental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/read/{id}")
    @PreAuthorize("hasAnyRole(\"USER\", \"ADMIN\") and #id == authentication.principal.id")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole(\"USER\", \"ADMIN\") and #id == authentication.principal.id")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole(\"USER\", \"ADMIN\") and #id == authentication.principal.id")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        Page<UserDto> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/filter/username/{username}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByUsername(@PathVariable String username) {
        List<UserDto> users = userService.findByUsername(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/filter/email/{email}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByEmail(@PathVariable String email) {
        List<UserDto> users = userService.findByEmail(email);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/filter/firstName/{firstName}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByFirstName(@PathVariable String firstName) {
        List<UserDto> users = userService.findByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/filter/lastName/{lastName}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByLastName(@PathVariable String lastName) {
        List<UserDto> users = userService.findByLastName(lastName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/filter/enabled/{enabled}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByEnabled(@PathVariable Boolean enabled) {
        List<UserDto> users = userService.findByEnabled(enabled);
        return ResponseEntity.ok(users);
    }
}
