package com.catchtable.api.auth.service;

import com.catchtable.api.auth.DTO.SignUpParam;
import com.catchtable.api.auth.domain.TokenEntity;
import com.catchtable.api.auth.repository.TokenRepository;
import com.catchtable.api.user.domain.UserEntity;
import com.catchtable.api.user.repository.UserRepository;
import com.catchtable.exception.exception.AuthException;
import com.catchtable.response.error.AuthErrorCode;
import com.catchtable.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signUp(SignUpParam signUpParam) {
        userRepository.save(UserEntity.of(signUpParam));
    }

    public boolean checkUserName(String username) {
        return userRepository.findByUserName(username)
                             .isPresent();
    }

    public TokenEntity signIn(String username, String password) {
        UserEntity user = userRepository.findByUserName(username)
                                        .orElseThrow(() -> new AuthException(AuthErrorCode.WRONG_USERNAME_OR_PASSWORD));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException(AuthErrorCode.WRONG_USERNAME_OR_PASSWORD);
        }

        TokenEntity oldToken = user.getToken();
        String accessKey = jwtUtil.generateAccessToken(user.getUserName(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserName(), user.getRole());

        if (oldToken != null) {
            TokenEntity newToken = oldToken.updateToken(accessKey, refreshToken);
            user.updateToken(newToken);
            return tokenRepository.save(newToken);
        }

        TokenEntity tokenEntity = TokenEntity.create(user, accessKey, refreshToken);

        return tokenRepository.save(tokenEntity);
    }

    public TokenEntity refresh(String refreshToken) {
        String token = jwtUtil.resolveToken(refreshToken);
        TokenEntity tokenEntity = tokenRepository.findByRefreshToken(token)
                                                 .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_TOKEN));

        UserEntity user = tokenEntity.getUser();
        String access = jwtUtil.generateAccessToken(user.getUserName(), user.getRole());
        String refresh = jwtUtil.generateRefreshToken(user.getUserName(), user.getRole());
        TokenEntity updatedToken = user.getToken()
                                       .updateToken(access, refresh);

        return tokenRepository.save(updatedToken);
    }

    public void signOut(String refreshToken) {
        String token = jwtUtil.resolveToken(refreshToken);
        TokenEntity tokenEntity = tokenRepository.findByRefreshToken(token)
                                                 .orElseThrow(() -> new AuthException(AuthErrorCode.TOKEN_NOT_FOUND));
        UserEntity user = tokenEntity.getUser();
        user.deleteToken();
        tokenRepository.delete(tokenEntity);
    }

    @Transactional
    public void withdraw(String accessToken) {
        String token = jwtUtil.resolveToken(accessToken);

        TokenEntity tokenEntity = tokenRepository.findByAccessToken(token)
                                                 .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_TOKEN));

        UserEntity user = tokenEntity.getUser();
        tokenRepository.delete(tokenEntity);
        userRepository.save(user.withdrawUser());
    }
}
