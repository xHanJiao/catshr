package com.xhan.catshare.service;

import com.xhan.catshare.entity.dao.record.RaiseRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class RelativeHelper {

    /**
     * 这个方法首先对请求进行检验，如果检验通过就保存一条新纪录
     * （这条记录应该是置位为等待的）
     * 如果校验失败，校验函数应该抛出一个运行时异常（因为不知道具体是什么错误
     * 导致校验失败，所以把抛出异常的工作交给子类）
     * @param acceptorAccount 申请者的账号，未校验是否存在
     * @param raiserId 接受者的id，从session中获得
     */
    @Transactional
    public void checkAndSaveRaiseRecord(String acceptorAccount, Integer raiserId) {
        Integer acceptorId = findUserIdByAccount(acceptorAccount);
        checkRaiseRecord(acceptorId, raiserId);
        saveRaiseRecord(new RaiseRecord(raiserId, acceptorId));
    }

    /**
     * 这个函数用来在保存申请记录之前对记录进行校验，
     * 并且如果校验失败要负责抛出运行时异常
     * 在这里作为参数的两个id都是一定存在的。
     * @param acceptorId 接受者的id
     * @param raiserId 发起者的id
     */
    protected abstract void checkRaiseRecord(Integer acceptorId, Integer raiserId);

    /**
     * 在这里应该已经通过校验，可以插入一条新的记录了
     * @param record 要插入的记录
     */
    protected abstract void saveRaiseRecord(RaiseRecord record);

    public void expireRaiseRecords(){
        List<RaiseRecord> records = findExpiredRecord();
        expire(records);
    }

    /**
     * 确认添加好友的方法，首先要对请求进行校验，
     * 如果校验失败，校验的函数要负责抛出未检查异常
     * 如果校验成功，则将请求记录置位并且插入一条当前
     * 可用关系。
     * @param acceptorAccount 请求者的账号（未确认存在）
     * @param raiserId 接受者的id，从session获得
     */
    @Transactional
    public void confirmRaiseRecord(String acceptorAccount, Integer raiserId){
        Integer acceptorId = findUserIdByAccount(acceptorAccount);
        checkRaiseRecordBeforeConfirm(acceptorId, raiserId);
        setBitAndAddCurrent(acceptorId, raiserId);
    }

    protected abstract void expire(List<RaiseRecord> records);

    protected abstract List<RaiseRecord> findExpiredRecord();

    /**
     * 通过账号找到用户的id，应该保证返回id，如果没有就抛出异常
     * @param account 账号
     * @return 数据库主键id
     */
    public abstract Integer findUserIdByAccount(String account);

    /**
     * 在确认添加好友请求前先对其进行校验，
     * 两个id都确认存在了
     * @param acceptorId 接受者id
     * @param raiserId 发起者id
     */
    protected abstract void checkRaiseRecordBeforeConfirm(Integer acceptorId, Integer raiserId);

    /**
     * 在完成校验后，将请求记录置位，并且插入一条
     * 当前有效好友关系记录
     * @param acceptorId 接受者的id
     * @param raiserId 发起者的id
     */
    protected abstract void setBitAndAddCurrent(Integer acceptorId, Integer raiserId);

    /**
     * 删除好友关系的方法，先检验，检验通过再删除
     * @param acceptorAccount 接受者的账户，未必有效，要检验
     * @param raiserId 发起者的id，来自Session
     */
    @Transactional
    public void deleteFriend(String acceptorAccount, Integer raiserId){
        Integer acceptorId = findUserIdByAccount(acceptorAccount);
        checkBeforeDelete(acceptorId, raiserId);
        delete(acceptorId, raiserId);
    }

    /**
     * 从好友关系表中删除记录，并且向删除记录表中插入一条
     * 新纪录
     * @param acceptorId 接受者的id
     * @param raiserId 发起者的id
     */
    protected abstract void delete(Integer acceptorId, Integer raiserId);

    /**
     * 在删除好友关系之前先对这个请求进行检验
     * @param acceptorId 发起者的id，一定存在
     * @param raiserId 发起者的id，来自Session
     */
    protected abstract void checkBeforeDelete(Integer acceptorId, Integer raiserId);
}
