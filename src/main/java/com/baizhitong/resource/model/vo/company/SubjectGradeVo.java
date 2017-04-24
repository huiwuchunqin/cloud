package com.baizhitong.resource.model.vo.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

public class SubjectGradeVo {
    /**
     * 年级编码
     */
    private String  gradeCode;
    /**
     * 年级名称
     */
    private String  gradeName;
    /**
     * 描述
     */
    private String  description;
    /**
     * 可编辑
     */
    private Integer editable;   // 0 不可编辑 1可编辑
    /**
     * 是否选择
     */
    private Integer selected;

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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

    public Integer getEditable() {
        return editable;
    }

    public void setEditable(Integer editable) {
        this.editable = editable;
    }

    /**
     * 年级map转vo ()<br>
     * 
     * @param map
     * @return
     */
    public static SubjectGradeVo mapToVo(Map<String, Object> map) {
        if (map == null)
            return null;
        SubjectGradeVo vo = new SubjectGradeVo();
        vo.setGradeCode(MapUtils.getString(map, "code"));
        vo.setGradeName(MapUtils.getString(map, "name"));
        vo.setSelected(0);
        vo.setEditable(0);
        return vo;
    }

    /**
     * mapList转volist ()<br>
     * 
     * @param mapList
     * @return
     */
    public static List<SubjectGradeVo> mapListToVoList(List<Map<String, Object>> mapList) {
        if (mapList == null || mapList.size() <= 0)
            return null;
        List<SubjectGradeVo> voList = new ArrayList<SubjectGradeVo>();
        for (Map<String, Object> map : mapList) {
            voList.add(mapToVo(map));
        }
        return voList;
    }

}
