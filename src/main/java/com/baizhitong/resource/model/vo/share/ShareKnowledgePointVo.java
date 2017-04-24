package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.model.share.ShareKnowledgePoint;
import com.baizhitong.utils.DataUtils;
import com.baizhitong.utils.DateUtils;

/**
 * 教材知识点VO实体类
 * 
 * @author creator Cuidc 2015/12/10
 * @author updater
 */
public class ShareKnowledgePointVo {
    /** 主键 */
    private String    gid;
    /** 学科编码 */
    private String    subjectCode;
    /** 教材版本编码 */
    private String    sectionCode;
    /** 当前层数 */
    private Integer   level;
    /** 知识点编码 */
    private String    code;
    /** 知识点体系编码 */
    private String    kpSerialCode;
    /** 知识点父节点编码 */
    private String    pcode;
    /** 知识点名称 */
    private String    name;
    /** 知识点描述 */
    private String    description;
    /** 显示顺序 */
    private Integer   dispOrder;
    /** 修改日期 */
    private Timestamp modifyTime;
    /** 修改日期文本 */
    private String    modifyTimeText;
    /** 修改IP */
    private String    modifyIP;
    /** 是否是父节点 */
    private Boolean   isParent;
    /** 修改程序 */
    private String    modifyPgm;

    /**
     * 教材知识点VO实体类
     */
    public ShareKnowledgePointVo() {
    }

    /**
     * 教材知识点VO实体类
     * 
     * @param shareKnowledgePointTextbook 教材知识点
     */
    public ShareKnowledgePointVo(ShareKnowledgePoint shareKnowledgePoint) {
        if (shareKnowledgePoint != null) {
            this.subjectCode = shareKnowledgePoint.getSubjectCode();
            this.level = shareKnowledgePoint.getLevel();
            this.code = shareKnowledgePoint.getCode();
            this.pcode = shareKnowledgePoint.getPcode();
            this.name = shareKnowledgePoint.getName();
            this.description = shareKnowledgePoint.getDescription();
            this.dispOrder = shareKnowledgePoint.getDispOrder();
            this.modifyTime = shareKnowledgePoint.getModifyTime();
            this.modifyIP = shareKnowledgePoint.getModifyIP();
            this.gid = shareKnowledgePoint.getGid();
            this.sectionCode = shareKnowledgePoint.getSectionCode();
            this.kpSerialCode = shareKnowledgePoint.getKpSerialCode();
        }
    }

    /**
     * 
     * vo转实体
     * 
     * @param shareKnowledgePointVo
     * @return
     */
    public static ShareKnowledgePoint voToEntity(ShareKnowledgePointVo shareKnowledgePointVo) {
        ShareKnowledgePoint knowledgePoint = new ShareKnowledgePoint();
        DataUtils.copySimpleObject(shareKnowledgePointVo, knowledgePoint);
        return knowledgePoint;
    }

    /**
     * 获取教材知识点VO集合
     * 
     * @param list 教材知识点集合
     * @return 集合
     */
    public static List<ShareKnowledgePointVo> getVoList(List<ShareKnowledgePoint> list) {
        List<ShareKnowledgePointVo> voList = new ArrayList<ShareKnowledgePointVo>();
        if (list != null && list.size() > 0) {
            for (ShareKnowledgePoint shareKnowledgePoint : list) {
                voList.add(new ShareKnowledgePointVo(shareKnowledgePoint));
            }
        }
        return voList;
    }

    /**
     * 教材知识点VO实体类
     * 
     * @param map 教材知识点
     */
    public ShareKnowledgePointVo(Map<String, Object> map) {
        if (map != null) {
            this.subjectCode = MapUtils.getString(map, "subjectCode");
            this.sectionCode = MapUtils.getString(map, "sectionCode");
            this.level = MapUtils.getInteger(map, "level");
            this.gid = MapUtils.getString(map, "gid");
            this.code = MapUtils.getString(map, "code");
            this.pcode = MapUtils.getString(map, "pcode");
            this.name = MapUtils.getString(map, "name");
            this.description = MapUtils.getString(map, "description");
            this.dispOrder = MapUtils.getInteger(map, "dispOrder");
            this.modifyTime = DateUtils.getTimestamp(MapUtils.getString(map, "modifyTime"));
            this.modifyIP = MapUtils.getString(map, "modifyIP");
            this.kpSerialCode = MapUtils.getString(map, "kpSerialCode");
            this.gid = MapUtils.getString(map, "gid");
        }
    }

    /**
     * 获取教材知识点VO集合
     * 
     * @param list 教材知识点集合
     * @return 集合
     */
    public static List<ShareKnowledgePointVo> getMapToVoList(List<Map<String, Object>> mapList) {
        List<ShareKnowledgePointVo> voList = new ArrayList<ShareKnowledgePointVo>();
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                voList.add(new ShareKnowledgePointVo(map));
            }
        }
        return voList;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getKpSerialCode() {
        return kpSerialCode;
    }

    public void setKpSerialCode(String kpSerialCode) {
        this.kpSerialCode = kpSerialCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
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

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
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

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

}
