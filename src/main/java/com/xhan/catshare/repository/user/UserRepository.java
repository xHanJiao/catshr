package com.xhan.catshare.repository.user;

import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.entity.projection.CredentialPair;
import com.xhan.catshare.entity.projection.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDO, Integer>{
    Optional<CredentialPair> getPairByEmail(String email);

    Optional<UserDO> findByEmail(String account);

    Optional<UserId> findIdByEmail(String email);

    boolean existsById(Integer id);

}
