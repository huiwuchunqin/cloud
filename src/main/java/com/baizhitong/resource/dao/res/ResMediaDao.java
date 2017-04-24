package com.baizhitong.resource.dao.res;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResMedia;

/**
 * 资源_音视频媒体数据接口
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
public interface ResMediaDao {

    /**
     * 获取视频分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param tbcCode 教材章节编码
     * @param kpCode 知识点编码
     * @param mediaName 视频名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 视频分页集合
     */
    public Page<Map<String, Object>> selectMediaPage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String kpCode, String mediaName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize);

    /**
     * 获取视频信息
     * 
     * @param mediaId 视频ID
     * @return 信息
     */
    public Map<String, Object> selectMediaInfo(Integer mediaId);

    /**
     * 更新resmedia
     * 
     * @param resMedia 资源对象
     * @return 是否成功
     */
    public boolean updateMediaInfo(ResMedia resMedia);

    /**
     * 根据resID获取resMedia
     * 
     * @param resID 资源id
     * @return resMedia对象
     */
    public ResMedia selectResMedia(Integer resID);

    /**
     * 根据resCode获取resMedia
     * 
     * @param resCode 资源编码
     * @return resMedia对象
     */
    public ResMedia selectResMedia(String resCode);

    /**
     * 保存资源信息
     * 
     * @param resMedia 资源对象
     * @return 是否成功
     */
    public boolean saveMediaInfo(ResMedia resMedia);

    /**
     * 保存资源信息并返回Map
     * 
     * @param resMedia 资源对象
     * @return 是否成功
     */
    public Integer saveMediaAndReturnId(ResMedia resMedia) throws Exception;

    /**
     * 获取相关视频信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param knowledgePointCode 知识点编码
     * @param textbookChapterCode 教材章节编码
     * @return 相关视频信息
     */
    public List<Map<String, Object>> selectRelevantMediaInfo(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String knowledgePointCode, String textbookChapterCode);

    /**
     * 获取首页视频资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param mediaName 视频名称
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页视频资源集合信息
     * @exception 异常
     */
    public List<Map<String, Object>> selectMediaResList(String sectionCode, String subjectCode, String mediaName,
                    Boolean isHotSort, Boolean isNewSort) throws Exception;

    /**
     * 根据crcCode获取视频
     * 
     * @param crcCode 文件唯一码
     * @return
     */
    public ResMedia selectResMediaByCrcCode(String crcCode, String creator);

    /**
     * 查询所有视频资源信息
     * 
     * @param param
     * @param pageSize
     * @param pageNo
     * @return
     * @author gaow
     * @date:2015年12月21日 上午9:19:43
     */
    public Page<Map<String, Object>> getAllMediaInfo(Map<String, Object> param, Integer pageNo, Integer pageSize);

    /**
     * 查询不正常数据 ()<br>
     * 
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<Map<String, Object>> getUnUsualMediaInfo(Map<String, Object> param, Integer pageNo, Integer pageSize);

    /**
     * 查询删除的数据 ()<br>
     * 
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<Map<String, Object>> getGarbageMediaInfo(Map<String, Object> param, Integer pageNo, Integer pageSize);

    /**
     * 根据资源id 查询视频信息
     * 
     * @param id 视频资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 下午7:11:52
     */
    public Map<String, Object> getResMediaById(Integer id) throws Exception;

    /**
     * 查询可以推荐的音视频资源信息
     * 
     * @param param
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月22日 下午3:15:28
     */
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception;

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
     * 获取相同的资源编码文件
     * 
     * @param resCode
     * @return
     * @author gaow
     * @date:2015年12月25日 下午1:37:41
     */
    public int updateSameResCodeMedia(ResMedia media);

    /**
     * 
     * (删除视频资源)<br>
     * 
     * @param ids
     * @param modifier
     * @param modifierIP
     * @return
     */
    public int deleteMedia(String ids, String modifier, String modifierIP);

    /**
     * 物理删除视频 ()<br>
     * 
     * @param ids 视频ids
     * @return
     */
    public int physicalDeleteMedia(String ids);

    /**
     * 恢复视频 ()<br>
     * 
     * @param ids 视频id列表
     */
    public int recoveryMediaList(String ids);

    /**
     * 
     * (分页查询可以设置在首页的视频资源)<br>
     * 
     * @param param 查询参数
     * @param rows 每页大小
     * @param page 当前页
     * @return
     */
    public Page<Map<String, Object>> selectPageByResSetting(Map<String, Object> param, Integer rows, Integer page);

    /**
     * 
     * (批量处理视频资源，彻底删除或者恢复)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip);
}
