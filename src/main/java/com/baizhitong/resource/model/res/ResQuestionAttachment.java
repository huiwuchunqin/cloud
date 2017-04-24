package com.baizhitong.resource.model.res;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 题目_附件实体类
 * 
 * @author lusm
 *
 */
@Entity
@Table(name = "res_question_attachment")
public class ResQuestionAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 附件主键 */
    private @Id Integer       id;
    /** 试题ID */
    private Integer           questionId;
    /** 附件类型 */
    private String            attachmentType;
    /** 附件路径 */
    private String            attachmentPath;
    /** 显示顺序 */
    private Integer           dispOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

}
