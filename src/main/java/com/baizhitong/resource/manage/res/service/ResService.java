package com.baizhitong.resource.manage.res.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.res.ResDoc;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.resource.model.vo.res.DocumentReaderPlayListVO;
import com.baizhitong.resource.model.vo.res.ResVo;

/**
 * 资源业务接口
 * 
 * @author gaow
 * @date:2015年12月21日 上午9:54:12
 */
public interface ResService {
    /**
     * 查询文档资源信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月16日 下午1:34:16
     */
    public Page<ResVo> getDocPageInfo(Map<String, Object> param, Integer pageSize, Integer pageNo) throws Exception;

    /**
     * 查询视频资源信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月16日 下午1:34:16
     */
    public Page<ResVo> getMediaPageInfo(Map<String, Object> param, Integer pageSize, Integer pageNo) throws Exception;

    /**
     * 根据文档id查询文档
     * 
     * @param id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午7:13:35
     */
    public ResVo getDocAllInfoById(Integer id) throws Exception;

    /**
     * 根据视频id查询视频
     * 
     * @param id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午7:13:35
     */
    public ResVo getMediaAllInfoById(Integer id) throws Exception;

    /**
     * 保存文档资源
     * 
     * @param vo
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月18日 下午3:48:14
     */
    public Integer addDco(ResDoc vo) throws Exception;

    /**
     * 保存视频资源
     * 
     * @param vo
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月18日 下午3:48:14
     */
    public Integer addMedia(ResMedia vo) throws Exception;

    /**
     * 
     * @param docList 文档资源列表
     * @param userCode 用户编码
     * @param userName 用户姓名
     * @param ip ip地址
     * @param kpCodes 知识点编码集合
     * @param tbcCodes 章节编码集合
     * @param tbvCodes 教材版本编码集合
     * @param gradeCodes 年级编码集合
     * @param subejctCodes 学科编码集合
     * @return
     * @author gaow
     * @date:2015年12月18日 下午4:03:16
     */
    public ResultCodeVo docInfoUpdate(Integer resId, String resDesc, String resName, String resTypeL2, String userCode,
                    String userName, String ip, String kpCodes, String tbcCodes, String tbvCodes, String gradeCodes,
                    String subejctCodes, String sectionCodes, String uploader, String makerOrgCode, String makerOrgName,
                    String makerCode, Integer flagAllowDownload, String tags) throws Exception;

    /**
     * 
     * @param mediaList 视频资源列表
     * @param userCode 用户编码
     * @param userName 用户姓名
     * @param ip ip地址
     * @param kpCodes 知识点编码集合
     * @param tbcCodes 章节编码集合
     * @param tbvCodes 教材版本编码集合
     * @param gradeCodes 年级编码集合
     * @param subejctCodes 学科编码集合
     * @return
     * @author gaow
     * @date:2015年12月18日 下午4:03:16
     */
    public ResultCodeVo mediaInfoUpdate(Integer resId, String resDesc, String resName, String resTypeL2,
                    String userCode, String userName, String ip, String kpCodes, String tbcCodes, String tbvCodes,
                    String gradeCodes, String subejctCodes, String sectionCodes, String uploader, String makerOrgCode,
                    String makerOrgName, String makerCode, Integer flagAllowDownload, String coverPath, String tags)
                                    throws Exception;

    /**
     * 根据id查询文档资源
     * 
     * @param id 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午2:46:02
     */
    public ResVo getDocResById(Integer id) throws Exception;

    /**
     * 根据id查询视频资源
     * 
     * @param id 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午2:46:02
     */
    public ResVo getMediaResById(Integer id) throws Exception;

    /**
     * 审核资源
     * 
     * @param resId 资源id
     * @param type 类型
     * @param status 审核状态
     * @throws Exception
     * @author gaow
     * @date:2015年12月23日 下午1:57:36
     */
    public ResultCodeVo examine(Integer resId, Integer type, String status) throws Exception;

    /**
     * 查询页码
     * 
     * @param pageNo 当前页面
     * @param pageSize 总页数
     * @param resId 资源Id
     * @return
     * @author gaow
     * @date:2015年12月23日 下午3:55:00
     */
    public DocumentReaderPlayListVO getPageListJsonForAnalyitcal(Integer pageNo, Integer pageSize, Integer resId)
                    throws Exception;

    /**
     * 
     * 保存视频 知识点关系
     * 
     * @param resId 资源id
     * @param kpCodes 知识点编码
     * @return
     */
    public ResultCodeVo saveMediaKnowledgeRelated(Integer resId, String kpCodes);

