package com.baizhitong.resource.manage.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.utils.GradeEntryYearMap;

@Controller
@RequestMapping("/manage/common")
public class CommonAction extends BaseAction{
    /**
     * 获取班级年级所对应的入学年份
     * ()<br>
     * @param gradeCode 年级编码
     * @return  入学年份
     */
    @RequestMapping("/getEntryYear.html")
    @ResponseBody
    public String getEntryYear(String gradeCode){
        try {
            return GradeEntryYearMap.getEntryYear(gradeCode,getStudyYearCode());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            e.printStackTrace();
            return Integer.parseInt(getStudyYearCode())+"";
        }
    }

}
