package com.baizhitong.resource.model.vo.share;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.model.share.ShareKnowledgePoint;
import com.baizhitong.resource.model.share.ShareKnowledgePointSerial;
import com.baizhitong.resource.model.share.ShareTextbook;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.utils.DataUtils;
import com.baizhitong.utils.StringUtils;

public class ZtreeHelpVo {
    private String            code;        // 节点
    private String            pcode;       // 父节点
    private String            name;        // 节点名称
    private boolean           isParent;    // 是否父节点
    private boolean           open;        // 是否展开
    private List<ZtreeHelpVo> parentList;
    private ZtreeHelpVo       child;       // 子节点
    private String            textbookCode;
    private String            kpSerialCode;
    private ZtreeHelpVo       topParent;   // 顶级父节点
    private String            nodeType;    // 节点类型 knowledgeSerial 知识体系 textbook 教材

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

    public List<ZtreeHelpVo> getParentList() {
        return parentList;
    }

    public void setParentList(List<ZtreeHelpVo> parentList) {
        this.parentList = parentList;
    }

    public ZtreeHelpVo getChild() {
        return child;
    }

    public void setChild(ZtreeHelpVo child) {
        this.child = child;
    }

    public ZtreeHelpVo getTopParent() {
        return topParent;
    }

    public void setTopParent(ZtreeHelpVo topParent) {
        this.topParent = topParent;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getTextbookCode() {
        return textbookCode;
    }

    public void setTextbookCode(String textbookCode) {
        this.textbookCode = textbookCode;
    }

    public String getKpSerialCode() {
        return kpSerialCode;
    }

    public void setKpSerialCode(String kpSerialCode) {
        this.kpSerialCode = kpSerialCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * 知识点转vo
     * 
     * @param know 知识点
     * @param pcode 手动设置父节点
     * @return
     */
    public static ZtreeHelpVo knowToVO(ShareKnowledgePoint know, String pcode) {
        if (know == null)
            return null;
        ZtreeHelpVo vo = new ZtreeHelpVo();
        DataUtils.copySimpleObject(know, vo);
        if (StringUtils.isNotEmpty(pcode)) {
            vo.setPcode(pcode);
        }
        return vo;
    }

    /**
     * 
     * 章节转vo
     * 
     * @param chapter 章节
     * @param pcode 手动设置父节点
     * @return
     */
    public static ZtreeHelpVo chapterToVO(ShareTextbookChapter chapter, String pcode) {
        if (chapter == null)
            return null;
        ZtreeHelpVo vo = new ZtreeHelpVo();
        DataUtils.copySimpleObject(chapter, vo);
        if (StringUtils.isNotEmpty(pcode)) {
            vo.setPcode(pcode);
        }
        return vo;
    }

    // 教材转vo
    public static ZtreeHelpVo textbooktoVo(ShareTextbook textbook) {
        if (textbook == null)
            return null;
        ZtreeHelpVo vo = new ZtreeHelpVo();
        vo.setCode("top-" + textbook.getTbCode());
        vo.setName(textbook.getTbName());
        vo.setNodeType("textbook");
        return vo;
    }

    // 知识体系转vo
    public static ZtreeHelpVo serialToVO(ShareKnowledgePointSerial shareKnowledgePointSerial) {
        if (shareKnowledgePointSerial == null)
            return null;
        ZtreeHelpVo vo = new ZtreeHelpVo();
        vo.setCode("top-" + shareKnowledgePointSerial.getCode());
        vo.setName(shareKnowledgePointSerial.getName());
        vo.setNodeType("knowledgeSerial");
        return vo;
    }

    // 知识点列表转vo列表
    public static List<ZtreeHelpVo> textbookListToVO(List<ShareTextbook> list) {
        if (list == null || list.size() <= 0)
            return null;
        List<ZtreeHelpVo> voList = new ArrayList<ZtreeHelpVo>();
        for (ShareTextbook textbook : list) {
            voList.add(textbooktoVo(textbook));
        }
        return voList;
    }

    // 体系列表转vo列表
    public static List<ZtreeHelpVo> serialListToVO(List<ShareKnowledgePointSerial> list) {
        if (list == null || list.size() <= 0)
            return null;
        List<ZtreeHelpVo> voList = new ArrayList<ZtreeHelpVo>();
        for (ShareKnowledgePointSerial serial : list) {
            voList.add(serialToVO(serial));
        }
        return voList;
    }

    // 知识点转vo
    public static ZtreeHelpVo mapToVO(Map<String, Object> map) {
        if (map == null)
            return null;
        ZtreeHelpVo vo = new ZtreeHelpVo();
        vo.setCode(MapUtils.getString(map, "code"));
        vo.setPcode(MapUtils.getString(map, "pcode"));
        vo.setName(MapUtils.getString(map, "name"));
        vo.setTextbookCode(MapUtils.getString(map, "textbookCode"));
        vo.setKpSerialCode(MapUtils.getString(map, "kpSerialCode"));
        return vo;
    }

    // 知识点数组转vo数组
    public static List<ZtreeHelpVo> knowListToVOList(List<ShareKnowledgePoint> list, String pcode) {
        if (list == null || list.size() <= 0)
            return null;
        List<ZtreeHelpVo> voList = new ArrayList<ZtreeHelpVo>();
        for (ShareKnowledgePoint know : list) {
            voList.add(knowToVO(know, pcode));
        }
        return voList;
    }

    // 章节数组转vo数组
    public static List<ZtreeHelpVo> chapterListToVOList(List<ShareTextbookChapter> list, String pcode) {
        if (list == null || list.size() <= 0)
            return null;
        List<ZtreeHelpVo> voList = new ArrayList<ZtreeHelpVo>();
        for (ShareTextbookChapter chapter : list) {
            voList.add(chapterToVO(chapter, pcode));
        }
        return voList;
    }

    // map数组转vo数组
    public static List<ZtreeHelpVo> mapListToVOList(List<Map<String, Object>> list) {
        if (list == null || list.size() <= 0)
            return null;
        List<ZtreeHelpVo> voList = new ArrayList<ZtreeHelpVo>();
        for (Map<String, Object> know : list) {
            voList.add(mapToVO(know));
        }
        return voList;
    }
}
