package com.oolaa.TiresCRM.jwt.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
@Component
public class CustomUserDetailsManager extends JdbcUserDetailsManager {

    private final UserRepository userRepository;

    public CustomUserDetailsManager(DataSource dataSource, UserRepository userRepository) {
        super(dataSource);
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserDetails user) {
        super.createUser(user);
        if (user instanceof CustomUser customUser) {
            userRepository.saveFullName(customUser.getUsername(), customUser.getFullName());
        }
    }

    // Other necessary overrides...
}