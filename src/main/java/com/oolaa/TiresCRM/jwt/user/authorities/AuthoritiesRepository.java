package com.oolaa.TiresCRM.jwt.user.authorities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthorityId> {

    List<Authorities> findByUsername(String username);
}
