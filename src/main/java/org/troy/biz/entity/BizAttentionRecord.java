package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.troy.biz.constant.BizConstant;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.StringUtil;
import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizAttentionRecordEntity </p> 
 *
 * <p>Description:biz_attention_record 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
@Entity
@Table(name = "biz_attention_record")
public class BizAttentionRecord extends IdEntity {

    /**
     * 日志对象
     */
    private static final Logger logger           = Logger.getLogger(BizAttentionRecord.class);

    /** 序列化Id */
    private static final long   serialVersionUID = 6809230230578667985L;

    /**
     * 分隔符
     */
    private static final String SPLIT            = BizConstant.SPLIT;

    /**
     *会员ID
     */
    @Column(length = 10)
    private long                memberId;

    /**
     *被关注的会员ID（大师I）
     */
    @Column(length = 10)
    private long                attentedMemberId;

    /**
     *资金类型N
     */
    @Column(length = 10)
    private String              type;

    /**
     *具体资金类型下面的值
     */
    @Column(length = 19)
    private Long                value;

    /**
     *每日花费
     */
    @Column(length = 100)
    private String              dailyCost;

    /**
     *评分
     */
    @Column(length = 5)
    private String              starCount;

    /**
     *对大师的评价
     */
    @Column(length = 1000)
    private String              memberDesc;

    /**
     *扩展字段
     */
    @Column(length = 200)
    private String              extendField;

    /**
     *创建时间
     */
    @Column(length = 19)
    private Date                gmtCreate;

    /**
     *修改时间
     */
    @Column(length = 19)
    private Date                gmtModified;

    /**
     * 关注天数
     */
    @Column(length = 100)
    private String              attentDayNum;

    /**
     * 获取最近的关注天数
     * 
     * @return
     */
    public int getLastestAttentDay() {
        try {
            String[] str = StringUtils.split(this.attentDayNum, SPLIT);
            return Integer.parseInt(str[0]);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取最近的一次消费
     * 
     * @return
     */
    public long getLastestTotalCost() {
        try {
            String[] attentDayNumStr = StringUtils.split(this.attentDayNum, SPLIT);
            String[] dailyCostStr = StringUtils.split(this.dailyCost, SPLIT);
            return Integer.parseInt(attentDayNumStr[attentDayNumStr.length - 1])
                   * Long.parseLong(dailyCostStr[dailyCostStr.length - 1]);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取最近的日消费
     * 
     * @return
     */
    public long getLastestDailyCost() {
        try {
            String[] str = StringUtils.split(this.dailyCost, SPLIT);
            return Integer.parseInt(str[0]);
        } catch (Exception e) {
            return 0;
        }
    }

    public int getLeftDay() {
        String[] str = StringUtils.split(this.attentDayNum, SPLIT);
        int i = 0;
        for (String s : str) {
            if (StringUtils.isBlank(s)) {
                continue;
            }
            i += Integer.parseInt(s);
        }
        return i;
    }

    /**
     * 执行扣费逻辑
     * @throws Exception 
     */
    public void execDeduct() throws Exception {
        try {
            //关注天数和每天花费是 ";" 左右一一对应的  3；4 --- 10；20
            String[] attentDayStrs = StringUtils.split(this.attentDayNum, SPLIT);
            String[] dailyCostStrs = StringUtils.split(this.dailyCost, SPLIT);
            if ((null == attentDayStrs || attentDayStrs.length == 0) || null == dailyCostStrs
                || dailyCostStrs.length == 0) {
                return;
            }
            String dailyCost = dailyCostStrs[0];

            int days = -1;
            //获取最早关注天数 进行计算
            days = Integer.parseInt(attentDayStrs[0]);

            //执行扣费逻辑 1. 计算关注天数,如果天数只剩下一天，就讲该天数移除，2. 计算每日花费
            if (--days == 0) {
                attentDayStrs[0] = null;
                dailyCostStrs[0] = null;
            } else {
                attentDayStrs[0] = days + "";
            }
            this.value -= Integer.parseInt(dailyCost);
            this.setAttentDayNum(StringUtil.joinWithoutNull(attentDayStrs, SPLIT));
            this.setDailyCost(StringUtil.joinWithoutNull(dailyCostStrs, SPLIT));
        } catch (Exception e) {
            logger.error("执行代扣失败，失败的代扣记录Id:[" + this.getId() + "],请检查对应的参数！", e);
            throw e;
        }
    }

    /**
     * 延长关注天数
     * 
     * @param day           延长的天数
     * @param dailyCost     延长的天数对应的每天需要花费的数量
     */
    public void addAttentDay(long day, long dailyCost) {
        this.attentDayNum += SPLIT + day;
        this.dailyCost += SPLIT + dailyCost;
        this.extendField += StringUtils.LF
                            + DateUtil.date2String(new Date(), BizConstant.DATE_TIME_FORMAT);
        this.value += day * dailyCost;
    }

    /**
    * @return the id
    */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
      * @param memberId the memberId to set
      */
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    /**
      * @return the memberId 
      */
    public long getMemberId() {
        return this.memberId;
    }

    /**
      * @param attentedMemberId the attentedMemberId to set
      */
    public void setAttentedMemberId(long attentedMemberId) {
        this.attentedMemberId = attentedMemberId;
    }

    /**
      * @return the attentedMemberId 
      */
    public long getAttentedMemberId() {
        return this.attentedMemberId;
    }

    /**
      * @param type the type to set
      */
    public void setType(String type) {
        this.type = type;
    }

    /**
      * @return the type 
      */
    public String getType() {
        return this.type;
    }

    /**
      * @param value the value to set
      */
    public void setValue(Long value) {
        this.value = value;
    }

    /**
      * @return the value 
      */
    public Long getValue() {
        return this.value;
    }

    /**
      * @param dailyCost the dailyCost to set
      */
    public void setDailyCost(String dailyCost) {
        this.dailyCost = dailyCost;
    }

    /**
      * @return the dailyCost 
      */
    public String getDailyCost() {
        return this.dailyCost;
    }

    /**
      * @param memberDesc the memberDesc to set
      */
    public void setMemberDesc(String memberDesc) {
        this.memberDesc = memberDesc;
    }

    /**
      * @return the memberDesc 
      */
    public String getMemberDesc() {
        return this.memberDesc;
    }

    /**
      * @param extendField the extendField to set
      */
    public void setExtendField(String extendField) {
        this.extendField = extendField;
    }

    /**
      * @return the extendField 
      */
    public String getExtendField() {
        return this.extendField;
    }

    /**
      * @param gmtCreate the gmtCreate to set
      */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
      * @return the gmtCreate 
      */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /**
      * @param gmtModified the gmtModified to set
      */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
      * @return the gmtModified 
      */
    public Date getGmtModified() {
        return this.gmtModified;
    }

    public String getStarCount() {
        return starCount;
    }

    public void setStarCount(String starCount) {
        this.starCount = starCount;
    }

    public String getAttentDayNum() {
        return attentDayNum;
    }

    public void setAttentDayNum(String attentDayNum) {
        this.attentDayNum = attentDayNum;
    }

}
