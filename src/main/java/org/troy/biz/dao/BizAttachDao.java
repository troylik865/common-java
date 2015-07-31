package org.troy.biz.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizAttach;

/***
 * <p>Title:BizAttachDao </p> 
 *
 * <p>Description:BizAttach 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
public interface BizAttachDao extends JpaRepository<BizAttach, Long> {

    Page<BizAttach> findByAttachTypeOrderByGmtCreateDesc(Pageable page, String attachType);

    Page<BizAttach> findByAttachTypeAndRefIdOrderByGmtCreateDesc(Pageable page, String attachType,
                                                                 Long refId);

    Page<BizAttach> findByAttachTypeAndRefIdInOrderByGmtCreateDesc(Pageable page,
                                                                   String attachType,
                                                                   Collection<Long> refId);

    /**
     * 通过附件类型和关联的业务主键获取到相应的附件列表
     * 
     * @param attachType    附件类型
     * @param refId           关联的业务主键 
     * @return                      
     */
    List<BizAttach> findByAttachTypeAndRefId(String attachType, Long refId);
    
    List<BizAttach> findByAttachTypeAndRefIdOrderByGmtCreateDesc(String attachType, Long refId);

}
