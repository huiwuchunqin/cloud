package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.utils.DateUtils;

/**
 * 学科VO实体类
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public class ShareCodeSubjectVo {
    /** 编码 */
    private String    code;
    /** 名称 */
    private String    name;
    /** 描述 */
    private String    description;
    /** 修改日期 */
    private Timestamp modifyTime;
    /** 修改日期文本 */
    private String    modifyTimeText;
    /** 修改IP */
    private String    modifyIP;
    /** 排序 */
    private Integer   dispOrder;
    /** 学段名称 */
    private String    sectionName;
    /** 年级名称 */
    private String    gradeName;

    /**
     * 学科VO实体类
     */
    public ShareCodeSubjectVo() {
    }

    /**
     * 学科VO实体类
     * 
     * @param shareCodeSubject 学科
     */
    public ShareCodeSubjectVo(ShareCodeSubject shareCodeSubject) {
        if (shareCodeSubject != null) {
            this.code = shareCodeSubject.getCode();
            this.name = shareCodeSubject.getName();
            this.description = shareCodeSubject.getDescription();
            this.modifyTime = shareCodeSubject.getModifyTime();
            this.modifyIP = shareCodeSubject.getModifyIP();
            this.dispOrder = shareCodeSubject.getDispOrder();
        }
    }

    /**
     * 获取学科VO集合
     * 
     * @param list 学科集合
     * @return 集合
     */
    public List<ShareCodeSubjectVo> getVoList(List<ShareCodeSubject> list) {
        List<ShareCodeSubjectVo> voList = new ArrayList<ShareCodeSubjectVo>();
        if (list != null && list.size() > 0) {
            for (ShareCodeSubject shareCodeSubject : list) {
                voList.add(new ShareCodeSubjectVo(shareCodeSubject));
            }
        }
        return voList;
    }

    /**
     * 学科VO实体类
     * 
     * @param map 学科
     */
    public ShareCodeSubjectVo(Map<String, Object> map) {
        if (map != null) {
            this.code = MapUtils.getString(map, "code");
            this.name = MapUtils.getString(map, "name");
            this.modifyTime = DateUtils.getTimestamp(MapUtils.getString(map, "modifyTime"));
            this.modifyIP = MapUtils.getString(map, "modifyIP");
            this.dispOrder = MapUtils.getInteger(map, "dispOrder");
            this.description = MapUtils.getString(map, "description");
            this.sectionName = MapUtils.getString(map, "sectionName");
            this.gradeName = MapUtils.getString(map, "gradeName");
        }
    }

    public static ShareCodeSubjectVo mapToVo(Map<String, Object> map) {
        ShareCodeSubjectVo vo = new ShareCodeSubjectVo();
        if (MapUtils.isNotEmpty(map)) {
            vo.setCode(MapUtils.getString(map, "code"));
            vo.setName(MapUtils.getString(map, "name"));
        }
        return vo;
    }

    /**
     * 获取学科VO集合
     * 
     * @param list 学科集合
     * @return 集合
     */
    public List<ShareCodeSubjectVo> getMapToVoList(List<Map<String, Object>> mapList) {
        List<ShareCodeSubjectVo> voList = new ArrayList<ShareCodeSubjectVo>();
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                voList.add(new ShareCodeSubjectVo(map));
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

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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
