package com.oolaa.TiresCRM.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import com.oolaa.TiresCRM.jwt.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private final JwtEncoder jwtEncoder;

    public JwtTokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        CustomUserDetails userDetailsr = (CustomUserDetails) authentication.getPrincipal();
        printUserDetails(userDetailsr);

//        var scope = authentication
//                .getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));

        System.out.println(userDetails.getAuthorities());

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        System.out.println(roles.length());

        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(90, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                //.claim("scope", scope)
                .claim("fullName", userDetails.getFullName())
                .claim("roles", roles)
                .build();

        return this.jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    public void printUserDetails(CustomUserDetails userDetails) {
        System.out.println("User Details:");

        // Print username
        System.out.println("Username: " + userDetails.getUsername());

        // Print full name if available
        if (userDetails.getFullName() != null) {
            System.out.println("Full Name: " + userDetails.getFullName());
        } else {
            System.out.println("Full Name: Not Available");
        }

        // Print authorities (roles)
        System.out.println("Authorities:");
        userDetails.getAuthorities().forEach(authority -> System.out.println(" - " + authority.getAuthority()));

        // Print other relevant details if needed
        // For example, account status (enabled/disabled, account non expired, etc.)
        System.out.println("Account Non Expired: " + userDetails.isAccountNonExpired());
        System.out.println("Account Non Locked: " + userDetails.isAccountNonLocked());
        System.out.println("Credentials Non Expired: " + userDetails.isCredentialsNonExpired());
        System.out.println("Enabled: " + userDetails.isEnabled());
    }
}
