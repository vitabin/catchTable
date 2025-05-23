package com.catchtable.app.api.user.repository;

import com.catchtable.app.api.user.domain.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = "token")
    Optional<UserEntity> findByUserName(@Param("username") String userame);

}
