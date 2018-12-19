package com.xhan.catshare.repository.record;

import com.xhan.catshare.entity.dao.record.RaiseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RaiseRecordRepository extends JpaRepository<RaiseRecord, Integer> {
    Optional<RaiseRecord> findByRaiserIdAndAcceptorIdAndCurrentState(Integer raiserId,
                                                                     Integer acceptorId,
                                                                     String state);

    List<RaiseRecord> findByCurrentStateAndRaiseTimeBefore(String state, Date raiseTime);

    List<RaiseRecord> findByAcceptorIdAndCurrentState(Integer acceptorId, String state);
//    @Modifying
//    @Query("update RaiseRecord r set r.currentState=?2 where r.id=?1")
//    int setSignal(Integer recordId, String signal);
}
