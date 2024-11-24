package com.devteria.identity_service.configuration;

import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.enums.Role;
import com.devteria.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j // Cần để ghi log
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) { // Được khởi chạy mổi khi Application đc start
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
//                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
