package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.utils.DataUtils;
import com.baizhitong.utils.DateUtils;

/**
 * 教材章节树VO实体类
 * 
 * @author creator Cuidc 2015/12/10
 * @author updater
 */
public class ShareTextbookChapterVo {
    /** 系统ID */
    private String    gid;
    /** 所属教材编码 */
    private String    textbookCode;
    /** 章节编号 */
    private String    code;
    /** 章节名称 */
    private String    name;
    /** 当前层数 */
    private Integer   level;
    /** 父章节编号 */
    private String    pcode;
    /** 显示顺序 */
    private Integer   dispOrder;
    /** 描述 */
    private String    description;
    /** 修改IP */
    private String    modifyIP;
    /** 更新时间 */
    private Timestamp modifyTime;
    /** 修改日期文本 */
    private String    modifyTimeText;
    /** 是否是父节点 */
    private Boolean   isParent;
    /** 是否删除 */
    private Integer   flagDelete;

    /**
     * 教材章节树VO实体类
     */
    public ShareTextbookChapterVo() {

    }

    /**
     * 教材章节树VO实体类
     * 
     * @param shareTextbookChapter 教材章节树
     */
    public ShareTextbookChapterVo(ShareTextbookChapter shareTextbookChapter) {
        if (shareTextbookChapter != null) {
            this.textbookCode = shareTextbookChapter.getTextbookCode();
            this.code = shareTextbookChapter.getCode();
            this.name = shareTextbookChapter.getName();
            this.level = shareTextbookChapter.getLevel();
            this.pcode = shareTextbookChapter.getPcode();
            this.description = shareTextbookChapter.getDescription();
            this.dispOrder = shareTextbookChapter.getDispOrder();
            this.modifyTime = shareTextbookChapter.getModifyTime();
            this.modifyIP = shareTextbookChapter.getModifyIP();
        }
    }

    /**
     * 实体转Vo
     * 
     * @param shareTextbookChapter
     * @return
     */
    public static ShareTextbookChapterVo entityToVo(ShareTextbookChapter shareTextbookChapter) {
        ShareTextbookChapterVo vo = new ShareTextbookChapterVo();
        DataUtils.copySimpleObject(shareTextbookChapter, vo);
        return vo;
    }

    /**
     * 实体列表转vo列表 ()<br>
     * 
     * @param chapterList
     * @return
     */
    public static List<ShareTextbookChapterVo> entityListToVo(List<ShareTextbookChapter> chapterList) {
        if (chapterList == null || chapterList.size() <= 0)
            return null;
        List<ShareTextbookChapterVo> voList = new ArrayList<ShareTextbookChapterVo>();
        for (ShareTextbookChapter chapter : chapterList) {
            ShareTextbookChapterVo vo = entityToVo(chapter);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 获取教材章节树VO集合
     * 
     * @param list 教材章节树集合
     * @return 集合
     */
    public List<ShareTextbookChapterVo> getVoList(List<ShareTextbookChapter> list) {
        List<ShareTextbookChapterVo> voList = new ArrayList<ShareTextbookChapterVo>();
        if (list != null && list.size() > 0) {
            for (ShareTextbookChapter shareTextbookChapter : list) {
                voList.add(new ShareTextbookChapterVo(shareTextbookChapter));
            }
        }
        return voList;
    }

    /**
     * 教材章节树VO实体类
     * 
     * @param map 教材章节树
     */
    public ShareTextbookChapterVo(Map<String, Object> map) {
        if (map != null) {
            this.textbookCode = MapUtils.getString(map, "tbCode");
            this.code = MapUtils.getString(map, "code");
            this.gid = MapUtils.getString(map, "gid");
            this.name = MapUtils.getString(map, "name");
            this.level = MapUtils.getInteger(map, "level");
            this.pcode = MapUtils.getString(map, "pcode");
            this.description = MapUtils.getString(map, "description");
            this.dispOrder = MapUtils.getInteger(map, "dispOrder");
            this.modifyTime = DateUtils.getTimestamp(MapUtils.getString(map, "modifyTime"));
            this.modifyIP = MapUtils.getString(map, "modifyIP");
        }
    }

    /**
     * 获取教材章节树VO集合
     * 
     * @param list 教材章节树集合
     * @return 集合
     */
    public static List<ShareTextbookChapterVo> getMapToVoList(List<Map<String, Object>> mapList) {
        List<ShareTextbookChapterVo> voList = new ArrayList<ShareTextbookChapterVo>();
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                voList.add(new ShareTextbookChapterVo(map));
            }
        }
        return voList;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
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

    public String getModifyIP() {
        return modifyIP;
    }

    public void setModifyIP(String modifyIP) {
        this.modifyIP = modifyIP;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyTimeText() {
        return modifyTimeText;
    }

    public void setModifyTimeText(String modifyTimeText) {
        this.modifyTimeText = modifyTimeText;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public String getTextbookCode() {
        return textbookCode;
    }

    public void setTextbookCode(String textbookCode) {
        this.textbookCode = textbookCode;
    }

    public Integer getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Integer flagDelete) {
        this.flagDelete = flagDelete;
    }

}
