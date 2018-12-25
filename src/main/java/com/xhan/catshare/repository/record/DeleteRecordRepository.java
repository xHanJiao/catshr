package com.xhan.catshare.repository.record;

import com.xhan.catshare.entity.dao.record.DeleteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteRecordRepository extends JpaRepository<DeleteRecord, Integer>
{

}
