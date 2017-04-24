package com.baizhitong.resource.manage.grade.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.manage.grade.service.GradeService;

/**
 * 年级控制类
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:44:50
 */
@Controller
@RequestMapping(value = "/manage/grade")
public class GradeAction extends BaseAction {
    private @Autowired GradeService gradeService; // 年级业务接口

    /**
     * 分页查询年级信息
     * 
     * @param gradeName 年级名称
     * @param page 页码
     * @param rows 每页记录数
     * @return
     * @author gaow
     * @date:2015年12月14日 下午6:16:32
     */
    @RequestMapping(value = "/listGrade.html")
    public @ResponseBody Page<Map<String, Object>> getGradePage(String gradeName, Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 20;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            return gradeService.selectGradePage(map, rows, page);
        } catch (Exception e) {
            log.error("查询年级信息出错", e);
            return null;
        }
    }

    /**
     * 跳转到年级查询页面
     * 
     * @return
     * @author gaow
     * @date:2015年12月14日 下午6:19:10
     */
    @RequestMapping(value = "/toGrade.html")
    public String toGrade() {
        return "/manage/grade/gradeList.html";
    }

}
