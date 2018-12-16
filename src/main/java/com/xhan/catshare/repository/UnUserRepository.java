package com.xhan.catshare.repository;

import com.xhan.catshare.entity.UncheckedUserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnUserRepository extends JpaRepository<UncheckedUserDO, Integer> {

    Optional<UncheckedUserDO> findByAccount(String account);

}
