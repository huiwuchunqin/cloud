package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 资源评论 实体类
 * 
 * @author lusm 2015/12/07
 *
 */
@Entity
@Table(name = "res_comment")
public class ResComment implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键 id */
    private @Id Integer       id;
    /** 资源ID */
    private Integer           resId;
    /** 资源分类（一级） */
    private Integer           resTypeL1;
    /** 资源分类（二级） */
    private String            resTypeL2;
    /** 所回复的评论ID */
    private Integer           repliedCommentID;
    /** 所回复的评论人用户代码 */
    private String            repliedUserCode;
    /** 评论内容 */
    private String            comment;
    /** 评论人用户代码 */
    private String            commentatorCode;
    /** 评论时间 */
    private Timestamp         commentTime;
    /** 是否匿名 */
    private Integer           flagAnonymous;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResTypeL1() {
        return resTypeL1;
    }

    public void setResTypeL1(Integer resTypeL1) {
        this.resTypeL1 = resTypeL1;
    }

    public String getResTypeL2() {
        return resTypeL2;
    }

    public void setResTypeL2(String resTypeL2) {
        this.resTypeL2 = resTypeL2;
    }

    public Integer getRepliedCommentID() {
        return repliedCommentID;
    }

    public void setRepliedCommentID(Integer repliedCommentID) {
        this.repliedCommentID = repliedCommentID;
    }

    public String getRepliedUserCode() {
        return repliedUserCode;
    }

    public void setRepliedUserCode(String repliedUserCode) {
        this.repliedUserCode = repliedUserCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentatorCode() {
        return commentatorCode;
    }

    public void setCommentatorCode(String commentatorCode) {
        this.commentatorCode = commentatorCode;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getFlagAnonymous() {
        return flagAnonymous;
    }

    public void setFlagAnonymous(Integer flagAnonymous) {
        this.flagAnonymous = flagAnonymous;
    }

}
