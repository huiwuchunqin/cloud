package com.baizhitong.resource.common.core.action;

import com.baizhitong.resource.common.constants.CodeConstants;
import com.baizhitong.resource.common.core.service.ResourceService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.FtpHelper;
import com.baizhitong.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bzt-00 on 2015/12/16.
 * 
 * @author wgh 资源上传共用控制器（提供一些共用接口）
 */
@Controller
@RequestMapping("/common/upload")
public class ResourceServiceAction extends BaseAction {

    /** 资源业务接口 */
    private @Autowired ResourceService resourceService;

    /**
     * 查询资源大类
     * 
     * @return
     */
    @RequestMapping(value = "/resType")
    public @ResponseBody ResultCodeVo getResType() {
        ResultCodeVo resultCodeVo = null;
        try {
            resultCodeVo = resourceService.queryResType();
        } catch (Exception e) {
            log.error(StringUtils.format("获取资源类型失败,类名:{0}", this.getClass().getName()), e, e.getMessage());
            resultCodeVo = new ResultCodeVo(false, "", "获取资源失败");
        }
        return resultCodeVo;
    }

    /**
     * 获取资源子类类
     * 
     * @param code 子类
     * @return
     */
    @RequestMapping(value = "/resType/{code}")
    public @ResponseBody ResultCodeVo getResTypeChilds(@PathVariable Integer code) {
        ResultCodeVo resultCodeVo = null;
        if (code == null) {
            return new ResultCodeVo(false, "", "参数不完整,code值为空");
        }
        try {
            resultCodeVo = resourceService.queryResTypeChilds(code);
        } catch (Exception e) {
            log.error(StringUtils.format("获取资源子类失败：基类code:{0}", code), e, e.getMessage());
        }
        return resultCodeVo;
    }

    /**
     * 封面上传
     * 
     * @param file MultipartFile
     * @return
     */
    @RequestMapping(value = "/uploadCover.html",
                    params = "do=upload")
    public @ResponseBody ResultCodeVo uploadCover(MultipartFile file) {
        return FtpHelper.ftpUploadFile(file, FtpHelper.MEDIA_COVER);
    }
    /*
    *//**
       * 资源上传
       * 
       * @param list 文件列表
       * @param request 请求对象
       * @return
       *//*
         * @RequestMapping(value="/saveList") public @ResponseBody ResultCodeVo saveList(String
         * list, HttpServletRequest request){ ResultCodeVo resultCodeVo=new ResultCodeVo(true,
         * CodeConstants.SAVE_ERROR,"");
         * 
         * UserInfoVo userInfoVo= BeanHelper.getWebUserFromCookie(request); if (userInfoVo == null)
         * { resultCodeVo.setMsg("请登录"); return resultCodeVo; } try { return
         * resourceService.saveList(list,userInfoVo,getIp(request),getHostByRequest(request)); }
         * catch (Exception e) { resultCodeVo.setMsg("新增资源保存出错,请确认资源类型是否选择正确。");
         * logError(request,"新增资源保存出错",e); } return resultCodeVo; }
         */
}
