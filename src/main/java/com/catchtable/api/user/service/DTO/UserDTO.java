package com.catchtable.api.user.service.DTO;

import com.catchtable.api.user.domain.UserEntity;
import com.catchtable.api.user.domain.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter @Setter
public class UserDTO {
    private long id;
    private String userName;
    private String realName;
    private String nickName;
    private String avartar;
    private LocalDateTime createdAt;

    public static UserDTO from(UserEntity user) {
        UserDTO dto = new UserDTO();
        dto.id = user.getId();
        dto.userName = user.getUserName();
        dto.realName = user.getRealName();
        dto.nickName = user.getNickName();
        dto.avartar = user.getAvartar();
        dto.createdAt = user.getCreatedAt();

        return dto;
    }
}
