package org.troy.biz.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.troy.biz.controller.form.TransRecordReviewForm;
import org.troy.biz.entity.BizTransRecord;
import org.troy.biz.entity.vo.BizTransRecordVO;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:TransRecordService </p> 
 *
 * <p>Description:TransRecord 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
public interface TransRecordService extends BaseJdbcService {

    /**
     * 增加
     * @param transRecord
     * @throws ServiceException
     */
    void save(BizTransRecord transRecord) throws ServiceException;

    /**
     * 保存交易记录，并进行交易统计和日志打印
     * 
     * @param transRecord
     * @param form
     * @throws ServiceException
     */
    void saveWithStatisAndReview(BizTransRecord transRecord, TransRecordReviewForm form)
                                                                                        throws ServiceException;

    void modifyLastRecord(final BizTransRecord transRecord) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param transRecord
     * @throws ServiceException
     */
    void update(BizTransRecord transRecord) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizTransRecord get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizTransRecord> findAll(Page page);

    /**
     * 根据主键获取VO对象
     * 
     * @param id
     * @return
     */
    BizTransRecordVO getBizTransRecordVO(Long id);

    /**
     * 保存
     * 
     * @param transRecord
     * @param myfiles1
     * @param myfiles2
     */
    void save(BizTransRecord transRecord, MultipartFile[] myfiles1, MultipartFile[] myfiles2,
              Map<String, String> extendParam);

    /**
     * 通过type分页查询
     * @param page
     */
    List<BizTransRecord> findAllByType(Page page, String type);

    /**
     * 查询初始资金
     * 
     * @param memberId
     * @param type
     * @return
     */
    BizTransRecordVO findFirstTransRecord(long memberId, String type);

    /**
     * 查询上日交易
     * 
     * @param memberId
     * @param type
     * @param currDate
     * @return
     */
    BizTransRecordVO findPreviousTransRecord(long memberId, String investType, Date currDate);

    BizTransRecordVO findLastRecord(long memberId, String investType) throws Exception;;

    /**
     * 查询当日交易
     * 
     * @param memberId
     * @param type
     * @param currDate
     * @return
     */
    BizTransRecordVO findCurrTransRecord(long memberId, String investType, String currDate);

    /**
     * 查询当天的历史交易信息
     * 
     * @param memberId
     * @param investType
     * @param currDate
     * @return
     */
    BizTransRecordVO queryTotalInfoBeforeOneDate(long memberId, String investType, String currDate)
                                                                                                   throws Exception;

    /**
     * 根据时间、投资种类、会员Id 综合查询
     * @param memberId
     * @param investType
     * @param startDate
     * @param endDate
     * @return
     */
    List<BizTransRecordVO> findTransRecrorByMemberAndTypeAndDate(Long memberId, String investType,
                                                                 String startDate, String endDate)
                                                                                                  throws Exception;

    List<BizTransRecordVO> findTransRecrorByMemberAndTypeAndDateAndStatus(Long memberId,
                                                                          String investType,
                                                                          String startDate,
                                                                          String endDate,
                                                                          String status)
                                                                                        throws Exception;

    /**
     * 查询用户一周的交易明细
     * 
     * @param page
     * @param memberId
     * @param investType
     * @return
     * @throws Exception
     */
    List<BizTransRecordVO> findTransRrcordByMemberAndTypeForWeek(Page page, Long memberId);

    List<BizTransRecordVO> findTransRrcordByMemberAndTypeAndStatusForWeek(Page page, Long memberId,
                                                                          String status);

    List<BizTransRecord> findTransRrcordByMemberAndTypeAndStatus(Page page, Long memberId,
                                                                 String type, String status);

    /**
     * 查询待审核的交易记录
     * 
     * @param page
     * @return
     */
    List<BizTransRecordVO> findTransRecordToReview(Page page);

    List<BizTransRecordVO> findDailyTransRecord(Page page);

    void deleteRecordByMemberId(long memberId);

    /**
     * 查询一审核的交易记录
     * 
     * @param page
     * @return
     */
    List<BizTransRecordVO> findTransRecordReviewed(Page page);

    /**
     * 查询用户已提交的交易记录
     * 
     * @param page
     * @param memberId
     * @return
     */
    List<BizTransRecordVO> findTransRecordByUserForSubmit(Page page, Long memberId);

    /**
     * 查询用户已提交的交易记录
     * 
     * @param page
     * @param memberId
     * @param investType
     * @return
     */
    List<BizTransRecordVO> findTransRecordByUserForSubmit(Page page, Long memberId,
                                                          String investType);

    /**
     * 查询用户生效的交易记录
     * 
     * @param page
     * @param memberId
     * @param investType
     * @return
     */
    List<BizTransRecordVO> findTransRecordByUserValid(Page page, Long memberId, String investType);

}
