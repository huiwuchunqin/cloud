//package com.baizhitong.syscode.consts;
package com.baizhitong.syscode.consts;

/**
 * BizConstants 业务常量
 * 
 * @author creator xuaihua 2015年12月26日 下午5:44:05
 * @author updater
 *
 * @version 1.0.0
 */
public class SysCodeConstants {
    /**
     * 教材编码<br>
     * 教材版本编码7位+年级后2位,流水号1位（A）<br>
     * 传参(1)与年级无关textbookVerCode,000000000001<br>
     * （2）与年级有关textbookVerCode,000000000001,gradeCode,001
     */
    public static final String TEXT_BOOK_CODE             = "textbookCode";
    /**
     * 知识体系编码<br>
     * 学科编码+流水号<br>
     * subjectCode,000000000001
     */
    public static final String KPSERIAL_CODE              = "kpSerialCode";
    /**
     * 教材章节编码 <br>
     * 教材编码10位 + 5层3位共15位编码 传参textbookCode,0000000000000001,code,0000001
     */
    public static final String CHAPTER_CODE               = "chapterCode";
    /**
     * 知识点编码 <br>
     * 学科编码3位 + 备用编码7位 + 五层编码(每层三位表示)<br>
     * 传参(1)有父节点subjectCode,001,kpCode,001<br>
     * (2)无父节点subjectCode,001<br>
     */
    public static final String KP_CODE                    = "kpCode";
    /**
     * 机构编码 <br>
     * 所在平台代码4位 + 国标学校代码10位<br>
     * 传参(1)有国标学校代码，platformCode,0001,schoolCode,00001<br>
     * (2)无国标学校代码，platformCode,0001
     */
    public static final String ORG_CODE                   = "orgCode";
    /**
     * 用户代码 <br>
     * YH+平台编码+生成年月+流水号8位 传参platformCode,001,年月 201601
     */
    public static final String USER_CODE                  = "userCode";
    /**
     * 行政班级代码 <br>
     * 考虑班级编码组成 = 'BG'+机构编码14位 +学段+ 入学年份4位 + 流水号2位
     */
    public static final String ADMIN_CLASS_CODE           = "adminClassCode";

    /**
     * 行政小组代码 <br>
     * 班级小组编码组成 = 机构编码14位 + 入学年份4位 + 流水号 + 2位班内
     * 
     * 
     */
    public static final String ADMIN_GROUP_CODE           = "adminGroupCode";

    /**
     * 课程编码 <br>
     * KC+平台编码4位 + 课程流水号6位
     * 
     */
    public static final String COURSE_CODE                = "courseCode";

    /**
     * 课时编码 <br>
     * KS+平台编码4位 + 学科编码3位 + 年级编码最后2位 + 课时流水号5位<br>
     * 传参 "platformCode","0001","subjectCode","001","gradeCode","001"
     */
    public static final String LESSON_CODE                = "lessonCode";

    /**
     * 题目编码 <br>
     * 机构编码14位+学科8位+原子题型编码4位+流水号6位
     * 
     * 
     */
    public static final String QUESTION_CODE              = "questionCode";

    /**
     * 机构题型编码<br>
     * 学科编码3位 + 机构代码14位 + 题型编码4位 + 流水号6位<br>
     */
    public static final String ORG_QUESTIONTYPE_CODE      = "questionTypeSubjectOrg";

    /**
     * 平台商品编码 平台编码+时间(YYYYMMDD)+流水号8位
     */
    public static final String PLATFROM_GOODS_CODE        = "platformGoodsCode";

    /**
     * 平台题型编码 学科编码3位 + 平台代码4位 + 题型编码4位 + 流水号3位
     */
    public static final String PLATFORM_QUESTIONTYPE_CODE = "platformQuestionTypeCode";

    /**
     * 机构商品编码 机构编码+流水号6位
     */
    public static final String ORG_GOODS_CODE             = "orgGoodsCode";

}
