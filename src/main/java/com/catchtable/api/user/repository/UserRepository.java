package com.catchtable.api.user.repository;

import com.catchtable.api.user.domain.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //    @EntityGraph(attributePaths = "token")
    @Query("select u from UserEntity u where u.userName = :username and u.deletedAt is null")
    Optional<UserEntity> findByUserName(@Param("username") String userame);

}
