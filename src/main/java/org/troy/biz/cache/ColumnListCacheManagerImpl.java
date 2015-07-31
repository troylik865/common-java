package org.troy.biz.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.troy.biz.entity.BizColumnList;
import org.troy.biz.service.ColumnListService;

/**
 * 
 * 
 * @author troy
 * @version $Id: ColumnListCacheManager.java, v 0.1 2014年7月6日 上午9:53:44 troy Exp $
 */
@Service("columnListCacheManager")
public class ColumnListCacheManagerImpl extends TroyCacheManager<List<BizColumnList>> {

    @Autowired
    private ColumnListService columnListService;

    @Override
    public List<BizColumnList> getObjectFromDB(String key) {
        return columnListService.findAll();
    }

}
