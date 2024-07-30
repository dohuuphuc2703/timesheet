package com.example.timesheet.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.timesheet.entity.Role;
import com.example.timesheet.entity.TimeSheet;
import com.example.timesheet.entity.User;
import com.example.timesheet.repository.RoleRepository;
import com.example.timesheet.repository.TimeSheetRepository;
import com.example.timesheet.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(
            UserRepository userRepository, TimeSheetRepository timeSheetRepository, RoleRepository roleRepository) {
        return (args) -> {
            if (userRepository.findByUsername("admin.admin@ncc.asia").isEmpty()) {
                roleRepository.save(
                        Role.builder().name("USER").description("User role").build());
                Role adminRole = roleRepository.save(
                        Role.builder().name("ADMIN").description("Admin role").build());
                HashSet<Role> roles = new HashSet();
                roles.add(adminRole);
                TimeSheet timeSheet =
                        timeSheetRepository.save(TimeSheet.builder().build());
                User user = User.builder()
                        .username("admin.admin@ncc.asia")
                        .name("admin.admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
