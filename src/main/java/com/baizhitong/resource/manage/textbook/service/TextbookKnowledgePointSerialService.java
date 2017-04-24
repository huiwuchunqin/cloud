package com.baizhitong.resource.manage.textbook.service;

import java.util.List;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareKnowledgePointSerial;
import com.baizhitong.resource.model.vo.share.ShareRelTextbookKpsVo;

/**
 * 
 * 教材知识体系关联接口
 * 
 * @author creator gaow 2016年1月13日 下午5:14:40
 * @author updater
 *
 * @version 1.0.0
 */
public interface TextbookKnowledgePointSerialService {
    /**
     * 保存教材知识体系关系 和知识体系 ()<br>
     * 
     * @param relTextbookKps
     * @param name 体系名称
     * @param memo 备注
     * @return
     */
    ResultCodeVo addTextbookKnowledgePointSerial(ShareRelTextbookKpsVo relTextbookKps, String name, String memo)
                    throws Exception;

    /**
     * 更新知识体系
     * 
     * @param name 体系名称
     * @param memo 备注
     * @param gid 主键
     * @return
     * @throws Exception
     */
    public ResultCodeVo updateKnowledgePointSerial(String name, String memo, String gid) throws Exception;

    /**
     * 查询知识体系
     * 
     * @param gid 主键
     * @return
     * @throws Exception
     */
    ShareKnowledgePointSerial getKnowledgeSerial(String gid) throws Exception;

    /**
     * 
     * 删除知识体系
     * 
     * @param gid
     * @throws Exception
     */
    ResultCodeVo delKnowledgeSerial(String gid) throws Exception;

    /**
     * 根据学科查询知识体系 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    List<ShareKnowledgePointSerial> getKnowledgePointSerialBySubjectCode(String subjectCode);
}
