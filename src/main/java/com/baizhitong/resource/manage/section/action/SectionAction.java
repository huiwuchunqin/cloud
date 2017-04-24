package com.baizhitong.resource.manage.section.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;

/**
 * 学段控制类
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:44:50
 */
@RequestMapping(value = "/manage/section")
@Controller
public class SectionAction extends BaseAction {
    private @Autowired SectionService sectionService; // 学段业务接口

    /**
     * 分页查询学段信息
     * 
     * @param sectionName
     * @param page
     * @param rows
     * @return
     * @author gaow
     * @date:2015年12月14日 下午6:16:32
     */
    @RequestMapping(value = "/listSection.html")
    public @ResponseBody Page<Map<String, Object>> getSectionPage(String sectionName, Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 20;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            return sectionService.selectSectionPage(map, rows, page);
        } catch (Exception e) {
            log.error("查询学段信息出错", e);
            return null;
        }
    }

    /**
     * 跳转到学段查询页面
     * 
     * @return
     * @author gaow
     * @date:2015年12月14日 下午6:19:10
     */
    @RequestMapping(value = "/toSection.html")
    public String toSection() {
        return "/manage/section/sectionList.html";
    }

    /**
     * 根据学段获取学科信息
     * 
     * @param sectionCode 学段编码
     * @return
     * @author zhangqiang
     * @date 2015年12月15日 下午5:32:44
     */
    @RequestMapping(value = "/getSubjectBySection.html")
    public @ResponseBody ResultCodeVo getSubjectListBySectionID(String sectionCode) {
        try {
            List<ShareCodeSubjectVo> subjectList = sectionService.getSectionSubject(sectionCode);
            return ResultCodeVo.rightCode(null, subjectList);
        } catch (Exception e) {
            log.error("根据学段获取学科信息失败！", e);
            return ResultCodeVo.errorCode(null);
        }
    }

    /**
     * 
     * (查询所有学段信息)<br>
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/list.html")
    @ResponseBody
    public List<ShareCodeSection> querySectionList(HttpServletRequest request) {
        try {
            return sectionService.selectSectionList();
        } catch (Exception e) {
            log.error("查询学段信息失败！", e);
            e.printStackTrace();
            return null;
        }
    }

    // public
}
