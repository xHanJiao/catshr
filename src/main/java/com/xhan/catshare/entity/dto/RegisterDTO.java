package com.xhan.catshare.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterDTO extends UserInfoDTO {

    @Size(min = 7, max = 14)
    private String confirmPassword;

    public RegisterDTO() {
    }

    public RegisterDTO(String account,
                       String username,
                       String password,
                       String confirmPassword,
                       String email) {
        super(account, username, password, email);
        this.confirmPassword = confirmPassword;
    }

    public boolean checkPwd(){
        return confirmPassword.equals(this.password);
    }
}
