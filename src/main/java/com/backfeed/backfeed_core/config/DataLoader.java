package com.backfeed.backfeed_core.config;

import com.backfeed.backfeed_core.entities.Role;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.exceptions.RoleNotFoundException;
import com.backfeed.backfeed_core.repositories.RoleRepository;
import com.backfeed.backfeed_core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Value("${superadmin.email}")
    private String email;

    @Value("${superadmin.password}")
    private String password;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Role role = roleRepository.findByName("SUPER_ADMIN");
        if(role == null){
            role = roleRepository.save(new Role("SUPER_ADMIN"));
        }

        User superAdmin = new User("Super", "Admin", email, passwordEncoder.encode(password), role);
        if(userRepository.existsByEmail(email)){
            return;
        }

        userRepository.save(superAdmin);
    }
}
