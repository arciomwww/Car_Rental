package com.example.CarRental.service;

import com.example.CarRental.dto.RoleDto;
import com.example.CarRental.entity.Role;
import com.example.CarRental.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleDto createRole(RoleDto roleDto) {
        Role role = Role.builder()
                .name(roleDto.getName())
                .build();
        Role savedRole = roleRepository.save(role);
        return convertToDto(savedRole);
    }

    public Page<RoleDto> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    public RoleDto getRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.map(this::convertToDto).orElse(null);
    }

    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            role.setName(roleDto.getName());
            Role updatedRole = roleRepository.save(role);
            return convertToDto(updatedRole);
        }
        return null;
    }

    public boolean deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private RoleDto convertToDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
