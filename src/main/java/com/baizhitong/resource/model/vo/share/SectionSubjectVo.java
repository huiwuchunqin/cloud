package com.baizhitong.resource.model.vo.share;

/**
 * 学段学科vo
 * 
 * @author gaow
 * @date:2015年12月15日 上午10:34:21
 */
public class SectionSubjectVo {
    private String  subjectCode; // 学科编码
    private String  subjectName; // 学科名称
    private String  description; // 描述
    private Integer selected;    // 0没选中 1选中
    private Integer dispOrder;

    public SectionSubjectVo(ShareCodeSubjectVo subject) {
        if (subject != null) {
            this.subjectCode = subject.getCode();
            this.description = subject.getDescription();
            this.selected = 0;
            this.subjectName = subject.getName();
            this.dispOrder = subject.getDispOrder();
        }
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

}
