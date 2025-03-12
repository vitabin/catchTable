package com.catchtable.api.user.domain;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.data.annotation.Id;

import java.util.Date;

//@Entity
@Getter @Setter
@NoArgsConstructor
public class UserEntity {
    private long id;
    private String userName;
    private String name;
    private String phoneNumber;
    private String nickName;
    private String avartar;
    private Date createdAt;  // Timestamp → Date 변경
}
