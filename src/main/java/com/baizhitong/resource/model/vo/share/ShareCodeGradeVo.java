package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.model.share.ShareCodeGrade;
import com.baizhitong.utils.DateUtils;

/**
 * 年级VO实体类
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public class ShareCodeGradeVo {
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
    /** 学段名称 */
    private String    sectionName;
    /** 学期编码 */
    private String    termCode;

    /**
     * 年级VO实体类
     */
    public ShareCodeGradeVo() {
    }

    /**
     * 年级VO实体类
     * 
     * @param shareCodeGrade 年级
     */
    public ShareCodeGradeVo(ShareCodeGrade shareCodeGrade) {
        if (shareCodeGrade != null) {
            this.code = shareCodeGrade.getCode();
            this.name = shareCodeGrade.getName();
            this.modifyTime = shareCodeGrade.getModifyTime();
            this.modifyIP = shareCodeGrade.getModifyIP();
            this.description = shareCodeGrade.getDescription();
            this.dispOrder = shareCodeGrade.getDispOrder();
        }
    }

    /**
     * 获取年级VO集合
     * 
     * @param list 年级集合
     * @return 集合
     */
    public List<ShareCodeGradeVo> getVoList(List<ShareCodeGrade> list) {
        List<ShareCodeGradeVo> voList = new ArrayList<ShareCodeGradeVo>();
        if (list != null && list.size() > 0) {
            for (ShareCodeGrade shareCodeGrade : list) {
                voList.add(new ShareCodeGradeVo(shareCodeGrade));
            }
        }
        return voList;
    }

    /**
     * 年级VO实体类
     * 
     * @param map 年级
     */
    public ShareCodeGradeVo(Map<String, Object> map) {
        if (map != null) {
            this.code = MapUtils.getString(map, "code");
            this.name = MapUtils.getString(map, "name");
            this.modifyTime = DateUtils.getTimestamp(MapUtils.getString(map, "modifyTime"));
            this.modifyIP = MapUtils.getString(map, "modifyIP");
            this.description = MapUtils.getString(map, "description");
            this.dispOrder = MapUtils.getInteger(map, "dispOrder");
            this.sectionName = MapUtils.getString(map, "sectionName");
            this.termCode = MapUtils.getString(map, "termCode");
        }
    }

    public static ShareCodeGradeVo mapToVo(Map<String, Object> map) {
        ShareCodeGradeVo vo = new ShareCodeGradeVo();
        if (MapUtils.isNotEmpty(map)) {
            vo.setCode(MapUtils.getString(map, "code"));
            vo.setName(MapUtils.getString(map, "name"));
        }
        return vo;
    }

    /**
     * 获取年级VO集合
     * 
     * @param list 年级集合
     * @return 集合
     */
    public List<ShareCodeGradeVo> getMapToVoList(List<Map<String, Object>> mapList) {
        List<ShareCodeGradeVo> voList = new ArrayList<ShareCodeGradeVo>();
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                voList.add(new ShareCodeGradeVo(map));
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

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
