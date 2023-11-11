package com.oolaa.TiresCRM.jwt.user;

import com.oolaa.TiresCRM.jwt.user.authorities.Authorities;
import com.oolaa.TiresCRM.jwt.user.authorities.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    public void saveUser(UserSignUpInfo userSignUpInfo) {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        CustomUser user = new CustomUser();
        user.setFullName(userSignUpInfo.name());
        user.setUsername(userSignUpInfo.username());
        user.setPassword(encoder.encode(userSignUpInfo.password()));
        user.setEnabled(true);
        //user.setRole(userSignUpInfo.authorities());

        // Save the user
        userRepository.save(user);
        userRepository.saveFullName(user.getUsername(), userSignUpInfo.name());

        // Insert authorities
        for (Authorities authority : userSignUpInfo.role()) {
            Authorities auth = new Authorities();
            auth.setUsername(userSignUpInfo.username());
            auth.setAuthority(authority.getAuthority());
            authoritiesRepository.save(auth);
        }
    }
}
