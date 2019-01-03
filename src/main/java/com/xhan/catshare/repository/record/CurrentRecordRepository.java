package com.xhan.catshare.repository.record;

import com.xhan.catshare.entity.dao.record.CurrentRelation;
import com.xhan.catshare.entity.dto.AccountNamePair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentRecordRepository extends JpaRepository<CurrentRelation, Integer> {
    Optional<CurrentRelation> findByRaiserIdAndAcceptorId(Integer raiserId, Integer acceptorId);

    void deleteByRaiserIdAndAcceptorId(Integer raiserId, Integer acceptorId);

    List<CurrentRelation> findByAcceptorId(Integer acceptorId);

    List<CurrentRelation> findByRaiserId(Integer raiserId);

    boolean existsByRaiserIdAndAcceptorId(int raiserId, int acceptorId);
}
