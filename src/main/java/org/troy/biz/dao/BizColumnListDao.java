package org.troy.biz.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizColumnList;

/***
 * <p>Title:BizColumnListDao </p> 
 *
 * <p>Description:BizColumnList 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
public interface BizColumnListDao extends JpaRepository<BizColumnList, Long> {

    public Page<BizColumnList> findByItemType(String itemType, Pageable page);

    public Page<BizColumnList> findByItemTypeOrderByItemTypeAsc(String itemType, Pageable page);

    public Page<BizColumnList> findByGmtCreateIsNotNullOrderByItemTypeAsc(Pageable page);

    public Page<BizColumnList> findByItemTypeAndShowPosition(String itemType, String showPosition,
                                                             Pageable page);

    public List<BizColumnList> findByItemTypeIsNotNullOrderByItemTypeAsc();

}
