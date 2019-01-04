package com.xhan.catshare.entity.dto;

import com.xhan.catshare.entity.projection.IdUsername;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class IdNamePair implements IdUsername {
    private Integer id;
    private String username;

    public IdNamePair(Integer id, @Size(min = 2, max = 7) String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setAccount(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
