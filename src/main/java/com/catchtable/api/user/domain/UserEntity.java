package com.catchtable.api.user.domain;

import com.catchtable.api.auth.DTO.SignInRequestDTO;
import com.catchtable.api.auth.DTO.SignUpParam;
import com.catchtable.api.auth.domain.TokenEntity;
import com.catchtable.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "avatar")
    private String avatar;

    @OneToOne(mappedBy = "user")
    private TokenEntity token;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static UserEntity of(SignUpParam signUpParam) {
        UserEntity entity = new UserEntity();
        entity.userName = signUpParam.userName();
        entity.password = signUpParam.password();
        entity.realName = signUpParam.realName();
        entity.phoneNumber = signUpParam.phoneNumber();
        entity.nickName = signUpParam.nickName();
        entity.role = signUpParam.role();
        return entity;
    }

    public void fromDto(SignInRequestDTO dto) {
        userName = dto.getUserName();
        password = dto.getPassword();
    }

    public void updateToken(TokenEntity tokenEntity) {
        token = tokenEntity;
    }

    public UserEntity updatePassword(String password) {
        this.password = password;
        return this;
    }

    public void withdrawUser() {
        deletedAt = LocalDateTime.now(Clock.systemDefaultZone());
        deleteToken();
    }

    public void deleteToken() {
        token = null;
    }
}
