package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.baizhitong.resource.model.share.ShareResTypeL1;
import com.baizhitong.utils.DateUtils;

/**
 * 资源一级分类VO实体类
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public class ShareResTypeL1Vo {
    /** 名称 */
    private Integer   code;
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
     * 资源一级分类VO实体类
     */
    public ShareResTypeL1Vo() {
    }

    /**
     * 资源一级分类VO实体类
     * 
     * @param shareResTypeL1 资源一级分类
     */
    public ShareResTypeL1Vo(ShareResTypeL1 shareResTypeL1) {
        if (shareResTypeL1 != null) {
            this.code = shareResTypeL1.getCode();
            this.name = shareResTypeL1.getName();
            this.description = shareResTypeL1.getDescription();
            this.modifyTime = shareResTypeL1.getModifyTime();
            this.modifyIP = shareResTypeL1.getModifyIP();
        }
    }

    /**
     * 获取资源一级分类VO集合
     * 
     * @param list 资源一级分类集合
     * @return 集合
     */
    public List<ShareResTypeL1Vo> getVoList(List<ShareResTypeL1> list) {
        List<ShareResTypeL1Vo> voList = new ArrayList<ShareResTypeL1Vo>();
        if (list != null && list.size() > 0) {
            for (ShareResTypeL1 shareResTypeL1 : list) {
                voList.add(new ShareResTypeL1Vo(shareResTypeL1));
            }
        }
        return voList;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
