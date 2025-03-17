package com.pr.soolsool.dto;

import com.pr.soolsool.domain.Gender;
import com.pr.soolsool.domain.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    @NotBlank(message = "이름을 입력하세요")
    private String username;
    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;
    @NotBlank(message = "중복되면 안됨")
    private String email;
    private String number;
    private String selfIntroduce;
    private Gender gender;
    private UserType userType;
}
