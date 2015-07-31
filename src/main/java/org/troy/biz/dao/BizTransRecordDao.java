package org.troy.biz.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizTransRecord;

/***
 * <p>Title:BizTransRecordDao </p> 
 *
 * <p>Description:BizTransRecord 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
public interface BizTransRecordDao extends JpaRepository<BizTransRecord, Long> {

    List<BizTransRecord> findByInvestTypeOrderByGmtCreateDesc(String recordType);

    List<BizTransRecord> findByMemberId(long memberId);

    public Page<BizTransRecord> findByMemberIdAndInvestTypeOrderByImportDateDesc(Pageable page,
                                                                                 long memberId,
                                                                                 String recordType);

    /**
     * 初期资金
     * @param memberId
     * @param investType
     * @param recordType
     * @return
     */
    public BizTransRecord findByMemberIdAndInvestTypeAndRecordType(long memberId,
                                                                   String investType,
                                                                   String recordType);

    /**
     * 当日工作交易记录
     * 
     * @param memberId
     * @param investType
     * @param importDate
     * @return
     */
    public BizTransRecord findByMemberIdAndInvestTypeAndImportDate(long memberId,
                                                                   String investType,
                                                                   String importDate);

    /**
     * 查询一周内的交易记录
     * 
     * @param memberId
     * @param investType
     * @param date
     * @return
     */
    public Page<BizTransRecord> findByMemberIdAndImportDateAfterOrderByGmtCreateDesc(Pageable page,
                                                                                     long memberId,
                                                                                     String date);

    public Page<BizTransRecord> findByMemberIdAndStatusAndImportDateAfterOrderByGmtCreateDesc(Pageable page,
                                                                                              long memberId,
                                                                                              String status,
                                                                                              String date);

    /**
     * 根据交易记录状态查询
     * 
     * @param status
     * @param pageable
     * @return
     */
    public Page<BizTransRecord> findByStatusOrderByGmtCreateDesc(Pageable pageable, String status);

    /**
     * 查询非某状态的交易记录
     * 
     * @param pageable
     * @return
     */
    public Page<BizTransRecord> findByStatusNotOrderByGmtCreateDesc(Pageable pageable, String status);

    /**
     * 根据用户id查询交易
     * @param pageable
     * @param memberId
     * @param status
     * @return
     */
    public Page<BizTransRecord> findByMemberIdOrderByGmtCreateDesc(Pageable pageable, long memberId);

    /**
     * 根据用户id、交易状态查询交易
     * @param pageable
     * @param memberId
     * @param status
     * @return
     */
    public Page<BizTransRecord> findByMemberIdAndStatusOrderByGmtCreateDesc(Pageable pageable,
                                                                            long memberId,
                                                                            String status);

    /**
     * 根据用户id、交易状态、投资方向查询
     * @param pageable
     * @param memberId
     * @param status
     * @param investType
     * @return
     */
    public Page<BizTransRecord> findByMemberIdAndStatusAndInvestTypeOrderByGmtCreateDesc(Pageable pageable,
                                                                                         long memberId,
                                                                                         String status,
                                                                                         String investType);

    public Page<BizTransRecord> findTransRrcordByMemberIdAndInvestTypeAndStatusOrderByGmtCreateDesc(Pageable pageable,
                                                                                                    Long memberId,
                                                                                                    String type,
                                                                                                    String status);

    //    @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
    //    String findByLastnameOrFirstname(@Param("lastname") String lastname,
    //                                   @Param("firstname") String firstname);
    //    

}
