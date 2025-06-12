package com.catchtable.api.user.DTO;

import com.catchtable.api.user.domain.UserEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserDTO {

    private long id;
    private String userName;
    private String realName;
    private String nickName;
    private String avatar;
    private LocalDateTime createdAt;

    public static UserDTO from(UserEntity user) {
        UserDTO dto = new UserDTO();
        dto.id = user.getId();
        dto.userName = user.getUserName();
        dto.realName = user.getRealName();
        dto.nickName = user.getNickName();
        dto.avatar = user.getAvatar();
        dto.createdAt = user.getCreatedAt();

        return dto;
    }
}
