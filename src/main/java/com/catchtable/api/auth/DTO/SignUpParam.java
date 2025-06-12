package com.catchtable.api.auth.DTO;

import com.catchtable.api.user.domain.UserRole;

public record SignUpParam(String userName, String password, String phoneNumber, String realName,
                          String nickName, UserRole role) {

}
