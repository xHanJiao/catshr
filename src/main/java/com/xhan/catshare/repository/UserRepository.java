package com.xhan.catshare.repository;

import com.xhan.catshare.entity.UserDO;
import com.xhan.catshare.entity.projection.CredentialPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDO, Integer>{
    CredentialPair getByAccount(String account);
}
