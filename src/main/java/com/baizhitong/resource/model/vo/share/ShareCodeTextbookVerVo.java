package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.resource.model.share.ShareCodeTextbookVer;
import com.baizhitong.utils.DataUtils;
import com.baizhitong.utils.DateUtils;

/**
 * 教材版本VO实体类
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public class ShareCodeTextbookVerVo {
    /** 系统ID */
    private String    gid;
    /** 系统版本号 */
    private Integer   sysVer;
    /** 学科名称 */
    private String    subjectName;
    /** 学科编码 */
    private String    subjectCode;
    /** 编码 */
    private String    code;
    /** 名称 */
    private String    name;
    /** 描述说明 */
    private String    description;
    /** 修改日期 */
    private Timestamp modifyTime;
    /** 修改日期文本 */
    private String    modifyTimeText;
    /** 修改IP */
    private String    modifyIP;

    /**
     * 教材版本VO实体类
     */
    public ShareCodeTextbookVerVo() {
    }

    /**
     * 教材版本VO实体类
     * 
     * @param shareCodeTextbookVer 教材版本
     */
    public ShareCodeTextbookVerVo(ShareCodeTextbookVer shareCodeTextbookVer) {
        if (shareCodeTextbookVer != null) {
            this.subjectCode = shareCodeTextbookVer.getSubjectCode();
            this.code = shareCodeTextbookVer.getCode();
            this.name = shareCodeTextbookVer.getName();
            this.description = shareCodeTextbookVer.getDescription();
            this.modifyTime = shareCodeTextbookVer.getModifyTime();
            this.modifyIP = shareCodeTextbookVer.getModifyIP();
        }
    }

    /**
     * 获取教材版本VO集合
     * 
     * @param list 教材版本集合
     * @return 集合
     */
    public List<ShareCodeTextbookVerVo> getVoList(List<ShareCodeTextbookVer> list) {
        List<ShareCodeTextbookVerVo> voList = new ArrayList<ShareCodeTextbookVerVo>();
        if (list != null && list.size() > 0) {
            for (ShareCodeTextbookVer shareCodeTextbookVer : list) {
                voList.add(new ShareCodeTextbookVerVo(shareCodeTextbookVer));
            }
        }
        return voList;
    }

    /**
     * mapList转volist
     * 
     * @param mapList
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:40:31
     */
    public static List<ShareCodeTextbookVerVo> mapListToVoList(List<Map<String, Object>> mapList) {
        if (mapList == null || mapList.size() <= 0)
            return null;
        List<ShareCodeTextbookVerVo> voList = new ArrayList<ShareCodeTextbookVerVo>();
        for (Map map : mapList) {
            if (map == null)
                continue;
            ShareCodeTextbookVerVo vo = new ShareCodeTextbookVerVo();
            vo = JSON.parseObject(JSON.toJSONString(map), ShareCodeTextbookVerVo.class);
            voList.add(vo);
        }
        return voList;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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
