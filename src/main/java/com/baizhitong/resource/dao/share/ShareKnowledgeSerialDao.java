package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.resource.model.share.ShareKnowledgePointSerial;

/**
 * 知识体系dao接口
 * 
 * @author creator gaow 2016年1月13日 下午3:50:56
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareKnowledgeSerialDao {
    /**
     * 
     * 保存知识点体系
     * 
     * @param serial
     * @throws Exception
     */
    boolean saveKnowledgeSerial(ShareKnowledgePointSerial serial) throws Exception;

    /**
     * 查询知识点体系
     * 
     * @param gid
     * @return
     * @throws Exception
     */
    ShareKnowledgePointSerial getKnowledgeSerial(String gid) throws Exception;

    /**
     * 
     * 查询知识体系
     * 
     * @param codes 体系编码
     * @return
     * @throws Exception
     */
    public List<ShareKnowledgePointSerial> getKnowledgeSerialList(List<String> codes) throws Exception;

    /**
     * 查询相同名称的知识体系 ()<br>
     * 
     * @param name 体系名称
     * @param subjectCode 学科名称
     * @param sectionCode 学段名称
     * @param gid 修改的主键 非修改则为null
     * @return
     * @throws Exception
     */
    public List<ShareKnowledgePointSerial> getSameNameKnowledgeSerialList(String name, String subjectCode,
                    String sectionCode, String gid) throws Exception;

    /**
     * 根据学科查知识体系 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    public List<ShareKnowledgePointSerial> getKnowledgePointSerialBySubjectCode(String subjectCode);
}
