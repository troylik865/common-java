package org.troy.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.troy.biz.dao.BizMobileMsgDao;
import org.troy.biz.entity.BizMobileMsg;
import org.troy.biz.service.MobileMsgService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/**
 * 
 * 短信相关操作的实现类
 *
 *
 * @author siren.lb
 * @version $Id: MobileMsgServiceImpl.java,v 0.1 2014年7月29日 下午12:04:22 siren.lb Exp $
 */
@Service
public class MobileMsgServiceImpl extends BaseJdbcServiceImpl implements MobileMsgService {

    @Autowired
    private BizMobileMsgDao bizMobileMsgDao;

    @Override
    public void save(BizMobileMsg bizMobileMsg) throws ServiceException {
        bizMobileMsgDao.save(bizMobileMsg);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        bizMobileMsgDao.delete(id);
    }

    @Override
    public void update(BizMobileMsg bizMobileMsg) throws ServiceException {
        bizMobileMsgDao.save(bizMobileMsg);
    }

    @Override
    public BizMobileMsg get(Long id) {
        return bizMobileMsgDao.findOne(id);
    }

    @Override
    public List<BizMobileMsg> findAll(Page page) {
        return PageUtils.getListData(bizMobileMsgDao.findAll(PageUtils.createPageable(page)), page);
    }

}
