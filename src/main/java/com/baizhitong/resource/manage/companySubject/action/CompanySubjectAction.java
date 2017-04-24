package com.baizhitong.resource.manage.companySubject.action;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.companyGradeSubject.service.ICompanyGradeSubjectService;
import com.baizhitong.resource.manage.companySubject.service.ICompanySubjectSerivce;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;

/**
 * 机构学科控制类 CompanySubjectAction
 * 
 * @author creator gaow 2016年1月21日 下午4:22:46
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/companySubject")
public class CompanySubjectAction extends BaseAction {
    /** 机构学科接口 */
    @Autowired
    private ICompanyGradeSubjectService companyGradeSubjectService;
    /** 机构学科年级接口 */
    @Autowired
    private ICompanySubjectSerivce      companySubjectService;

    /**
     * 查询机构学科 ()<br>
     * 
     * @param orgCode 机构编码
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    @RequestMapping("/subjectPageList.html")
    @ResponseBody
    public Page getCompanySubject(HttpServletRequest request, Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = null;
        CompanyInfoVo companyInfo =  getCompanyInfo();
        if (companyInfo == null)
            return null;
        String orgCode = companyInfo.getOrgCode();
        return companySubjectService.getCompanySubject(orgCode, "", rows, page);
    }

    /**
     * 跳转到学科新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toSubjectAdd.html")
    public String toSubjectAdd() {
        return "/manage/company/companySubjectAdd.html";
    }

    /**
     * 跳转到机构学科页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toCompanySubject.html")
    public String toCompanySubject() {
        return "/manage/company/companySubject.html";
    }

    /**
     * 跳转到学科年级设置页面 ()<br>
     * 
     * @param map
     * @param subjectCode 学科编码
     * @param subjectName 学科名称
     * @return
     */
    @RequestMapping("/toSubjectGrade.html")
    public String toSubjectGrade(ModelMap map, String subjectCode, String subjectName) {
        map.put("subjectName", URLDecoder.decode(subjectName));
        map.put("subjectCode", subjectCode);
        map.put("gradeList", JSON.toJSONString(companyGradeSubjectService.getSubjectGradeList(subjectCode)));
        return "/manage/company/companySubjectGrade.html";
    }

    /**
     * 查询机构学科 ()<br>
     * 
     * @return
     */
    @RequestMapping("/subjectList.html")
    @ResponseBody
    public List<ShareCodeSubjectVo> getCompanySubjectList(String sectionCode) {
        return companySubjectService.getCompanySubjectList(sectionCode);
    }

    /**
     * 查询机构学科 ()<br>
     * 
     * @return
     */
    @RequestMapping("/subjectNotSelectList.html")
    @ResponseBody
    public List<ShareCodeSubjectVo> getCompanySubjectNotSelectList() {
        return companySubjectService.getCompanyNotSelectSubject();
    }

    /**
     * 新增学科 ()<br>
     * 
     * @param subjectCode 学科编码
     * @return
     */
    @RequestMapping("/addSubject.html")
    @ResponseBody
    public ResultCodeVo addSubject(@RequestParam(value = "subjectCodes[]",
                                                 required = false) String[] subjectCodes) {
        return companySubjectService.saveCompanySubject(subjectCodes, null);
    }

    /**
     * 删除学科 ()<br>
     * 
     * @param subjectCode 学科编码
     * @return
     */
    @RequestMapping("/deleteSubject.html")
    @ResponseBody
    public ResultCodeVo deleteSubject(@RequestParam(value = "subjectCodes[]",
                                                    required = false) String[] subjectCodes) {
        return companySubjectService.delCompanySubject(subjectCodes);
    }

}
