package com.example.CarRental.service;

import com.example.CarRental.dto.UserRolesDto;
import com.example.CarRental.entity.Role;
import com.example.CarRental.entity.User;
import com.example.CarRental.entity.UserRoles;
import com.example.CarRental.repository.RoleRepository;
import com.example.CarRental.repository.UserRepository;
import com.example.CarRental.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserRolesService {
    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    public Page<UserRolesDto> getAllUserRoles(Pageable pageable) {
        return userRolesRepository.findAll(pageable).map(this::convertToDto);
    }


    public UserRolesDto getUserRoleById(Long id) {
        Optional<UserRoles> userRole = userRolesRepository.findById(id);
        return userRole.map(this::convertToDto).orElse(null);
    }


    public UserRolesDto updateUserRole(Long id, UserRolesDto userRolesDto) {
        Optional<UserRoles> optionalUserRole = userRolesRepository.findById(id);
        if (optionalUserRole.isPresent()) {
            UserRoles userRole = optionalUserRole.get();

            User user = userRepository.findById(userRolesDto.getUserId()).orElse(null);
            Role role = roleRepository.findById(userRolesDto.getRoleId()).orElse(null);

            if (user != null && role != null) {
                userRole.setUser(user);
                userRole.setRole(role);
                UserRoles updatedUserRole = userRolesRepository.save(userRole);
                return convertToDto(updatedUserRole);
            }
        }
        return null;
    }


    public boolean deleteUserRole(Long id) {
        if (userRolesRepository.existsById(id)) {
            userRolesRepository.deleteById(id);
            return true;
        }
        return false;
    }


    private UserRolesDto convertToDto(UserRoles userRole) {
        return UserRolesDto.builder()
                .id(userRole.getId())
                .userId(userRole.getUser().getId())
                .roleId(userRole.getRole().getId())
                .build();
    }
}
