package com.catchtable.api.user.DTO;

import com.catchtable.api.user.repository.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class UserDTO {
    private long id;
    private String name;
    private String phoneNumber;
    private String nickName;
    private String avartar;
    private Date createdAt;  // Timestamp → Date 변경

    public static UserDTO of(User user) {
        UserDTO dto = new UserDTO();
        dto.id = user.getId();
        dto.name = user.getName();
        dto.phoneNumber = user.getPhoneNumber();
        dto.nickName = user.getNickName();
        dto.avartar = user.getAvartar();
        dto.createdAt = user.getCreatedAt();  // 생성일자 추가

        return dto;
    }
}
