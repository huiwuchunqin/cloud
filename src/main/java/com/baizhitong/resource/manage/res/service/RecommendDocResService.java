package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;

/**
 * 文档推荐资源接口
 * 
 * @author zhangqiang
 * @date 2015年12月21日 下午2:17:25
 */
public interface RecommendDocResService {

    /**
     * 查询已被推荐的文档资源信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午2:24:25
     */
    public Page<Map<String, Object>> getRecommendDocInfo(Map<String, Object> param) throws Exception;

    /**
     * 取消文档资源推荐
     * 
     * @param resId 资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午4:48:16
     */
    public int deleteRecommendDocRes(String resId) throws Exception;

    /**
     * 查询未被推荐的文档资源信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午6:44:45
     */
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception;

    /**
     * 推荐文档资源
     * 
     * @param docResId 文档资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月22日 上午9:57:39
     */
    public ResultCodeVo recommendDocRes(String docResId) throws Exception;

}
