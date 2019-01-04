package com.xhan.catshare.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterDTO extends UserInfoDTO {

    @Size(min = 7, max = 14)
    private String confirmPassword;

    public RegisterDTO(String username,
                       String password,
                       String confirmPassword,
                       String email) {
        super(username, password, email);
        this.confirmPassword = confirmPassword;
    }

    /**
     * 校验两个密码是否相等
     *
     * @return 如果相等就返回false
     */
    public boolean pwdNotEqual() {
        return !confirmPassword.equals(this.password);
    }
}
