package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.resource.model.share.ShareTextbook;

/**
 * 
 * 教材dao接口
 * 
 * 
 * @author creator gaowei 2016年1月12日 上午10:00:33
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareTextbookDao {
    /**
     * 
     * 保存教材
     * 
     * @param textbook
     */
    boolean saveShareTextbook(ShareTextbook textbook) throws Exception;

    /**
     * 查询教材
     * 
     * @param gid 教材id
     * @return
     * @throws Exception
     */
    ShareTextbook getShareTextbook(String gid) throws Exception;

    /**
     * 查询教材
     * 
     * @param tbCode 教材编码
     * @return
     * @throws Exception
     */
    ShareTextbook getShareTextbookByTbCode(String tbCode) throws Exception;

    /**
     * 查询教材信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param textbookCode 教材编码
     * @return
     * @throws Exception
     */
    List<ShareTextbook> getTextbookList(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode, String termCode) throws Exception;

    /**
     * 查询相同名称的教材 ()<br>
     * 
     * @param name 教材名称
     * @param subjectCode 学科
     * @param gid 修改的主键 非修改则为null
     * @return
     */
    List<ShareTextbook> getSameName(String name, String subjectCode, String gid);
}
