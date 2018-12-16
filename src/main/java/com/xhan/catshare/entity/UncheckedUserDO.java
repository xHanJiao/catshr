package com.xhan.catshare.entity;

import com.xhan.catshare.entity.dto.RegisterDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.stream.IntStream;

import static javax.persistence.GenerationType.IDENTITY;
import static com.xhan.catshare.controller.ControllerConstant.*;

@Data
@Entity
@Table(name = "unchecked_user")
@EqualsAndHashCode(of = "id", callSuper = false)
public class UncheckedUserDO extends UserDO{

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, name = "user_id")
    Integer id;

    @Column(name = "url_identifier", nullable = false)
    String identifier;

    public static void main(String[] args){
        String result = null;
        try {
            result = UUID.nameUUIDFromBytes("xhan".getBytes("utf-8"))
                    .toString()
                    .replaceAll("-", "");
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(result.getBytes());
            byte[] bytes = digest.digest();
            StringBuilder nere = new StringBuilder();
            for (byte abyte: bytes) {
                nere.append(abyte);
            }
            System.out.println(nere.toString().replaceAll("-", ""));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//        System.out.println(result);
    }

    public String buildURL() throws UnsupportedEncodingException {
        if(this.identifier == null)
            throw new IllegalStateException("identifier and account cannot be null");
        return host + registerURL + "?account=" +
                URLEncoder.encode(this.getAccount(), "ASCII") +
                "&" +
                "check=" +
                this.identifier;
    }

    public UncheckedUserDO() {
    }

    public UncheckedUserDO(String account, String username, String password, String email) {
        super(account, username, password, email);
    }

    private UncheckedUserDO(RegisterDTO dto) {
        super(dto);
        buildUUID(dto);
    }

    private void buildUUID(RegisterDTO dto) {
        try {
            String result = UUID.nameUUIDFromBytes("xhan".getBytes("utf-8"))
                    .toString()
                    .replaceAll("-", "");

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(result.getBytes());
            byte[] bytes = digest.digest();

            StringBuilder builder = new StringBuilder();
            IntStream.range(0, bytes.length)
                    .forEach(i -> builder.append(bytes[i]));
            this.identifier = builder.toString().replaceAll("-", "");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            this.identifier = dto.getAccount();
        }
    }

    public static UncheckedUserDO build(RegisterDTO dto) {
        return new UncheckedUserDO(dto);
    }
}
