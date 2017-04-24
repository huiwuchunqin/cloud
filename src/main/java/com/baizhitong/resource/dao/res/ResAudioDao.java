package com.baizhitong.resource.dao.res;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResAudio;

public interface ResAudioDao {

    /**
         * 查询音频资源
         * ()<br>
         * @param id
         * @return
         */
    Map<String, Object> getAudioById(Integer id);
    /**
     * 根据资源编码查询音频资源
     * ()<br>
     * @param resCode
     */
    ResAudio getAudio(String resCode);
    /**
     * 查询音频资源
     * ()<br>
     * @param resCode
     * @return
     */
    public ResAudio select(Integer resId);
    /**
     * 更新音频资源
     * ()<br>
     * @param audio
     */
    void updateAudio(ResAudio audio);
    /**
     * 删除音频 ()<br>
     * 
     * @param ids 视频ids
     * @return
     */
     int deleteAudio(String ids, String modifier, String modifierIP);
    /**
        * 后台分页查询视频资源信息
        * 
        * @param param 查询参数
        * @param pageNo 页码
        * @param pageSize 每页记录数
        * @return
        * @author gaow
        * @date:2015年12月16日 上午11:39:53
        */
    Page<Map<String, Object>> getAllAudioInfo(Map<String, Object> param, Integer pageNo, Integer pageSize);

}