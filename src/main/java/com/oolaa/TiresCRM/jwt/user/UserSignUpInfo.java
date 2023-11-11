package com.oolaa.TiresCRM.jwt.user;

import com.oolaa.TiresCRM.jwt.user.authorities.Authorities;

import java.util.Set;

public record UserSignUpInfo(String name, String username, String password, Set<Authorities> role) {
}
