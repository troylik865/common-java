package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/**
 * 
 * 新闻实体类
 *
 * @author siren.lb
 * @version $Id: BizNews.java,v 0.1 2014年9月6日 下午5:58:00 siren.lb Exp $
 */
@Entity
@Table(name = "biz_news")
public class BizNews extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 6571429903970592028L;

    /**
     * 标题
     */
    @Column(length = 20)
    private String            title;

    /**
     * 新闻内容
     */
    @Column(length = 2000)
    private String            content;

    /**
     * 扩展信息
     */
    @Column(length = 200)
    private String            extendField;

    /**
     * 创建时间
     */
    private Date              gmtCreate;

    /**
     * 修改时间
     */
    private Date              gmtModified;

    /**
     * 顺序
     */
    @Column(length = 11)
    private int               turn;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

}
