package com.harithasewana.identity.config;

import com.harithasewana.identity.entity.Role;
import com.harithasewana.identity.repository.RoleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner initializeRoles(RoleRepository roleRepository) {

        return args -> {

            createRoleIfNotExists(roleRepository, "ROLE_CUSTOMER");
            createRoleIfNotExists(roleRepository, "ROLE_ADMIN");

        };
    }

    private void createRoleIfNotExists(
            RoleRepository roleRepository,
            String roleName) {

        if (roleRepository.findByName(roleName).isEmpty()) {

            Role role = new Role();
            role.setName(roleName);

            roleRepository.save(role);
        }
    }
}