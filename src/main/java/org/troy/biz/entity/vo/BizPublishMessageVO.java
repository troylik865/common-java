package org.troy.biz.entity.vo;

import java.io.Serializable;

/**
 * 发布信息的VO对象
 * 
 * @author troy
 * @version $Id: BizPublishMessageVO.java, v 0.1 2014年7月6日 下午9:01:00 BizPublishMessage Exp $
 */
public class BizPublishMessageVO implements Serializable {

    /** 序列化ID */
    private static final long serialVersionUID = 6420871041955598581L;

    private long              id;

    private String            content;

    private String            gmtCreateDate;

    private String            gmtCreateTime;

    private String            personNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGmtCreateDate() {
        return gmtCreateDate;
    }

    public void setGmtCreateDate(String gmtCreateDate) {
        this.gmtCreateDate = gmtCreateDate;
    }

    public String getGmtCreateTime() {
        return gmtCreateTime;
    }

    public void setGmtCreateTime(String gmtCreateTime) {
        this.gmtCreateTime = gmtCreateTime;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

}
