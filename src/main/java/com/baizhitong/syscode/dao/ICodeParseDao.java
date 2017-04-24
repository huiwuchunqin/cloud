package com.baizhitong.syscode.dao;

import java.util.Map;

import com.baizhitong.resource.common.vo.ListVo;

public interface ICodeParseDao {

    /**
     * 
     * (获取知识点编码)<br>
     * 
     * @param sectionCode
     * @param pcode
     */
    ListVo<Map<String, Object>> getKpCode(String subjectCode, String pcode);

    /**
     * 
     * (获取教材编码)<br>
     * 
     * @param textbookVerCode
     * @param gradeCode
     */
    ListVo<Map<String, Object>> getTextbookCode(String textbookVerCode, String gradeCode);

    /**
     * 
     * (获取教材章节编码)<br>
     * 
     * @param sectionCode
     * @param pcode
     */
    public ListVo<Map<String, Object>> getChapterCode(String tbCode, String pcode);

}
