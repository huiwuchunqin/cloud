package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;

/**
 * 音视频推荐资源接口
 * 
 * @author zhangqiang
 * @date 2015年12月22日 下午1:34:58
 */
public interface RecommendMediaResService {

    /**
     * 查询已被推荐的音视频资源信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午2:24:25
     */
    public Page<Map<String, Object>> getRecommendMediaInfo(Map<String, Object> param) throws Exception;

    /**
     * 取消音视频资源推荐
     * 
     * @param resId 资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月21日 下午4:48:16
     */
    public int deleteRecommendMediaRes(String resId) throws Exception;

    /**
     * 查询未被推荐的音视频资源信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月22日 下午3:00:21
     */
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception;

    /**
     * 推荐音视频资源
     * 
     * @param resId 音视频资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月22日 下午3:00:52
     */
    public ResultCodeVo recommendMediaRes(String resId) throws Exception;
}
