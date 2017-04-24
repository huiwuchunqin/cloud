package com.baizhitong.resource.manage.subject.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.subject.service.SubjectService;

/**
 * 学科控制类
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:44:50
 */
@Controller
@RequestMapping(value = "/manage/subject")
public class SubjectAction extends BaseAction {
    private @Autowired SubjectService subjectService;
    private @Autowired SectionService sectionService;

    /**
     * 分页查询学科信息
     * 
     * @param subjectName
     * @param page
     * @param rows
     * @return
     * @author gaow
     * @date:2015年12月14日 下午6:16:32
     */
    @RequestMapping(value = "/listSubject.html")
    public @ResponseBody Page<Map<String, Object>> getSubjectPage(String subjectName, Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 20;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            return subjectService.selectSubjectPage(map, rows, page);
        } catch (Exception e) {
            log.error("查询学科信息出错", e);
            return null;
        }
    }

    /**
     * 跳转到学科查询页面
     * 
     * @return
     * @author gaow
     * @date:2015年12月14日 下午6:19:10
     */
    @RequestMapping(value = "/toSubject.html")
    public String toSubject() {
        return "/manage/subject/subjectList.html";
    }

    /**
     * 
     * 跳转到学科新增页面
     * 
     * @return
     */
    @RequestMapping(value = "/toSubjectAdd.html")
    public String toSubjectAdd(ModelMap map) {
        try {
            map.put("sectionList", sectionService.selectSectionList());
            return "/manage/subject/subjectAdd.html";
        } catch (Exception e) {
            return "/manage/subject/subjectAdd.html";
        }
    }

    /**
     * 保存学科信息 ()<br>
     * 
     * @param sectionCode 学段
     * @param name 学科名称
     * @param description 备注
     * @return
     */
    @RequestMapping("/addSubject.html")
    @ResponseBody
    public ResultCodeVo addSubject(String sectionCode, String name, String description) {
        return subjectService.saveSubject(sectionCode, name, description);
    }
}
