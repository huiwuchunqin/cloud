package com.baizhitong.resource.model.vo.report;

public class KpExportVo {
    /** 学科名称 */
    private String  subjectName;
    /** 学段名称 */
    private String  sectionName;
    /** 年级名称 */
    private String  gradeName;
    /** 一级节点 */
    private String  node1;
    /** 二级节点 */
    private String  node2;
    /** 三级节点 */
    private String  node3;
    /** 四级节点 */
    private String  node4;
    /** 五级节点 */
    private String  node5;
    /** 视频数 */
    private Integer mediaCount;
    /** 文档数 */
    private Integer docCount;
    /** 测验数 */
    private Integer testCount;
    /** 题目数数 */
    private Integer questionCount;
    /** 总数数 */
    private Integer totalCount;
    /** 排序 */
    private Integer dispOrder;
    /** 章节编码 */
    private String  code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getNode1() {
        return node1;
    }

    public void setNode1(String node1) {
        this.node1 = node1;
    }

    public String getNode2() {
        return node2;
    }

    public void setNode2(String node2) {
        this.node2 = node2;
    }

    public String getNode3() {
        return node3;
    }

    public void setNode3(String node3) {
        this.node3 = node3;
    }

    public String getNode4() {
        return node4;
    }

    public void setNode4(String node4) {
        this.node4 = node4;
    }

    public String getNode5() {
        return node5;
    }

    public void setNode5(String node5) {
        this.node5 = node5;
    }

    public Integer getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(Integer mediaCount) {
        this.mediaCount = mediaCount;
    }

    public Integer getDocCount() {
        return docCount;
    }

    public void setDocCount(Integer docCount) {
        this.docCount = docCount;
    }

    public Integer getTestCount() {
        return testCount;
    }

    public void setTestCount(Integer testCount) {
        this.testCount = testCount;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
