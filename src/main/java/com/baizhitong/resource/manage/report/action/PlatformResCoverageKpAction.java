package com.baizhitong.resource.manage.report.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.manage.report.service.PlatformResCoverageKpService;
import com.baizhitong.resource.manage.report.service.PlatformResCoverageKpsService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.vo.report.NodeVo;

@RequestMapping("/manage/coverageKp")
@Controller
public class PlatformResCoverageKpAction extends BaseAction {
    private @Autowired PlatformResCoverageKpService  coverageKpService; // 知识点覆盖率接口
    private @Autowired PlatformResCoverageKpsService coverageKpsService;// 知识体系覆盖率接口
    private @Autowired SectionService                sectionService;

    /**
     * 跳转到知识点资源覆盖率查看页面 ()<br>
     * 
     * @param tbvCode
     * @return
     */
    @RequestMapping("/toCoverageKp.html")
    public String toCoverageKp(ModelMap map, Integer baseDate, Integer id, String chapterName) {
        try {
            map.put("kpCoverage", JSON.toJSONString(coverageKpsService.getById(id)));
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            map.put("baseDate", baseDate);
            /*
             * map.put("chapterList",
             * JSON.toJSONString(textbookChapterService.getChapterTopNodeTreeList(tbCode,chapterName
             * )));
             */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "/manage/report/PlatformResCoverageKp.html";
    }

    /**
     * 查询知识点覆盖率 ()<br>
     * 
     * @param code
     * @return
     */
    @RequestMapping("/getKpCoverageList.html")
    @ResponseBody
    public List<NodeVo> getKpCoverageList(String code, String kpsCode, Integer baseDate) {
        return coverageKpService.getChapterCoverageList(code, kpsCode, baseDate);
    }

}
