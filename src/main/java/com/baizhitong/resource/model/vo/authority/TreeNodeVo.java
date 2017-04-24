package com.baizhitong.resource.model.vo.authority;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建树形菜单的类（用于页面展示）
 * 
 * @author zhangqiang
 * @date 2015年12月18日 上午10:06:16
 */
public class TreeNodeVo {

    private String           id;
    private String           text;
    private String           state;
    private String           iconCls;
    private boolean          checked;
    private TreeNodeAttr     attributes;
    private List<TreeNodeVo> children = new ArrayList<TreeNodeVo>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<TreeNodeVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeVo> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public TreeNodeAttr getAttributes() {
        return attributes;
    }

    public void setAttributes(TreeNodeAttr attributes) {
        this.attributes = attributes;
    }

}
