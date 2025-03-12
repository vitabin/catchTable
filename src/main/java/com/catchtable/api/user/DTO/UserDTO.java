package com.catchtable.api.user.DTO;

import com.catchtable.api.user.domain.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class UserDTO {
    private long id;
    private String userName;
    private String realName;
    private String phoneNumber;
    private String nickName;
    private String avartar;
    private Date createdAt;  // Timestamp → Date 변경

    public static UserDTO of(UserEntity user) {
        UserDTO dto = new UserDTO();
        dto.id = user.getId();
        dto.userName = user.getUserName();
        dto.realName = user.getName();
        dto.phoneNumber = user.getPhoneNumber();
        dto.nickName = user.getNickName();
        dto.avartar = user.getAvartar();
        dto.createdAt = user.getCreatedAt();

        return dto;
    }
}
