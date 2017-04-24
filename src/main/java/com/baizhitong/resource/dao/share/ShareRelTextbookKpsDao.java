package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.resource.model.share.ShareRelTextbookKps;

public interface ShareRelTextbookKpsDao {

    /**
     * 查询某学科当前知识点体系体系编码最大的数据
     * 
     * @param subjectCode 学科
     * @return 学科知识点体系数量
     */
    public ShareRelTextbookKps getSubjectKps(String subjectCode);

    /**
     * 保存知识点教材关系信息
     * 
     * @param knowledge
     * 
     * @exception
     */
    public void saveRelTextBookKps(ShareRelTextbookKps relTextBookKps) throws Exception;

    /**
     *
     * 查询知识体系关系
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param textbookCode 教材编码
     * @param termCode 学期编码
     * @return
     * @throws Exception
     */
    List<ShareRelTextbookKps> getRelTextBookKps(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode, String termCode) throws Exception;

    /**
     * 
     * 查询知识体系关系
     * 
     * @param kpSerialCode 体系编码
     * @return
     * @throws Exception
     */
    List<ShareRelTextbookKps> getRelTextBookKps(String kpSerialCode) throws Exception;

    /**
     * 删除关系 @param relList
     * 
     * @exception
     */
    public void delRelTextBookKps(List<ShareRelTextbookKps> relList) throws Exception;
}