    /**
     * 
     * 保存文档 知识点关系
     * 
     * @param resId 资源id
     * @param kpCodes 知识点编码
     * @return
     */
    public ResultCodeVo saveDocKnowledgeRelated(Integer resId, String kpCodes);

    /**
     * 
     * 保存视频章节关系
     * 
     * @param resId 资源id
     * @param chapterCodes 章节编码
     * @return
     */
    public ResultCodeVo saveMediaChapterRelated(Integer resId, String chapterCodes);

    /**
     * 
     * 保存文档章节关系
     * 
     * @param resId 资源id
     * @param chapterCodes 章节编码
     * @return
     */
    public ResultCodeVo saveDocChapterRelated(Integer resId, String chapterCodes);

    /**
     * 删除资源章节关系 ()<br>
     * 
     * @param resId
     * @param chapterCode
     * @throws Exception
     */
    public void delResChapter(Integer resId, String chapterCode) throws Exception;

    /**
     * 
     * 删除资源知识点关系
     * 
     * @param resId
     * @param knowledge
     * @throws Exception
     */
    public void delResKnowledge(Integer resId, String knowledge) throws Exception;

    /**
     * 
     * 查询视频文档资源
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param shareCheckStatus 分享审核中级别先
     * @param orgName 机构名称
     * @param userName 上传人姓名
     * @param resTypeL2 资源类别
     * @param uploadStartTime 上传时间开始
     * @param uploadEndTime 上传时间结束
     * @param shareCheckLevel 分享审核中状态
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    public Page<List<Map<String, Object>>> getAllRes(String sectionCode, String subjectCode, String gradeCode,
                    Integer shareCheckStatus, String orgName, String userName, Integer resTypeL1,
                    String uploadStartTime, String uploadEndTime, Integer shareCheckLevel, String resName,
                    Integer pageNo, Integer rows);

    /**
     * 查询不正常视频资源信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月16日 下午1:34:16
     */
    public Page<ResVo> getUnUsualMediaPageInfo(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception;

    /**
     * 
     * (查询资源回收站资源)<br>
     * 
     * @param param
     * @param pageSize
     * @param pageNo
     * @return
     * @throws Exception
     */
    public Page<ResVo> queryGarbageResPage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception;

    /**
     * 查询不正常视频资源信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @author gaow
     * @date:2015年12月16日 下午1:34:16
     */
    public Page<ResVo> getUnUsualDocPageInfo(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception;

    /**
     * 
     * (根据资源一级分类删除资源，支持批量删除)<br>
     * 
     * @param ids
     * @param resType
     * @return
     */
    public ResultCodeVo deleteRes(String ids, Integer resType);

    /**
     * 
     * (操作回收站资源)<br>
     * 
     * @param docIds 文档ID集合
     * @param mediaIds 视频ID集合
     * @param mediaSpecialIds 特色视频ID集合
     * @param lessonIds 课时ID集合
     * @param exerciseIds 测验ID集合
     * @param questionIds 题目ID集合
     * @param flashIds 互动资源ID集合
     * @param operateType 操作类型：1，删除；2，恢复；
     * @return 操作结果
     */
    public ResultCodeVo operateGarbageRes(String docIds, String mediaIds, String mediaSpecialIds, String lessonIds,
                    String exerciseIds, String questionIds, String flashIds, Integer operateType) throws Exception;

    /**
     * 恢复删除的资源
     * 
     * @param ids
     * @return
     */
    public ResultCodeVo recoveryRes(String docIds, String mediaIds);

    /**
     * 资源审核列表 param 查询参数
     * 
     * @return
     */
    public Page getResShareCheckList(Map<String, Object> param);

    /**
     * 
     * 资源审核
     * 
     * @param id 资源id
     * @param resTypeL1 资源一级分类
     * @param status 资源状态
     * @param shareCheckLevel 分享审核中级别
     * @return
     */
    public ResultCodeVo updateResShareCheck(String resCode, Integer id, Integer resTypeL1, Integer status,
                    Integer shareCheckLevel, String applierOrgCode, String applierCode, String checkComments);

    /**
     * 
     * 审核资源
     * 
     * @param resCodes 资源编码集合（可以一个或者多个）
     * @param checkCode 审核状态编码 05退回  20通过 
     * @param comment 审核意见
     * @param resType 资源类型 10视频；11特色资源；12音频资源；15互动资源；20文档； 30测验； 50题目；60课时
     * @return 审核结果
     */
    public ResultCodeVo checkRes(String resCodes[], String checkCode, String comment, String resType); 
    
}
