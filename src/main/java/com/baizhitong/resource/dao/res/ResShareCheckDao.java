package com.baizhitong.resource.dao.res;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.model.res.ResShareCheck;

/**
 * 资源共享审核接口 ResShareCheckDao TODO
 * 
 * @author creator gaow 2016年3月26日 上午11:26:30
 * @author updater
 *
 * @version 1.0.0
 */
public interface ResShareCheckDao {
    /**
     * 
     * 查询审核列表
     * 
     * @param param 查询参数
     * @return
     */
    Page selectPage(Map<String, Object> param);

    /**
     * 查询资源 ()<br>
     * 
     * @param resCode
     * @return
     */
    public List<ResShareCheck> getResByType(Integer resTypeL1);

    /**
     * 查询资源 ()<br>
     * 
     * @param resTypeL1
     * @param resCode
     * @return
     */
    public List<ResShareCheck> getResByCodeAndType(Integer resTypeL1, String resCode);

    /**
     * 
     * 更新资源审核状态
     * 
     * @param id 资源id
     * @param resTypeL1 资源一级分类
     * @param shareCheckStatus 审核状态
     * @param userInfoVo 用户信息
     * @param ip ip地址
     * @return
     */
    int updateShareCheckStatus(Integer id, Integer resTypeL1, Integer shareCheckStatus, UserInfoVo userInfoVo,
                    String ip, String checkComments);

    /**
     * 
     * 更新消息审核表
     * 
     * @param resCode 资源编码
     * @param resCheckTime 申请审核时间
     * @param shareCheckStatus 审核状态
     * @param userInfoVo 审核用户信息
     * @param ip ip地址
     * @param checkComments 审核意见
     * @return
     */
    int updateShareCheckStatusNew(String resCode, Timestamp resCheckTime, String shareCheckStatus,
                    UserInfoVo userInfoVo, String ip, String checkComments);

    /**
     * 
     * 保存资源分享审核资源
     * 
     * @param resShareCheck
     * @return
     */
    boolean save(ResShareCheck resShareCheck) throws Exception;

    /**
     * 查询资源 ()<br>
     * 
     * @param resCode
     * @return
     */
    public List<ResShareCheck> getRes(String resCode);

    /**
     * 删除资源 ()<br>
     * 
     * @param resCode
     * @return
     */
    public int delete(String resCode);

    /**
     * 查询资源 ()<br>
     * 
     * @param id
     * @return
     */
    ResShareCheck getRes(Integer id);

    /**
     * 
     * (特色资源审核)<br>
     * 
     * @param resCode 资源编码
     * @param resTypeL1 资源一级分类
     * @param shareCheckStatus 审核状态
     * @param userInfo 用户信息
     * @param ip ip地址
     * @param checkComments 审核意见
     * @return
     */
    int update(String resCode, Integer resTypeL1, Integer shareCheckStatus, UserInfoVo userInfo, String ip,
                    String checkComments);

    /**
     * 
     * (查询审核情况详细数据)<br>
     * 
     * @param param 查询参数
     * @return
     */
    Page<Map<String, Object>> selectByResCodeTypeL1(Map<String, Object> param);

    /**
     * 得到最近的审核信息 ()<br>
     * 
     * @param resCode
     * @param shareCheckStatus
     * @return
     */
    public Map<String, Object> getRecentlyInfo(String resCode, Integer shareCheckStatus);
    
    /**
     * 
     * (根据相关条件查询审核记录)<br>
     * @param resTypeL1 资源一级分类
     * @param resCode 资源编码
     * @param shareCheckLevel 共享审核级别
     * @return
     */
    List<ResShareCheck> selectResCheckList(Integer resTypeL1,String resCode,Integer shareCheckLevel);

}
