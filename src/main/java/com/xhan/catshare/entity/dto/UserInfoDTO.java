package com.xhan.catshare.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Data
@NoArgsConstructor
public class UserInfoDTO {

    public UserInfoDTO(@Size(min = 2, max = 7) String username,
                       @Size(min = 7, max = 14) String password,
                       @Email String email) {
        setPassword(password);
        setEmail(email);
        setUsername(username);
    }

    @Size(min = 2, max = 7)
    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Size(min = 7, max = 14)
    @Column(name = "password", nullable = false)
    String password;

    @Email @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    String email;

    public UserInfoDTO(RegisterDTO dto) {
        if (dto.pwdNotEqual())
            throw new IllegalArgumentException();
        setUsername(dto.getUsername());
        setPassword(dto.getPassword());
        setEmail(dto.getEmail());
    }
}
