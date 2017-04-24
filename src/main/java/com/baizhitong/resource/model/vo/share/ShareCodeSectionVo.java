package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.utils.DateUtils;

/**
 * 学段VO实体类
 * 
 * @author creator Cuidc 2015/12/02
 * @author updater
 */
public class ShareCodeSectionVo {
    /** 编码 */
    private String    code;
    /** 名称 */
    private String    name;
    /** 修改日期 */
    private Timestamp modifyTime;
    /** 修改日期文本 */
    private String    modifyTimeText;
    /** 修改IP */
    private String    modifyIP;
    /** 介绍 */
    private String    description;
    /** 排序 */
    private Integer   dispOrder;

    /**
     * 学段VO实体类
     */
    public ShareCodeSectionVo() {
    }

    /**
     * 学段VO实体类
     * 
     * @param shareCodeSection 学段
     */
    public ShareCodeSectionVo(ShareCodeSection shareCodeSection) {
        if (shareCodeSection != null) {
            this.code = shareCodeSection.getCode();
            this.name = shareCodeSection.getName();
            this.modifyTime = shareCodeSection.getModifyTime();
            this.modifyIP = shareCodeSection.getModifyIP();
            this.description = shareCodeSection.getDescription();
        }
    }

    /**
     * 获取学段VO集合
     * 
     * @param list 学段集合
     * @return 集合
     */
    public List<ShareCodeSectionVo> getVoList(List<ShareCodeSection> list) {
        List<ShareCodeSectionVo> voList = new ArrayList<ShareCodeSectionVo>();
        if (list != null && list.size() > 0) {
            for (ShareCodeSection shareCodeSection : list) {
                voList.add(new ShareCodeSectionVo(shareCodeSection));
            }
        }
        return voList;
    }

    /**
     * 学段VO实体类
     * 
     * @param map 年级
     */
    public ShareCodeSectionVo(Map<String, Object> map) {
        if (map != null) {
            this.code = MapUtils.getString(map, "code");
            this.name = MapUtils.getString(map, "name");
            this.modifyTime = DateUtils.getTimestamp(MapUtils.getString(map, "modifyTime"));
            this.modifyIP = MapUtils.getString(map, "modifyIP");
            this.description = MapUtils.getString(map, "description");
            this.dispOrder = MapUtils.getInteger(map, "dispOrder");
        }
    }

    /**
     * 获取学段VO集合
     * 
     * @param list 年级集合
     * @return 集合
     */
    public List<ShareCodeSectionVo> getMapToVoList(List<Map<String, Object>> mapList) {
        List<ShareCodeSectionVo> voList = new ArrayList<ShareCodeSectionVo>();
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                voList.add(new ShareCodeSectionVo(map));
            }
        }
        return voList;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

}
