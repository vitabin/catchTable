package com.catchtable.api.auth.DTO;

public record SignUpParam(String userName, String password, String phoneNumber, String realName,
                          String nickName, String role) {

}
