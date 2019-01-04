package com.xhan.catshare.service;

import com.xhan.catshare.entity.dao.record.RaiseRecord;
import com.xhan.catshare.entity.dto.IdNamePair;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 在这个类里，因为选择了将用户的主键暴露出去，所以所有用到
 * 前端传来的主键的地方，都要对主键进行校验
 */
public abstract class RelativeHelper {

    /**
     * 这个方法首先对请求进行检验，如果检验通过就保存一条新纪录
     * （这条记录应该是置位为等待的）
     * 如果校验失败，校验函数应该抛出一个运行时异常（因为不知道具体是什么错误
     * 导致校验失败，所以把抛出异常的工作交给子类）
     * @param acceptorId 申请者的主键值，未校验是否存在
     * @param raiserId 接受者的id，从session中获得
     */
    @Transactional
    public void checkAndSaveRaiseRecord(Integer acceptorId, Integer raiserId) {
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
     * @param acceptorId 请求者的id（未确认存在）
     * @param raiserId 接受者的id，从session获得
     */
    @Transactional
    public void confirmRaiseRecord(Integer acceptorId, Integer raiserId) {
        checkRaiseRecordBeforeConfirm(acceptorId, raiserId);
        setBitAndAddCurrent(acceptorId, raiserId);
    }

    protected abstract void expire(List<RaiseRecord> records);

    protected abstract List<RaiseRecord> findExpiredRecord();

    /**
     * 通过邮箱找到用户的id，应该保证返回id，如果没有就抛出异常
     * @param email 邮箱
     * @return 数据库主键id
     */
    public abstract Integer findUserIdByEmail(String email);

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
     * @param acceptorId 被删除者的id，未必有效，要检验
     * @param raiserId 发起者的id，来自Session
     */
    @Transactional
    public void deleteFriend(Integer acceptorId, Integer raiserId) {
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

    /**
     * 得到所有申请加好友的申请人，要得到他们的用户名
     * 和主键id（现在还没实现头像）
     * @param userId 当前用户（接受者）的id
     * @return 返回一个RaiseRecordDTO列表
     */
    public abstract List<IdNamePair> fromIdGetWaitingRecords(Integer userId);

    public abstract List<IdNamePair> fromIdGetCurrentFriend(Integer userId);
}
