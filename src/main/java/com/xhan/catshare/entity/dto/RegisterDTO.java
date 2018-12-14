package com.xhan.catshare.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterDTO extends UserInfoDTO {
    private String confirmPassword;

    public boolean checkPwd(){
        return confirmPassword.equals(this.password);
    }
}
