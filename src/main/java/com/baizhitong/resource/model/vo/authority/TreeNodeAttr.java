package com.baizhitong.resource.model.vo.authority;

/**
 * 菜单树中的自定义属性
 * 
 * @author zhangqiang
 * @date 2015年12月18日 上午10:06:47
 */
public class TreeNodeAttr {
    private String  pid;       // 父节点id
    private String  menuCode;  // 菜单代码
    private Integer menuFlag;  // 是否为菜单对应的功能
    // private Integer rtid; //角色与菜单关系id
    private String  url;       // 链接地址
    private String  target;    // 链接目标
    private Integer displayNo; // 排序
    // private Integer removed; //是否已删

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getDisplayNo() {
        return displayNo;
    }

    public void setDisplayNo(Integer displayNo) {
        this.displayNo = displayNo;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Integer getMenuFlag() {
        return menuFlag;
    }

    public void setMenuFlag(Integer menuFlag) {
        this.menuFlag = menuFlag;
    }
}
