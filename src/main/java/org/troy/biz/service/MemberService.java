package org.troy.biz.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.troy.biz.entity.BizMember;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:MemberService </p> 
 *
 * <p>Description:Member 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface MemberService extends BaseJdbcService {

    /**
     * 增加
     * @param member
     * @throws ServiceException
     */
    void save(BizMember member) throws ServiceException;

    /**
     * 保存用户的时候伴随推广信息的登记
     * 
     * @param member
     * @param key
     * @throws ServiceException
     */
    void saveWithSpreadKey(BizMember member, String key) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param member
     * @throws ServiceException
     */
    void update(BizMember member) throws ServiceException;

    void updateWithFinance(BizMember member, String coinValue) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizMember get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizMember> findAll(Page page);

    /**
     * 通过用户名来获取用户对象
     * 
     * @param username
     * @return
     */
    BizMember getBizMemberByUserName(String username);

    /**
     * 获取管理員账号
     * 
     * @param username
     * @return
     */
    BizMember getAdminAccount();

    /**
     * 通过大师编号获取大师信息
     * 
     * @param memberNo
     * @return
     */
    BizMember getBizMemberByMemberNo(String memberNo);

    List<BizMember> getBizMemberByName(String name);

    BizMember getSingleBizMemberByName(String name);

    /**
     * 获取会员列表
     * @param specification
     * @param page
     * @return
     */
    List<BizMember> findBizMembers(Specification<BizMember> specification, Page page);

    /**
     * 获取会员列表
     * @param type
     * @param vali
     * @return
     */
    List<BizMember> findBizMemberByInvestAndValidate(String type, String vali);

}
