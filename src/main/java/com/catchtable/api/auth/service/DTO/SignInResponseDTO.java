package com.catchtable.api.auth.service.DTO;

import com.catchtable.api.user.service.DTO.UserDTO;
import lombok.Data;

@Data
public class SignInResponseDTO {
    private TokenDTO token;

    private UserDTO user;

    public static SignInResponseDTO create(TokenDTO tokenDTO, UserDTO userDTO) {
        SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
        signInResponseDTO.token = tokenDTO;
        signInResponseDTO.user = userDTO;
        return signInResponseDTO;
    }
}
