package com.baizhitong.resource.model.vo.share;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.baizhitong.resource.model.share.ShareCodeTextbookVer;

public class VersionSimpleVo {
    public String code;
    public String name;
    public String description;

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

    public String getMemo() {
        return description;
    }

    public void setMemo(String description) {
        this.description = description;
    }

    public VersionSimpleVo(ShareCodeTextbookVer vo) {
        this.name = vo.getName();
        this.code = vo.getCode();
        this.description = vo.getDescription();
    }

    public VersionSimpleVo() {
    }

    public static List<VersionSimpleVo> getSimpleList(List<ShareCodeTextbookVer> voList) {
        if (voList == null || voList.size() < 0)
            return null;
        List<VersionSimpleVo> simpleList = new ArrayList<VersionSimpleVo>();
        for (ShareCodeTextbookVer vo : voList) {
            VersionSimpleVo simpleVo = new VersionSimpleVo(vo);
            simpleList.add(simpleVo);
        }
        return simpleList;
    }

    /**
     * 获取不重复的
     * 
     * @param voList
     * @return
     * @author gaow
     * @date:2015年12月17日 下午7:14:56
     */
    public static List<VersionSimpleVo> getDistinicSimpList(List<ShareCodeTextbookVer> voList) {
        List<VersionSimpleVo> simpleList = getSimpleList(voList);
        if (simpleList == null || simpleList.size() <= 0)
            return null;
        HashSet<VersionSimpleVo> unique = new HashSet<VersionSimpleVo>(simpleList);
        simpleList.clear();
        simpleList.addAll(unique);
        return simpleList;
    }
}
