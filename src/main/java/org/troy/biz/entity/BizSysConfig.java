package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/**
 * 系统配置实体对象
 * 
 * @author siren.lb
 * @version $Id: BizSysConfig.java,v 0.1 2014年9月26日 下午5:33:06 siren.lb Exp $
 */
@Entity
@Table(name = "biz_sys_config")
public class BizSysConfig extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 3469625334303083747L;

    @Column(length = 10)
    private String            type;

    @Column(length = 45)
    private String            paramName;

    @Column(length = 100)
    private String            paramValue;

    /**
     *扩展字段
     */
    @Column(length = 100)
    private String            extendField;

    /**
     *创建时间
     */
    @Column(length = 19)
    private Date              gmtCreate;

    /**
     *修改时间
     */
    @Column(length = 19)
    private Date              gmtModified;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getExtendField() {
        return extendField;
    }

    public void setExtendField(String extendField) {
        this.extendField = extendField;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}
