package com.catchtable.app.api.auth.DTO;

import com.catchtable.app.api.user.domain.UserRole;

public record SignUpParam(String userName, String password, String phoneNumber, String realName,
                          String nickName, UserRole role) {

}
