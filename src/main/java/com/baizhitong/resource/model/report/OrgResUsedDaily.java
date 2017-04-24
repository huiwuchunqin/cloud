package com.baizhitong.resource.model.report;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 日次资源使用统计 OrgResUsedDaily TODO
 * 
 * @author creator BZT 2016年7月18日 下午12:57:01
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "org_res_used_daily")
@Entity
public class OrgResUsedDaily implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 主键 */
    private Integer           id;
    /** 机构编码 */
    private String            orgCode;
    /** 机构名称 */
    private String            orgName;
    /** 基准日 */
    private Integer           baseDate;
    /** 学年学期代码 */
    private String            yearTermCode;
    /** 学年代码 */
    private String            studyYearCode;
    /** 学期代码 */
    private String            termCode;
    /** 基准月 */
    private Integer           baseMonth;
    /** 是否月基准 */
    private Integer           flagBaseMonth;
    /** 基准周 */
    private Integer           weekNum;
    /** 是否周基准 */
    private Integer           flagBaseWeek;
    /** 学段编码 */
    private String            sectionCode;
    /** 年级编码 */
    private String            gradeCode;
    /** 学科编码 */
    private String            subjectCode;
    /** 资源分类（一级） */
    private Integer           resTypeL1;
    /** 资源分类（二级） */
    private Integer           resTypeL2;
    /** 个人私有资源总数 */
    private Integer           shareLevelPrivate;
    /** 校内共享资源总数 */
    private Integer           shareLevelOrg;
    /** 区域共享资源总数 */
    private Integer           shareLevelArea;
    /** 浏览总数 */
    private Integer           browseCount;
    /** 点踩总数 */
    private Integer           badCount;
    /** 下载总数 */
    private Integer           downloadCount;
    /** 引用总数 */
    private Integer           referCount;
    /** 收藏总数 */
    private Integer           favoriteCount;
    /** 点赞总数 */
    private Integer           goodCount;
    /** 评论总数 */
    private Integer           commentCount;
    /** 更新方式 */
    private Integer           updateType;
    /** 更新者 */
    private String            modifier;
    /** 更新时间 */
    private Timestamp         modifyTime;
    /** 更新者IP */
    private String            modifierIP;
}
