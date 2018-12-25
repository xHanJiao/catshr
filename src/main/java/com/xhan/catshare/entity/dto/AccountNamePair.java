package com.xhan.catshare.entity.dto;

import com.xhan.catshare.entity.projection.AccountUsername;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AccountNamePair implements AccountUsername{
    private String account;
    private String username;

    public AccountNamePair(@Size(min = 7, max = 14) String account, @Size(min = 2, max = 7) String username) {
        this.account = account;
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
