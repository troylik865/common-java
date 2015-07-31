package org.troy.biz.service;

import java.io.File;
import java.util.List;

import org.troy.biz.entity.BizAttach;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:AttachService </p> 
 *
 * <p>Description:Attach 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
public interface AttachService extends BaseJdbcService {

    /**
     * 增加
     * @param attach
     * @throws ServiceException
     */
    void save(BizAttach attach) throws ServiceException;

    void save(List<BizAttach> attachs) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param attach
     * @throws ServiceException
     */
    void update(BizAttach attach) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizAttach get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizAttach> findAll(Page page);

    File getAttachFile(String attachPath);

    List<BizAttach> findByMemberNoAndAttachTypeOrderByGmtCreateDesc(Page page, String memberNo,
                                                                    String attachType);

    List<BizAttach> findByTransRecordIdsAndAttachTypeOrderByGmtCreateDesc(Page page,
                                                                          List<Long> transRecordIds,
                                                                          String attachType);

    List<BizAttach> findByRefIdAndAttachTypeOrderByGmtCreateDesc(Long refId,
                                                                          String attachType);

    /**
     * 通过业务类型和关联度的业务主键获取到对应的附件列表对象
     * 
     * @param attachType    附件类型  主要是指关联的业务
     * @param refId            关联的业务主键 
     * @return                    对应的附件列表
     */
    List<BizAttach> findAttachByAttachTypeAndRefId(String attachType, Long refId);

}
