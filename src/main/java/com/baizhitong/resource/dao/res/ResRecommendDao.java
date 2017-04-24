package com.baizhitong.resource.dao.res;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResRecommend;

/**
 * 资源_推荐数据接口
 * 
 * @author creator shancy 2015/12/16
 * @author updater
 */
public interface ResRecommendDao {
    /**
     * 获取首页推荐视频资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页推荐视频资源集合信息
     * @exception 异常
     */
    public List<Map<String, Object>> selectMediaResRecommendList(String sectionCode, String subjectCode,
                    Boolean isHotSort, Boolean isNewSort) throws Exception;

    /**
     * 获取首页推荐文档资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页推荐文档资源集合信息
     * @exception 异常
     */
    public List<Map<String, Object>> selectDocResRecommendList(String sectionCode, String subjectCode,
                    Boolean isHotSort, Boolean isNewSort) throws Exception;

    /**
     * 获取首页更多推荐视频资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param mediaName 视频名称
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页更多推荐视频资源集合
     * @exception 异常
     */
    public Page<Map<String, Object>> selectMediaResMoreRecommendPage(String sectionCode, String subjectCode,
                    String mediaName, Integer pageNo, Integer pageSize, Boolean isHotSort, Boolean isNewSort)
                                    throws Exception;

    /**
     * 获取首页更多推荐文档资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param docName 文档名称
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页更多推荐文档资源集合
     * @exception 异常
     */
    public Page<Map<String, Object>> selectDocResMoreRecommendPage(String sectionCode, String subjectCode,
                    String docName, Integer pageNo, Integer pageSize, Boolean isHotSort, Boolean isNewSort)
                                    throws Exception;

    /**
     * 获取已被设为推荐的文档资源分页信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午2:38:39
     */
    public Page<Map<String, Object>> getRecommendDocInfo(Map<String, Object> param) throws Exception;

    /**
     * 取消资源推荐
     * 
     * @param resId 资源ID
     * @param resTypeL1 资源分类（一级）
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午5:05:57
     */
    public int deleteRecommendRes(String resId, Integer resTypeL1) throws Exception;

    /**
     * 根据资源一级分类得到所有的被推荐的资源id的Map集合
     * 
     * @param resTypeL1 资源分类（一级）
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午7:31:43
     */
    public List<Map<String, Object>> getResIdMapList(Integer resTypeL1) throws Exception;

    /**
     * 根据资源ID和资源一级分类查询资源推荐信息
     * 
     * @param resId 资源ID
     * @param resTypeL1 资源一级分类
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月22日 上午10:05:18
     */
    public ResRecommend getInfoByResIdAndResTypeL1(String resId, Integer resTypeL1) throws Exception;

    /**
     * 添加资源推荐
     * 
     * @param entity 实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月22日 上午11:01:18
     */
    public boolean addResRecommend(ResRecommend entity) throws Exception;

    /**
     * 获取已被设为推荐的音视频资源分页信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午2:38:39
     */
    public Page<Map<String, Object>> getRecommendMediaInfo(Map<String, Object> param) throws Exception;

    /**
     * 查询资源推荐信息 ()<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public Page getRecommendResInfo(Map<String, Object> param) throws Exception;
}
