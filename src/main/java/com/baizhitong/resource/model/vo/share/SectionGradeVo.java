package com.baizhitong.resource.model.vo.share;

/**
 * 学段年级vo
 * 
 * @author gaow
 * @date:2015年12月15日 上午10:32:20
 */
public class SectionGradeVo {
    private String  gradeCode;   // 年级编码
    private String  gradeName;   // 年级名称
    private String  description; // 介绍
    private Integer selected;    // 0没选中 1选中；
    private Integer dispOrder;

    public SectionGradeVo(ShareCodeGradeVo grade) {
        if (grade != null) {
            this.gradeCode = grade.getCode();
            this.description = grade.getDescription();
            this.selected = 0;
            this.gradeName = grade.getName();
            this.dispOrder = grade.getDispOrder();
        }
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
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
