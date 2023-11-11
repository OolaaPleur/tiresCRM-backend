package com.oolaa.TiresCRM.jwt.user;

import com.oolaa.TiresCRM.jwt.user.authorities.Authorities;
import com.oolaa.TiresCRM.jwt.user.authorities.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository; // Injected Authorities Repository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        // Fetch authorities from the database using authoritiesRepository
        List<GrantedAuthority> grantedAuthorities = authoritiesRepository.findByUsername(username).stream()
                .map(Authorities::getAuthority) // Transform Authorities to String (authority names)
                .map(SimpleGrantedAuthority::new) // Transform authority names to GrantedAuthority objects
                .collect(Collectors.toList());

        return new CustomUserDetails(user, grantedAuthorities);
    }
}

