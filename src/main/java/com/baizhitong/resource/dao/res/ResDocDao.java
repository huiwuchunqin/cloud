package com.baizhitong.resource.dao.res;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResDoc;

/**
 * 资源_文档数据接口
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
public interface ResDocDao {
    /**
     * 获取文档分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param tbcCode 教材章节编码
     * @param kpCode 知识点编码
     * @param docName 文档名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 文档分页集合
     * @throws Exception 异常
     */
    public Page<Map<String, Object>> selectDocPage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String kpCode, String docName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 获取文档信息
     * 
     * @param docId 文档ID
     * @return 信息
     * @throws Exception 异常
     */
    public Map<String, Object> selectDocInfo(Integer docId) throws Exception;

    /**
     * 获取相关文档信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param knowledgePointCode 知识点编码
     * @param textbookChapterCode 教材章节编码
     * @return 相关文档信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelevantDocInfo(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String knowledgePointCode, String textbookChapterCode) throws Exception;

    /**
     * 根据resID获取资源
     * 
     * @param resID 资源id
     * @return 返回资源对象
     */
    public ResDoc selectResDoc(Integer resID);

    /**
     * 根据resCode获取资源
     * 
     * @param resCode 资源编码
     * @return 返回资源对象
     */
    public ResDoc selectResDoc(String resCode);

    /**
     * 更新资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    public boolean updateDocInfo(ResDoc resDoc);

    /**
     * 保存资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    public boolean saveDocInfo(ResDoc resDoc);

    /*************************************************** 后台查询方法 ****************************************************/
    /**
     * 后台查询文档信息
     * 
     * @param param 查询参数
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     * @author gaow
     * @date:2015年12月16日 上午11:40:47
     */
    public Page<Map<String, Object>> getAllDocInfo(Map<String, Object> param, Integer pageNo, Integer pageSize);

    /**
     * 查询不正常的文档信息 ()<br>
     * 
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<Map<String, Object>> getUnUsualDocInfo(Map<String, Object> param, Integer pageNo, Integer pageSize);

    /**
     * 获取首页文档资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param docName 文档名称
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页文档资源集合信息
     * @exception 异常
     */
    public List<Map<String, Object>> selectDocResList(String sectionCode, String subjectCode, String docName,
                    Boolean isHotSort, Boolean isNewSort) throws Exception;

    /**
     * 根据crcCode获取文档
     * 
     * @param crcCode 文件唯一码
     * @param creator 上传人
     * @return
     */
    public ResDoc selectResDocByCrcCode(String crcCode, String creator);

    /**
     * 保存并返回id
     * 
     * @param doc
     * @return
     * @author gaow
     * @date:2015年12月18日 下午3:51:39
     */
    public Integer saveDocAndReturnId(ResDoc doc) throws Exception;

    /**
     * 根据资源id 查询文档信息
     * 
     * @param id 文档资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 下午7:11:52
     */
    public Map<String, Object> getResDocById(Integer id) throws Exception;

    /**
     * 查询可以推荐的文档资源信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午6:53:03
     */
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception;

    /**
     * 资源审核
     * 
     * @param resId 资源id
     * @param status 审核状态
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月23日 下午1:53:19
     */
    public int examine(Integer resId, String status) throws Exception;

    /**
     * 获取用户资源分页集合
     * 
     * @param userCode 用户编码
     * @param resStatus 资源状态
     * @param checkStatus 审核状态
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 用户资源分页集合
     * @exception 异常
     */
    public Page<Map<String, Object>> selectSpaceUserResPage(String userCode, Integer resStatus, Integer checkStatus,
                    String sort, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 获取相同的资源编码文件
     * 
     * @param resCode
     * @return
     * @author gaow
     * @date:2015年12月25日 下午1:37:41
     */
    public int updateSameCodeDoc(ResDoc doc);

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
                    String orgCode, Integer pageNo, Integer rows);

    /**
     * 查询所有未被推荐的资源 ()<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public Page<Map<String, Object>> getAllCanRecommendInfo(Map<String, Object> param) throws Exception;

    /**
     * 恢复文档 ()<br>
     * 
     * @param ids 文档id列表
     */
    public int recoveryDocList(String ids);

    /**
     * 删除文档 ()<br>
     * 
     * @param ids 文档id
     * @return
     */
    public int deleteDoc(String ids, String modifier, String modifierIP);

    /**
     * 物理删除文档 ()<br>
     * 
     * @param ids 文档ids
     * @return
     */
    public int physicalDeleteDoc(String ids);

    /**
     * 
     * (批量处理文档资源，彻底删除或者恢复)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip);
}
