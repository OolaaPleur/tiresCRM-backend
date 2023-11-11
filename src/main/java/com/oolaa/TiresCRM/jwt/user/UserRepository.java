package com.oolaa.TiresCRM.jwt.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);

    @Modifying
    @Transactional
    @Query("update CustomUser c set c.fullName = :fullName where c.username = :username")
    void saveFullName(@Param("username") String username, @Param("fullName") String fullName);
}
