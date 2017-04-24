package com.baizhitong.resource.common.core.service;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;

import java.util.Map;

/**
 * Created by bzt-00 on 2015/12/16
 * 
 * @author wgh 共用接口-资源 （提供一些资源相关接口）
 */
public interface ResourceService {

    /**
     * 查询资源大类
     * 
     * @return
     */
    public ResultCodeVo queryResType() throws Exception;

    /**
     * 查询资源子类
     * 
     * @param code 父类Code
     * @return
     * @throws Exception
     */
    public ResultCodeVo queryResTypeChilds(Integer code) throws Exception;

    /**
     * 判断文件是否已存在
     * 
     * @param crcCode 文件唯一码
     * @param ext 文件后缀
     * @return
     */
    public ResultCodeVo fileIsExist(String crcCode, String ext) throws Exception;

    /**
     * 保存文件
     * 
     * @param list 文件列表json
     * @param userInfoVo 用户信息
     * @param ip ip
     * @param hostByRequest
     * @return
     */
    public ResultCodeVo saveList(String list, UserInfoVo userInfoVo, String ip, String hostByRequest) throws Exception;

    /**
     * 转码回调处理
     * 
     * @param resType 资源类型
     * @param dataId 资源id
     * @param fileInfo 文件信息
     */
    public void transcodingUpdate(Integer resType, Integer dataId, Map fileInfo);

    /**
     * 提交转码
     * 
     * @param id 资源id
     * @param ResTypeL1 资源类型
     * @param referer
     *        {request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();}
     */
    public Map submitConvertByResId(Integer id, Integer ResTypeL1, String referer);
}
