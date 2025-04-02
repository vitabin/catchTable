package com.catchtable.api.auth.service;

import com.catchtable.api.auth.domain.TokenEntity;
import com.catchtable.api.auth.repository.TokenRepository;
import com.catchtable.api.auth.service.DTO.SignInRequestDTO;
import com.catchtable.api.auth.service.DTO.SignUpRequestDTO;
import com.catchtable.api.auth.service.DTO.TokenDTO;
import com.catchtable.api.user.domain.UserEntity;
import com.catchtable.api.user.repository.UserRepository;
import com.catchtable.api.user.service.DTO.UserDTO;
import com.catchtable.util.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityManager;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
    private final EntityManager em;

    public UserDTO signUp(SignUpRequestDTO signUpRequestDTO) throws HttpServerErrorException {
        UserEntity entity = userRepository.save(UserEntity.signUp(signUpRequestDTO));
        return UserDTO.from(entity);
    }

    public boolean checkUserName(String username) {
        return userRepository.findByUserName(username).isPresent();
    }

    public TokenDTO signIn(SignInRequestDTO signInRequestDTO) throws ExpiredJwtException, ResponseStatusException {
        UserEntity user = userRepository.findByUserNameAndPassword(signInRequestDTO.getUserName(), signInRequestDTO.getPassword())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 ID 또는 비밀번호입니다."));

        if (user.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "탈퇴한 유저입니다.");
        }

        TokenEntity tokenEntity = user.getToken();
        String accessKey = jwtUtil.generateAccessToken(user.getUserName(), user.getRole().toString());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserName(),  user.getRole().toString());

        if (tokenEntity == null) {
            TokenEntity token = TokenEntity.create(user,  accessKey, refreshToken);
            tokenRepository.save(token);

            return TokenDTO.create(accessKey, refreshToken);
        }

        tokenEntity.update(accessKey, refreshToken);
        tokenRepository.save(tokenEntity);

        return TokenDTO.create(accessKey, refreshToken);
    }

    public TokenDTO refresh(String refreshToken) throws ExpiredJwtException, ResponseStatusException {
        String token = jwtUtil.resolveToken(refreshToken);

        TokenEntity tokenEntity = tokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그아웃한 유저입니다."));

        UserEntity user = tokenEntity.getUser();
        String accessToken = jwtUtil.generateAccessToken(user.getUserName(), user.getRole().toString());
        tokenEntity.setAccessToken(accessToken);
        tokenRepository.save(tokenEntity);

        return TokenDTO.create(accessToken, token);
    }

    public void signOut(String refreshToken) {
        String token = jwtUtil.resolveToken(refreshToken);

        TokenEntity tokenEntity = tokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 처리된 유저입니다."));
        tokenEntity.setRefreshToken(null);
        tokenRepository.save(tokenEntity);
    }

    @Transactional
    public void withdraw(String accessToken) {
        String token = jwtUtil.resolveToken(accessToken);

        TokenEntity tokenEntity = tokenRepository.findByAccessToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "요청한 유저를 찾을 수 없습니다."));
        tokenRepository.delete(tokenEntity);

        UserEntity user = tokenEntity.getUser();
        user.setDeletedAt(LocalDateTime.now(Clock.systemDefaultZone()));
        user.setUserName(user.getUserName() + "::" + "withdraw" + "::" + user.getDeletedAt());
        user.setToken(null);
        userRepository.save(user);
    }
}
