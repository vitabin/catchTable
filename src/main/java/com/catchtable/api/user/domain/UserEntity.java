package com.catchtable.api.user.domain;

import com.catchtable.api.auth.DTO.SignInRequestDTO;
import com.catchtable.api.auth.DTO.SignUpRequestDTO;
import com.catchtable.api.auth.domain.TokenEntity;
import com.catchtable.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
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

    @Column(name = "avartar")
    private String avartar;

    @OneToOne(mappedBy = "user")
    private TokenEntity token;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "role")
    private String role;

    public UserEntity fromDto(SignInRequestDTO dto) {
        userName = dto.getUserName();
        password = dto.getPassword();
        return this;
    }

    public UserEntity fromDTO(SignUpRequestDTO dto) {
        userName = dto.getUserName();
        password = dto.getPassword();
        realName = dto.getRealName();
        phoneNumber = dto.getPhoneNumber();
        nickName = dto.getNickName();
        return this;
    }

    public UserEntity updateToken(TokenEntity tokenEntity) {
        token = tokenEntity;
        return this;
    }

    public UserEntity withrawUser() {
        deletedAt = LocalDateTime.now(Clock.systemDefaultZone());
        userName = userName + "::" + "withdraw" + "::" + deletedAt;
        return this;
    }

    public void authorize(String role) {
        this.role = role;
    }

    public void authorize() {
        this.role = "ROLE_USER";
    }
}
