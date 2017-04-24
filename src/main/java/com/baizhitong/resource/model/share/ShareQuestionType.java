package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 题型 ShareQuestionType TODO
 * 
 * @author creator BZT 2016年5月11日 下午4:48:50
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "share_question_type")
@Entity
public class ShareQuestionType implements Serializable {
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String            gid;
    /**
     * 编码
     */
    private String            code;
    /**
     * 状态
     */
    private Integer           validStatus;
    /**
     * 名称
     */
    private String            name;
    /**
     * 排序
     */
    private Integer           dispOrder;
    /**
     * 修改程序
     */
    private String            modifyPgm;
    /**
     * 修改时间
     */
    private Timestamp         modifyTime;
    /**
     * 修改ip
     */
    private String            modifyIp;
    /**
     * 系统版本号
     */
    private Integer           sysVer;
    /**
     * 支持系统批阅
     */
    private Integer           flagSysEvaluate;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyIp() {
        return modifyIp;
    }

    public void setModifyIp(String modifyIp) {
        this.modifyIp = modifyIp;
    }

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

    public Integer getFlagSysEvaluate() {
        return flagSysEvaluate;
    }

    public void setFlagSysEvaluate(Integer flagSysEvaluate) {
        this.flagSysEvaluate = flagSysEvaluate;
    }

}
