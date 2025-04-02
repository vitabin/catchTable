package com.catchtable.api.user.domain;

import com.catchtable.api.auth.service.DTO.SignUpRequestDTO;
import com.catchtable.api.auth.domain.TokenEntity;
import com.catchtable.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="user")
@Getter @Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    public static UserEntity signUp(SignUpRequestDTO signUpRequestDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.userName = signUpRequestDTO.getUserName();
        userEntity.password = signUpRequestDTO.getPassword();
        userEntity.realName = signUpRequestDTO.getRealName();
        userEntity.phoneNumber = signUpRequestDTO.getPhoneNumber();
        userEntity.nickName = signUpRequestDTO.getNickName();
        userEntity.role = signUpRequestDTO.getRole();

        return userEntity;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avartar='" + avartar + '\'' +
                ", token=" + token + '\'' +
                ", createdAt=" + super.getCreatedAt() + '\'' +
                ", deletedAt=" + deletedAt + '\'' +
                ", role=" + role + '\'' +
                '}';
    }
}
