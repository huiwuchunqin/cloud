package com.baizhitong.resource.manage.textbook.service;

import java.util.List;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;
import com.baizhitong.resource.model.vo.share.ShareTextbookVo;

/**
 * 
 * 教材接口
 * 
 * @author creator gaow 2016年1月12日 下午5:11:56
 * @author updater
 *
 * @version 1.0.0
 */
public interface TextbookService {
    /**
     * 保存教材
     * 
     * @param textbook
     * @throws Exception
     */
    ResultCodeVo saveTextbook(ShareTextbookVo textbook) throws Exception;

    /**
     * 删除教材
     * 
     * @param gid
     * @return
     */
    ResultCodeVo delTextbook(String gid);

    /**
     * 
     * 查询教材
     * 
     * @param gid 教材id
     * @return
     * @throws Exception
     */
    ShareTextbookVo getShareTextbookVo(String gid) throws Exception;

    /**
     * 
     * 更新教材信息
     * 
     * @param name
     * @param memo
     * @param gid
     * @return
     * @throws Exception
     */
    ResultCodeVo updateTextbook(String name, String memo, String gid) throws Exception;

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
    public List<ShareTextbookVo> getTextbookList(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode, String termCode) throws Exception;
}
