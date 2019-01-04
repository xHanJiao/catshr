package com.xhan.catshare.service.impl;

import com.xhan.catshare.entity.dao.record.CurrentRelation;
import com.xhan.catshare.entity.dao.record.DeleteRecord;
import com.xhan.catshare.entity.dao.record.RaiseRecord;
import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.entity.dto.IdNamePair;
import com.xhan.catshare.exception.records.*;
import com.xhan.catshare.repository.record.CurrentRecordRepository;
import com.xhan.catshare.repository.record.DeleteRecordRepository;
import com.xhan.catshare.repository.record.RaiseRecordRepository;
import com.xhan.catshare.repository.user.UserRepository;
import com.xhan.catshare.service.RelativeHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.xhan.catshare.entity.dao.record.RaiseRecord.*;
import static java.util.stream.Collectors.toList;

@Service
public class RelativeHelperImpl extends RelativeHelper{

    private final CurrentRecordRepository currRepository;
    private final DeleteRecordRepository deleRepository;
    private final RaiseRecordRepository raiseRepository;
    private final UserRepository userRepository;

    public RelativeHelperImpl(CurrentRecordRepository currentRecordRepository,
                              DeleteRecordRepository deleteRecordRepository,
                              RaiseRecordRepository raiseRecordRepository, UserRepository userRepository) {
        this.currRepository = currentRecordRepository;
        this.deleRepository = deleteRecordRepository;
        this.raiseRepository = raiseRecordRepository;
        this.userRepository = userRepository;
    }

    /** 这个函数用来在保存申请记录之前对记录进行校验，
     * 并且如果校验失败要负责抛出运行时异常
     * 在这里作为参数的两个id都是一定存在的。
     * @param acceptorId 接受者的id
     * @param raiserId 发起者的id
     * @throws AlreadyExistRecordException: 已有未过期申请
     * @throws CannotBeSameException: 不能自己请求自己
     * @throws AlreadyBeFriendException: 已经是好友
     */
    @Override
    protected void checkRaiseRecord(Integer acceptorId, Integer raiserId) {
        isIdExist(acceptorId);

        if(acceptorId.equals(raiserId))
            throw new CannotBeSameException();

        if(isFriend(acceptorId, raiserId))
            throw new AlreadyBeFriendException();

        if(raiseRepository.findByRaiserIdAndAcceptorIdAndCurrentState(
                raiserId, acceptorId, WAIT).isPresent())
            throw new AlreadyExistRecordException();
    }

    private void isIdExist(Integer acceptorId) {
        if (acceptorId == null || !userRepository.existsById(acceptorId))
            throw new IdNotFoundException();
    }

    /**
     * 在这里应该已经通过校验，可以插入一条新的记录了
     * @param record 要插入的记录
     */
    @Override
    protected void saveRaiseRecord(RaiseRecord record) {
        assert record.getCurrentState().equals(WAIT);
        assert record.getRaiseTime() != null;
        raiseRepository.save(record);
    }

    /**
     * 将所有记录的标志位设置为放弃并保存
     * @param records 过期记录列表
     */
    @Override
    protected void expire(List<RaiseRecord> records) {
        for (RaiseRecord record: records) {
            record.setCurrentState(ABORT);
            raiseRepository.save(record);
        }
    }

    /**
     * 找到所有距离现在已经过去若干天的未确认记录
     * @return 符合要求的未确认记录表
     */
    @Override
    protected List<RaiseRecord> findExpiredRecord() {
        return null;
    }

    /**
     * 通过账号找到用户的id，应该保证返回id，如果没有就抛出异常
     * @param email 账号
     * @return 数据库主键id
     */
    @Override
    public Integer findUserIdByEmail(String email) {
        return userRepository
                .findIdByEmail(email)
                .orElseThrow(EmailNotFoundException::new)
                .getId();
    }

