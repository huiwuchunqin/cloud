package com.baizhitong.resource.manage.furtherEducation.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.furtherEducation.service.IFurtherEducationService;

/**
 * 
 * 机构升学控制类 FurtherEducationAction TODO
 * 
 * @author creator zhangqd 2016年9月9日 下午2:31:14
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/furtherEducation")
public class FurtherEducationAction extends BaseAction {
    /**
     * 机构升学接口
     */
    @Autowired
    private IFurtherEducationService furtherEducationService;

    @RequestMapping(value = "/update.html")
    @ResponseBody
    public ResultCodeVo OrgFurtherEdu(String orgCode) {
        return furtherEducationService.updateStudyYearTerm(orgCode);
    }
    
    /**
     * 跳转到升级页面
     * ()<br>
     * @return
     */
    @RequestMapping("/toAdminClassUpdate.html")
    public String toAdminClassUpdate(){
        return "/manage/furtherEdu/furtherEdu.html";
    }
    /**
     * 查询需要升级的班级
     * ()<br>
     * @return
     */
    @RequestMapping("/adminClassToUpdate.html")
    @ResponseBody
    public List getAdminClass(){
        return furtherEducationService.getAdminClassToUpdate(getOrgCode());
    }
    /**
     * 查询需要升级的班级
     * ()<br>
     * @return
     */
    @RequestMapping("/tInfoToUpdate.html")
    @ResponseBody
    public List getTInfo(){
        return furtherEducationService.getAdminClassWidthTeaInfo(getOrgCode());
    }
    /**
     * 更新行政班级信息
     * ()<br>
     * @return
     */
    @RequestMapping("/updateAdminClass.html")
    @ResponseBody
    public ResultCodeVo updateAdminClass(){
        return furtherEducationService.updateAdminClass(getOrgCode());
    }
    /**
     * 更新任教信息
     * ()<br>
     * @param adminClassCodes
     * @param gradeCodes
     * @return
     */
    @RequestMapping("/updateTInfo.html")
    @ResponseBody
    public ResultCodeVo updateTInfo(){
        return furtherEducationService.updateTeachingInfo();
    }
}
