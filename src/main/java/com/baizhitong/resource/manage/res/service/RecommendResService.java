package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;

public interface RecommendResService {
    /**
     * 查询已被推荐的资源信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午2:24:25
     */
    public Page<Map<String, Object>> getRecommendInfo(Map<String, Object> param) throws Exception;

    /**
     * 取消资源推荐
     * 
     * @param resId 资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午4:48:16
     */
    public int deleteRecommendRes(String resId, Integer resType) throws Exception;

    /**
     * 查询未被推荐的资源信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午6:44:45
     */
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception;

    /**
     * 推荐资源
     * 
     * @param resId 文档资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月22日 上午9:57:39
     */
    public ResultCodeVo recommendRes(String resId, Integer resType) throws Exception;
}
