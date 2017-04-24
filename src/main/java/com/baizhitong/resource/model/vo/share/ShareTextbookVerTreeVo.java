package com.baizhitong.resource.model.vo.share;

public class ShareTextbookVerTreeVo {
    private String  textbookverName;
    private String  sectionName;
    private String  subjectName;
    private String  description;
    private String  modifyTime;
    private String  _parentId;
    private String  id;
    private String  gid;
    private boolean hasTextbook;
    private boolean hasKps;

    public String getTextbookverName() {
        return textbookverName;
    }

    public void setTextbookverName(String textbookverName) {
        this.textbookverName = textbookverName;
    }

    public boolean isHasKps() {
        return hasKps;
    }

    public void setHasKps(boolean hasKps) {
        this.hasKps = hasKps;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public boolean isHasTextbook() {
        return hasTextbook;
    }

    public void setHasTextbook(boolean hasTextbook) {
        this.hasTextbook = hasTextbook;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String get_parentId() {
        return _parentId;
    }

    public void set_parentId(String _parentId) {
        this._parentId = _parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
