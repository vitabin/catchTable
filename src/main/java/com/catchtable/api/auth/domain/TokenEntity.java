package com.catchtable.api.auth.domain;

import com.catchtable.api.user.domain.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="token")
@Getter @Setter
@NoArgsConstructor
public class TokenEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "access_token", unique = true)
    private String accessToken;

    @Column(name = "refresh_token", unique = true)
    private String refreshToken;

    public static TokenEntity create(UserEntity user, String accessToken, String refreshToken) {
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.accessToken = accessToken;
        tokenEntity.refreshToken = refreshToken;

        tokenEntity.setUser(user);
        user.setToken(tokenEntity);

        return tokenEntity;
    }

    public void update(String accessToken, String refreshToken) {
        accessToken = accessToken;
        refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "TokenEntity {" +
                "id='" + id + '\'' +
                ", asseceToken='" + accessToken+ '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", user='" + user + "\'" +
                "}";
    }
}
