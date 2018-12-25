package com.xhan.catshare.entity.dao.user;

import com.xhan.catshare.entity.dto.RegisterDTO;
import com.xhan.catshare.entity.dto.UserInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.xhan.catshare.controller.ControllerConstant.registerURL;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;
import static com.xhan.catshare.controller.ControllerConstant.*;

@Data
@Entity(name = "user")
@EqualsAndHashCode(of = "id", callSuper = false)
public class UserDO extends UserInfoDTO {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, name = "user_id")
    Integer id;


    @Temporal(value = DATE)
    @Column(name = "register_date", nullable = false)
    Date registerDate;

    @Column(name = "check_state", nullable = false)
    private Boolean checked;

    public UserDO() {
        super();
    }

    public UserDO(String account, String username, String password, String email) {
        super(account, username, password, email);
        setRegisterDate(new Date());
    }

    private UserDO(RegisterDTO dto){
        super(dto);
        setRegisterDate(new Date());
    }

    public String getURL() throws UnsupportedEncodingException {
        if(getAccount()==null)
            throw new IllegalStateException();

        return host + registerURL + "?account=" +
                URLEncoder.encode(this.getAccount(), "ASCII") +
                "&" +
                "check=" + getIdentifier();
    }

    public String getIdentifier() {
        String identifier;
        try {
            String result = fromAccountGetUUID();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(result.getBytes());
            byte[] bytes = digest.digest();

            StringBuilder builder = new StringBuilder();
            IntStream.range(0, bytes.length)
                    .forEach(i -> builder.append(bytes[i]));

            identifier = builder.toString().replaceAll("-", "");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            identifier = getAccount();
        }
        return identifier;
    }

    public static UserDO buildUncheckedUser(RegisterDTO dto){
        UserDO userDO = new UserDO(dto);
        userDO.setChecked(false);
        return userDO;
    }

    private String fromAccountGetUUID() throws UnsupportedEncodingException {
        return UUID.nameUUIDFromBytes(getAccount().getBytes("utf-8"))
                .toString()
                .replaceAll("-", "");
    }

}
