package com.baizhitong.resource.model.vo.base;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.baizhitong.resource.model.share.ShareKnowledgePointSerial;
import com.baizhitong.resource.model.vo.share.ShareKnowledgePointVo;
import com.baizhitong.resource.model.vo.share.ShareTextbookChapterVo;
import com.baizhitong.resource.model.vo.share.ShareTextbookVo;

/**
 * 教材章节树VO
 * 
 * @author zhangqiang
 * @date 2015年12月15日 下午1:40:52
 */

public class NodeTreeVo {

    /** 主键 */
    private String          gid;
    /** 节点名称 */
    private String          text;
    /** 节点状态(open、closed) */
    private String          state;
    /** 用来显示图标的 */
    private String          iconCls;
    /** 节点描述 */
    private String          description;
    /** 节点自定义属性 */
    public String           attributes;
    /** 目标DOM对象 */
    public String           target;
    /** 有没有子节点 */
    public boolean          isParent;
    /** 当前层数 */
    private Integer         level;
    /** 父节点编码 */
    private String          pcode;
    /** 节点编码 */
    private String          code;
    /** 教材知识点关系 */
    private String          kpSerialCode;
    /** 教材编码 */
    private String          tbCode;
    /** 节点类型 */
    private String          nodeType;       // textbook 教材 chapter章节 knowledgeSerial知识点
    /** 学段名称 */
    private String          sectionName;
    /** 学科名称 */
    private String          subjectName;
    /** 年级名称 */
    private String          gradeName;
    /** 教材版本名称 */
    private String          textbookVerName;
    /** 学段编码 */
    private String          sectionCode;
    /** 学科名称 */
    private String          subjectCode;
    private String          gradeCode;
    private String          textbookVerCode;
    /** 子菜单集合 */
    public List<NodeTreeVo> children;

    /**
     * 
     * 教材转vo
     * 
     * @param textBook
     * @return
     */
    public static NodeTreeVo textBookVoToNode(ShareTextbookVo textBook) {
        NodeTreeVo nodeTreeVo = new NodeTreeVo();
        nodeTreeVo.setGid(textBook.getGid());
        nodeTreeVo.setText(textBook.getTbName());
        nodeTreeVo.setCode(textBook.getTbCode());
        nodeTreeVo.setPcode(null);
        nodeTreeVo.setDescription(textBook.getMemo());
        nodeTreeVo.setNodeType("textbook");
        nodeTreeVo.setState("closed");
        nodeTreeVo.setGradeCode(textBook.getGradeCode());
        nodeTreeVo.setTextbookVerCode(textBook.getTextbookVerCode());
        nodeTreeVo.setSectionCode(textBook.getSectionCode());
        nodeTreeVo.setSubjectCode(textBook.getSubjectCode());
        return nodeTreeVo;
    }

    /**
     * 教材list转nodeTreeVo数组 ()<br>
     * 
     * @param textbookList
     * @return
     */
    public static List<NodeTreeVo> textbookListToNodeTreeList(List<ShareTextbookVo> textbookList) {
        List<NodeTreeVo> nodeTreeList = new ArrayList<NodeTreeVo>();
        if (textbookList != null && textbookList.size() > 0) {
            for (ShareTextbookVo textbookVo : textbookList) {
                nodeTreeList.add(textBookVoToNode(textbookVo));
            }
        }
        return nodeTreeList;
    }

    /**
     * 
     * 知识体系转vo
     * 
     * @param textBook
     * @return
     */
    public static NodeTreeVo shareKnowledgePointSerialToNode(ShareKnowledgePointSerial shareKnowledgePointSerial) {
        NodeTreeVo nodeTreeVo = new NodeTreeVo();
        nodeTreeVo.setGid(shareKnowledgePointSerial.getGid());
        nodeTreeVo.setPcode(null);
        nodeTreeVo.setText(shareKnowledgePointSerial.getName());
        nodeTreeVo.setCode(shareKnowledgePointSerial.getCode());
        nodeTreeVo.setKpSerialCode(shareKnowledgePointSerial.getCode());
        nodeTreeVo.setDescription(shareKnowledgePointSerial.getMemo());
        nodeTreeVo.setNodeType("knowledgeSerial");
        nodeTreeVo.setState("closed");
        nodeTreeVo.setSectionCode(shareKnowledgePointSerial.getSectionCode());
        nodeTreeVo.setSubjectCode(shareKnowledgePointSerial.getSubjectCode());
        return nodeTreeVo;
    }

