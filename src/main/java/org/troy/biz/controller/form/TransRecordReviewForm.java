/**
 * YY.com Inc.
 * Copyright (c) 2011-2014 All Rights Reserved.
 */
package org.troy.biz.controller.form;

import java.io.Serializable;

/**
 * 
 * @author helloKitty
 * @version $Id: TransRecordReviewForm.java, v 0.1 2014-8-14 下午08:08:24 helloKitty Exp $
 */
public class TransRecordReviewForm implements Serializable {
    /** 描述  */
    private static final long serialVersionUID = 8430941165882152228L;

    private Long              transRecordId;

    private String            comment;

    private String            result;

    /**
     * Getter method for property <tt>transRecordId</tt>.
     * 
     * @return property value of transRecordId
     */
    public Long getTransRecordId() {
        return transRecordId;
    }

    /**
     * Setter method for property <tt>transRecordId</tt>.
     * 
     * @param transRecordId value to be assigned to property transRecordId
     */
    public void setTransRecordId(Long transRecordId) {
        this.transRecordId = transRecordId;
    }

    /**
     * Getter method for property <tt>comment</tt>.
     * 
     * @return property value of comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter method for property <tt>comment</tt>.
     * 
     * @param comment value to be assigned to property comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Getter method for property <tt>result</tt>.
     * 
     * @return property value of result
     */
    public String getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     * 
     * @param result value to be assigned to property result
     */
    public void setResult(String result) {
        this.result = result;
    }

}
