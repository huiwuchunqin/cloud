package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.baizhitong.resource.model.share.ShareResTypeL2;
import com.baizhitong.utils.DateUtils;

/**
 * 资源二级分类VO实体类
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public class ShareResTypeL2Vo {
    /** 一级分类编码 */
    private Integer   codeL1;
    /** 名称 */
    private String    code;
    /** 编码 */
    private String    name;
    /** 描述 */
    private String    description;
    /** 修改日期 */
    private Timestamp modifyTime;
    /** 修改日期文本 */
    private String    modifyTimeText;
    /** 修改 */
    private String    modifyIP;

    /**
     * 资源二级分类VO实体类
     */
    public ShareResTypeL2Vo() {
    }

    /**
     * 资源二级分类VO实体类
     * 
     * @param shareResTypeL2 资源二级分类
     */
    public ShareResTypeL2Vo(ShareResTypeL2 shareResTypeL2) {
        if (shareResTypeL2 != null) {
            this.codeL1 = shareResTypeL2.getCodeL1();
            this.code = shareResTypeL2.getCode();
            this.name = shareResTypeL2.getName();
            this.description = shareResTypeL2.getDescription();
            this.modifyTime = shareResTypeL2.getModifyTime();
            this.modifyIP = shareResTypeL2.getModifyIP();
        }
    }

    /**
     * 获取资源二级分类VO集合
     * 
     * @param list 资源二级分类集合
     * @return 集合
     */
    public List<ShareResTypeL2Vo> getVoList(List<ShareResTypeL2> list) {
        List<ShareResTypeL2Vo> voList = new ArrayList<ShareResTypeL2Vo>();
        if (list != null && list.size() > 0) {
            for (ShareResTypeL2 shareResTypeL2 : list) {
                voList.add(new ShareResTypeL2Vo(shareResTypeL2));
            }
        }
        return voList;
    }

    public Integer getCodeL1() {
        return codeL1;
    }

    public void setCodeL1(Integer codeL1) {
        this.codeL1 = codeL1;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyTimeText() {
        if (this.getModifyTime() != null) {
            return DateUtils.formatDate(this.getModifyTime());
        }
        return modifyTimeText;
    }

    public void setModifyTimeText(String modifyTimeText) {
        this.modifyTimeText = modifyTimeText;
    }

    public String getModifyIP() {
        return modifyIP;
    }

    public void setModifyIP(String modifyIP) {
        this.modifyIP = modifyIP;
    }
}
