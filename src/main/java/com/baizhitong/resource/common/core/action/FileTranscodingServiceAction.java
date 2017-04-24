package com.baizhitong.resource.common.core.action;

import com.alibaba.fastjson.JSONObject;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.ResourceService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.utils.StringUtils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * Created by bzt-00 on 2015/12/15. 文件转码服务控制器(提交转码，检测文件唯一性，获取转码进度等)
 */
@Controller
@RequestMapping(value = "/common/transcoding")
public class FileTranscodingServiceAction extends BaseAction {

    private @Autowired ResourceService resourceService;

    /**
     * 是否已存在于服务器上
     * 
     * @param crcCode 文件唯一码
     * @param ext 后缀
     * @return
     */
    @RequestMapping(value = "/exist/{crcCode}/{ext}")
    public @ResponseBody ResultCodeVo exist(@PathVariable String crcCode, @PathVariable String ext,
                    HttpServletRequest request) {
        ResultCodeVo result = new ResultCodeVo(true, "", "  ");

        if (StringUtils.isEmpty(crcCode) || StringUtils.isEmpty(ext)) {
            result.setMsg("参数不完整");
            return result;
        }

        try {
            result = resourceService.fileIsExist(crcCode, ext);
        } catch (Exception e) {
            logError(request, "秒传验证出错", e);
        }
        return result;
    }

    /**
     * 检测文件是否已存在与资源库中
     * 
     * @param request
     * @param uniqueCode 文件crc校验码
     * @return
     */
    @RequestMapping(value = "/checkFileExist")
    public @ResponseBody ResultCodeVo checkFileExist(HttpServletRequest request, String uniqueCode, String ext) {
        /*
         * LoginUser user=getLoginUserByRequest(request); if(user!=null){
         */
        ResultCodeVo result = new ResultCodeVo(true, "", "  ");

        if (StringUtils.isEmpty(uniqueCode) || StringUtils.isEmpty(ext)) {
            result.setMsg("参数不完整");
            return result;
        }
        try {
            return resourceService.fileIsExist(uniqueCode, ext);
        } catch (Exception e) {
            logError(request, "秒传验证出错", e);
        }
        return result;
        /*
         * }else{ return ResultCodeVo.errorCode("请登录。"); }
         */

    }

    /**
     * 更新数据
     * 
     * @param resType 资源类型
     * @param dataId resId
     * @param data 转码后文件信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/update")
    public @ResponseBody ResultCodeVo update(Integer resType, Integer dataId, String data, HttpServletRequest request) {
        Map fileInfo = JSONObject.parseObject(data);
        resourceService.transcodingUpdate(resType, dataId, fileInfo);
        if (!MapUtils.getString(fileInfo, "convertStatus").equals(CoreConstants.RESOURCE_STATE_CONVERT_FAIL)) {
            log.error(StringUtils.format("转码成功,回调信息:{0}", JSONObject.toJSONString(request.getParameterMap())));
        }
        return ResultCodeVo.rightCode("已处理转码回调...");
    }
}