    /**
     * 知识体系list转nodeTreeVo数组 ()<br>
     * 
     * @param textbookList
     * @return
     */
    public static List<NodeTreeVo> serialListToNodeTreeList(List<ShareKnowledgePointSerial> serialList) {
        List<NodeTreeVo> nodeTreeList = new ArrayList<NodeTreeVo>();
        if (serialList != null && serialList.size() > 0) {
            for (ShareKnowledgePointSerial shareKnowledgePointSerial : serialList) {
                nodeTreeList.add(shareKnowledgePointSerialToNode(shareKnowledgePointSerial));
            }
        }
        return nodeTreeList;
    }

    /**
     * 获取教材章节树型结构数据
     * 
     * @param nodeList 教材章节集合
     * @param pcode 父章节编号(根节点为空)
     * @return
     * @throws Exception
     */
    public static List<NodeTreeVo> getNodeTree(List<ShareTextbookChapterVo> nodeList, String pcode) throws Exception {
        List<NodeTreeVo> nodeTree = new ArrayList<NodeTreeVo>();
        if (nodeList != null && nodeList.size() > 0) {
            // 获取该父节点下的所有子节点
            List<ShareTextbookChapterVo> subMenu = new ArrayList<ShareTextbookChapterVo>();
            for (ShareTextbookChapterVo node : nodeList) {
                // if
                // ((!StringUtils.isEmpty(pcode)&&node.getPcode().equals(pcode))||pcode.equals(""))
                // {
                // subMenu.add(node);
                // }
                if (StringUtils.isEmpty(pcode) && StringUtils.isEmpty(node.getPcode())) {
                    subMenu.add(node);
                } else if ((!StringUtils.isEmpty(pcode) && pcode.equals(node.getPcode()))) {
                    subMenu.add(node);
                }
            }

            // 获取该子节点下的子节点
            if (subMenu != null && subMenu.size() > 0) {
                for (ShareTextbookChapterVo node : subMenu) {
                    NodeTreeVo menu = new NodeTreeVo();
                    menu.setGid(node.getGid());
                    menu.setText(node.getName());
                    menu.setCode(node.getCode());
                    menu.setPcode(node.getPcode());
                    menu.setLevel(node.getLevel());
                    menu.setDescription(node.getDescription());
                    menu.setChildren(getNodeTree(nodeList, node.getCode()));
                    menu.setNodeType("chapter");
                    menu.setTbCode(node.getTextbookCode());
                    menu.setState("closed");

                    nodeTree.add(menu);
                }
            }
        }
        return nodeTree;
    }

    /**
     * 获取教材知识点树型结构数据
     * 
     * @param nodeList 教材知识点集合
     * @param pcode 父知识点编号(根节点为空)
     * @return
     * @throws Exception
     */
    public static List<NodeTreeVo> getKnowledgeTree(List<ShareKnowledgePointVo> nodeList, String pcode)
                    throws Exception {
        List<NodeTreeVo> nodeTree = new ArrayList<NodeTreeVo>();
        if (nodeList != null && nodeList.size() > 0) {
            // 获取该父节点下的所有子节点
            List<ShareKnowledgePointVo> subMenu = new ArrayList<ShareKnowledgePointVo>();
            for (ShareKnowledgePointVo node : nodeList) {
                if (StringUtils.isEmpty(pcode) && StringUtils.isEmpty(node.getPcode())) {
                    subMenu.add(node);
                } else if ((!StringUtils.isEmpty(pcode) && pcode.equals(node.getPcode()))) {
                    subMenu.add(node);
                }
            }

            // 获取该子节点下的子节点
            if (subMenu != null && subMenu.size() > 0) {
                for (ShareKnowledgePointVo node : subMenu) {
                    NodeTreeVo menu = new NodeTreeVo();
                    menu.setGid(node.getGid());
                    menu.setText(node.getName());
                    menu.setDescription(node.getDescription());
                    menu.setChildren(getKnowledgeTree(nodeList, node.getCode()));
                    menu.setCode(node.getCode());
                    menu.setPcode(node.getPcode());
                    menu.setLevel(node.getLevel());
                    menu.setKpSerialCode(node.getKpSerialCode());
                    menu.setNodeType("knowledge");
                    menu.setState("closed");
                    nodeTree.add(menu);
                }
            }
        }
        return nodeTree;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<NodeTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<NodeTreeVo> children) {
        this.children = children;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getTextbookVerName() {
        return textbookVerName;
    }

    public void setTextbookVerName(String textbookVerName) {
        this.textbookVerName = textbookVerName;
    }

    public String getKpSerialCode() {
        return kpSerialCode;
    }

    public void setKpSerialCode(String kpSerialCode) {
        this.kpSerialCode = kpSerialCode;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getTextbookVerCode() {
        return textbookVerCode;
    }

    public void setTextbookVerCode(String textbookVerCode) {
        this.textbookVerCode = textbookVerCode;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}
