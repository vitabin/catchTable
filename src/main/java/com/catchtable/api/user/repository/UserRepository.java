package com.catchtable.api.user.repository;

import com.catchtable.api.user.domain.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);

    @EntityGraph(attributePaths = "token")
    Optional<UserEntity> findByUserNameAndPassword(String userName, String password);
}
