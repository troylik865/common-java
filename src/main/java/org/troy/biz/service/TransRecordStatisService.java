package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizTransRecord;
import org.troy.biz.entity.BizTransRecordStatis;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/**
 * 
 * 交易记录统计的服务
 *
 * <p>根据交易记录的新增，更新和删除操作，来进行统计记录的生成</p>
 *
 * @author siren.lb
 * @version $Id: TransRecordStatisService.java,v 0.1 2014年7月23日 下午2:59:24 siren.lb Exp $
 */
public interface TransRecordStatisService extends BaseJdbcService {

    /**
     *  根据交易记录生成交易统计记录
     *  
     * @param transrecord           交易记录
     * @return                            创建的交易统计对象
     * @throws ServiceException
     */
    BizTransRecordStatis create(BizTransRecord transrecord) throws ServiceException;

    /**
     * 根据大师的id和相应需要进行统计的交易记录来生成一条统计记录
     * 
     * @param transrecords          需要进行统计的交易记录
     * @param memberId              大师Id
     * @return
     * @throws ServiceException
     */
    BizTransRecordStatis update(List<BizTransRecord> transrecords, Long memberId)
                                                                                 throws ServiceException;

    /**
     * 根据新的交易记录信息和大师Id来生成新的统计记录
     * 
     * @param transrecords          需要进行统计的交易记录
     * @param memberId              会员Id
     * @return
     * @throws ServiceException
     */
    BizTransRecordStatis rebuild(List<BizTransRecord> transrecords, Long memberId)
                                                                                  throws ServiceException;

    /**
     * 增加
     * @param bizTransRecordStatis
     * @throws ServiceException
     */
    void save(BizTransRecordStatis bizTransRecordStatis) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param bizTransRecord
     * @throws ServiceException
     */
    void update(BizTransRecordStatis bizTransRecordStatis) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizTransRecordStatis get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizTransRecordStatis> findAll(Page page);

    /**
     * 分页查询
     * 
     * @param page
     * @param memberId  大师Id
     * @param type          投资品种
     * @return
     */
    BizTransRecordStatis findByMemberId(Long memberId, String type);

    /**
     * 获取大师排名列表
     * 
     * @param page
     * @param investType 投资方向
     * @param isValidated 是否通过参赛验证
     * @return
     */
    List<BizTransRecordStatis> rankMember(Page page, String investType, String isValidated);

    List<BizTransRecordStatis> rankMemberBySQL(Page page, String investType);

    int getMemberRankByMemberIdAndInvest(long memberId, String invest);

    public boolean executeRebuildRank(String investType,BizTransRecordStatis statis);
}
