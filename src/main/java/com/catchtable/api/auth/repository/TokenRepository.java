package com.catchtable.api.auth.repository;


import com.catchtable.api.auth.domain.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query("SELECT t FROM TokenEntity t JOIN FETCH t.user WHERE t.refreshToken = :refreshToken")
    Optional<TokenEntity> findByRefreshToken(@Param("refreshToken") String refreshToken);

    @Query("SELECT t FROM TokenEntity t JOIN FETCH t.user WHERE t.accessToken = :accessToken")
    Optional<TokenEntity> findByAccessToken(@Param("accessToken") String accessToken);
}
