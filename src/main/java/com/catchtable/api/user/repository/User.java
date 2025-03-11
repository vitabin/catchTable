package com.catchtable.api.user.repository;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.data.annotation.Id;

import java.util.Date;

//@Entity
@Getter @Setter
public class User {
    private long id;
    private String name;
    private String phoneNumber;
    private String nickName;
    private String avartar;
    private Date createdAt;  // Timestamp → Date 변경
}
