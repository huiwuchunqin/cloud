package com.baizhitong.resource.model.vo.report;

public class ChapterExportVo {
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
    /** 课程数 */
    private Integer lessonCount;
    /** 翻转课堂数 */
    private Integer flipLessonCount;
    /** 自主课程数 */
    private Integer autonomousCount;
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

    public Integer getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(Integer lessonCount) {
        this.lessonCount = lessonCount;
    }

    public Integer getFlipLessonCount() {
        return flipLessonCount;
    }

    public void setFlipLessonCount(Integer flipLessonCount) {
        this.flipLessonCount = flipLessonCount;
    }

    public Integer getAutonomousCount() {
        return autonomousCount;
    }

    public void setAutonomousCount(Integer autonomousCount) {
        this.autonomousCount = autonomousCount;
    }
}