    /**
     * 在确认添加好友请求前先对其进行校验，
     * 已知两个id都确认存在了
     * 校验的内容有：
     * 1.当前不是好友关系
     * 2.确认存在着一条有效请求记录
     * @param acceptorId 接受者id
     * @param raiserId 发起者id
     * @throws AlreadyBeFriendException: 已经是好友
     * @throws RaiseRecordNotExistException: 不存在请求记录
     * @throws CannotBeSameException: 不能自己请求自己
     */
    @Override
    protected void checkRaiseRecordBeforeConfirm(Integer acceptorId, Integer raiserId) {
        isIdExist(acceptorId);

        if(acceptorId.equals(raiserId))
            throw new CannotBeSameException();

        if(isFriend(acceptorId, raiserId))
            throw new AlreadyBeFriendException();

        RaiseRecord record = raiseRepository
                .findByRaiserIdAndAcceptorIdAndCurrentState(
                        raiserId, acceptorId, RaiseRecord.WAIT
                ).orElseThrow(RaiseRecordNotExistException::new);
    }

    private boolean isFriend(Integer acceptorId, Integer raiserId) {
        return currRepository.findByRaiserIdAndAcceptorId(
                raiserId, acceptorId).isPresent();
    }

    /**
     * 将请求记录置位，并且插入两条当前有效好友关系记录
     * 一条正向的，一条反向的
     * @param acceptorId 接受者的id
     * @param raiserId 发起者的id
     */
    @Override
    @Transactional
    protected void setBitAndAddCurrent(Integer acceptorId, Integer raiserId) {
        RaiseRecord record = raiseRepository
                .findByRaiserIdAndAcceptorIdAndCurrentState(
                        raiserId, acceptorId, RaiseRecord.WAIT
                ).orElseThrow(RaiseRecordNotExistException::new);
        record.setCurrentState(ACCEPT);
        raiseRepository.save(record);
        UserDO aDO = userRepository
                .findById(acceptorId)
                .orElseThrow(EmailNotFoundException::new);
        UserDO bDO = userRepository
                .findById(raiserId)
                .orElseThrow(EmailNotFoundException::new);
        CurrentRelation.buildCrPairForFriends(aDO.getId(), bDO.getId(),
                aDO.getUsername(), bDO.getUsername())
                .forEach(currRepository::save);

    }

    /**
     * 从好友关系表中删除记录，并且向删除记录表中插入一条
     * 新纪录
     * @param acceptorId 接受者的id
     * @param raiserId 发起者的id
     */
    @Override
    protected void delete(Integer acceptorId, Integer raiserId) {
        currRepository.deleteByRaiserIdAndAcceptorId(
                raiserId, acceptorId
        );
        deleRepository.save(new DeleteRecord(acceptorId, raiserId));
    }

    /**
     * 在删除好友关系之前先对这个请求进行检验
     * 校验的内容有：
     * 1.是不是好友
     * 2.要删除的那个id是不是存在
     * @param acceptorId 发起者的id
     * @param raiserId 发起者的id，来自Session
     */
    @Override
    protected void checkBeforeDelete(Integer acceptorId, Integer raiserId) {
        isIdExist(acceptorId);

        if (!currRepository
                .findByRaiserIdAndAcceptorId(raiserId, acceptorId)
                .isPresent())
        throw new CurrentRelationNotExistException();
    }

    @Override
    public List<IdNamePair> fromIdGetWaitingRecords(Integer userId) {
        return raiseRepository
                 .findByAcceptorIdAndCurrentState(userId, RaiseRecord.WAIT)
                 .stream()
                 .map(this::build)
                 .collect(toList());
    }

    @Override
    public List<IdNamePair> fromIdGetCurrentFriend(Integer userId) {
        return  currRepository.findByRaiserId(userId)
                .stream()
                .map(CurrentRelation::buildPairForAcceptor)
                .collect(toList());
    }


    private IdNamePair build(RaiseRecord record) {
        UserDO raiser = userRepository
                .findById(record.getRaiserId())
                .orElseThrow(IdNotFoundException::new);

        return new IdNamePair(
                raiser.getId(),
                raiser.getUsername());
    }
}
