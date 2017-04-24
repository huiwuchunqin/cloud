package com.baizhitong.resource.manage.textbook.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.textbook.service.TextbookKnowledgePointSerialService;
import com.baizhitong.resource.manage.textbook.service.TextbookKnowledgePointService;
import com.baizhitong.resource.model.share.ShareKnowledgePointSerial;
import com.baizhitong.resource.model.vo.share.ShareRelTextbookKpsVo;
import com.baizhitong.utils.StringUtils;

/**
 * 知识体系控制类
 * 
 * @author creator gaow 2016年1月13日 下午8:41:55
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/knowledgeSerial")
public class TextbookKnowledgeSerialAction extends BaseAction {
    /** 知识体系信息接口 */
    private @Autowired TextbookKnowledgePointSerialService textbookKnowledgePointSerialService;
    /** 学段信息接口 */
    private @Autowired SectionService                      sectionService;
    /** 知识点接口 */
    private @Autowired TextbookKnowledgePointService       textbookKnowledgePointService;

    /**
     * 
     * 跳转到知识体系新增页面
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/knowledgeSerialAdd.html")
    public String toKnowledgeSerialAdd(ModelMap map, String kpSerialCode) {
        try {
            map.addAttribute("kpSerialCode", kpSerialCode);
            map.addAttribute("sectionList", sectionService.selectSectionList());
            return "/manage/textbook/knowledge_point_serial_add.html";
        } catch (Exception e) {
            log.error("知识点体系新增页面跳转 查询学段出错", e);
            return "/manage/textbook/knowledge_point_serial_add.html";
        }
    }

    /**
     * 保存知识体系 教材与知识体系关系
     * 
     * @param relTextbookKpsVo
     * @param name
     * @param memo
     * @return
     */
    @RequestMapping("/addKnowledgeSerial.html")
    @ResponseBody
    public ResultCodeVo addKnowledgeSerial(ShareRelTextbookKpsVo relTextbookKpsVo, String name, String memo) {
        if (StringUtils.isEmpty(name)) {
            return ResultCodeVo.errorCode("知识体系名称不能为空");
        }
        try {
            return textbookKnowledgePointSerialService.addTextbookKnowledgePointSerial(relTextbookKpsVo, name, memo);
        } catch (Exception e) {
            log.error("知识体系保存失败", e);
            return ResultCodeVo.errorCode("保存失败");
        }
    }

    /**
     * 
     * 知识体系修改
     * 
     * @param map @param gid 知识点id @return
     * 
     * @exception
     */
    @RequestMapping(value = "/editKnowledgeSerial.html")
    public String toKnowLedgeSerialEdit(ModelMap map, String gid) {
        try {
            ShareKnowledgePointSerial knowledgePointSerial = textbookKnowledgePointSerialService
                            .getKnowledgeSerial(gid);
            map.put("knowLedgeSerial", knowledgePointSerial);
            return "/manage/textbook/knowledge_point_serial_edit.html";
        } catch (Exception e) {
            log.error("知识点修改页面跳转 知识点出错", e);
            return "/manage/textbook/knowledge_point_serial_edit.html";
        }
    }

    /**
     * 更新知识体系
     * 
     * @param request
     * @param name 体系名称
     * @param memo 备注
     * @param gid 主键
     * @return
     */
    @RequestMapping("/updateKnowledgeSerial.html")
    @ResponseBody
    public ResultCodeVo updateKnowLedgeSerial(HttpServletRequest request, String name, String memo, String gid) {
        if (StringUtils.isEmpty(name)) {
            return ResultCodeVo.errorCode("知识点名称不能为空");
        }
        try {
            return textbookKnowledgePointSerialService.updateKnowledgePointSerial(name, memo, gid);
        } catch (Exception e) {
            log.error("知识点保存失败", e);
            return ResultCodeVo.errorCode("知识点保存失败");
        }
    }

    /**
     * 删除知识体系
     * 
     * @param gid 知识点体系主键 @return
     * 
     * @exception
     */
    @RequestMapping(value = "/delKnowledgeSerial.html")
    @ResponseBody
    public ResultCodeVo delKnowLedge(String gid) {
        try {
            return textbookKnowledgePointSerialService.delKnowledgeSerial(gid);
        } catch (Exception e) {
            log.error("知识点删除出错", e);
            return ResultCodeVo.errorCode("知识点删除出错");
        }
    }

    /**
     * 根据学科查知识体系 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    @RequestMapping(value = "/getKpsBySubjectCode.html")
    @ResponseBody
    public List<ShareKnowledgePointSerial> getKpsBySubjectCode(String subjectCode) {
        return textbookKnowledgePointSerialService.getKnowledgePointSerialBySubjectCode(subjectCode);
    }
}
