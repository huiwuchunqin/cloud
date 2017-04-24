package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "res_favorite")
public class ResFavorite implements Serializable {

    private static final long                                          serialVersionUID = 1L;
    /** 收藏ID */
    private @Id @Column(insertable = false,
                        updatable = false) Integer                     id;
    /** 资源分类（一级） */
    private Integer                                                    resTypeL1;
    /** 资源分类（二级） */
    private String                                                     resTypeL2;
    /** 资源ID */
    private Integer                                                    resId;
    /** 收藏时间 */
    private Date                                                       collectTime;
    /** 收藏者 */
    private String                                                     userCode;
    /** 收藏所在文件夹 */
    private Long                                                       folderId;

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

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

}
