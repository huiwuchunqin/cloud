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
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.textbook.service.TextbookKnowledgePointSerialService;
import com.baizhitong.resource.manage.textbook.service.TextbookKnowledgePointService;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;
import com.baizhitong.resource.model.vo.share.ShareKnowledgePointVo;
import com.baizhitong.resource.model.vo.share.ZtreeHelpVo;
import com.baizhitong.utils.StringUtils;

/**
 * 教材知识点控制器
 * 
 * @author zhangqiang
 * @date 2015年12月15日 下午7:53:42
 */
@Controller
@RequestMapping("/manage/textbook")
public class TextbookKnowledgePointAction extends BaseAction {

    /** 学段信息接口 */
    private @Autowired SectionService                      sectionService;
    /** 教材知识点信息接口 */
    private @Autowired TextbookKnowledgePointService       textbookKnowledgePointService;
    /** 知识体系信息接口 */
    private @Autowired TextbookKnowledgePointSerialService textbookKnowledgePointSerialService;

    /**
     * 跳转到教材知识点页面
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月15日 下午8:02:13
     */
    @RequestMapping(value = "/knowledgePoint.html")
    public String jumpToTextbookKnowLedgePointPage(HttpServletRequest request, ModelMap map) {
        try {
            List<ShareCodeSection> sectionList = sectionService.selectSectionList();
            map.addAttribute("sectionList", sectionList);
            UserInfoVo userInfo =getUserInfoVo();
            map.addAttribute("sectionCode", userInfo.getUserSectionCode());
            map.addAttribute("subjectCode", userInfo.getUserSubjectCode());
        } catch (Exception e) {
            log.error("跳转到教材知识点页面失败！", e);
        }
        return "/manage/textbook/textbook_knowledge_point.html";
    }

    /**
     * 获取知识体系知识点
     * 
     * @param request
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本
     * @param textbookCode 教材编码
     * @param termCode 学期编码
     * @param sectionCode 学段编码
     * @param map
     * @return
     */
    @RequestMapping(value = "/getKnowledgeTree.html")
    public @ResponseBody List<NodeTreeVo> getNodeTree(HttpServletRequest request, String nodeType, String code,
                    String gradeCode, String subjectCode, String textbookVerCode, String textbookCode, String termCode,
                    String sectionCode, ModelMap map) {
        try {
            if ("knowledgeSerial".equals(nodeType)) {
                return textbookKnowledgePointService.getKnowledgeTopNodeTreeList(code);
            } else if ("knowledge".equals(nodeType)) {
                return textbookKnowledgePointService.getKnowledgeChildNodeTreeList(code);
            } else if (StringUtils.isEmpty(nodeType)) {
                return textbookKnowledgePointService.getNodeTreeList(sectionCode, gradeCode, subjectCode,
                                textbookVerCode, textbookCode, termCode);
            }
            return null;
        } catch (Exception e) {
            log.error("获取教材知识点树信息异常！", e);
            return null;
        }
    }

    /**
     * 跳转到知识点新增页面
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/knowledgeAdd.html")
    public String toKnowLedgeAdd(ModelMap map, String pcode, Integer level, String kpSerialCode) {
        try {
            List<ShareCodeSection> sectionList = sectionService.selectSectionList();
            map.addAttribute("sectionList", sectionList);
            map.addAttribute("pcode", pcode);
            map.addAttribute("level", level);
            map.addAttribute("kpSerialCode", kpSerialCode);
            return "/manage/textbook/textbook_knowledge_point_add.html";
        } catch (Exception e) {
            log.error("知识点新增页面跳转 查询学段出错", e);
            return "/manage/textbook/textbook_knowledge_point_add.html";
        }
    }

    /**
     * 保存知识点
     * 
     * @param request @param knowledgeVo @return 结果vo
     * 
     * @exception
     */
    @RequestMapping("/addKnowledge.html")
    @ResponseBody
    public ResultCodeVo addKnowLedge(HttpServletRequest request, ShareKnowledgePointVo knowledgeVo) {
        if (knowledgeVo.getLevel() != null && knowledgeVo.getLevel() > 5) {
            return ResultCodeVo.errorCode("最多只能添加五层");
        }
        if (StringUtils.isEmpty(knowledgeVo.getName())) {
            return ResultCodeVo.errorCode("知识点名称不能为空");
        }
        try {
            return textbookKnowledgePointService.saveKnowLedge(knowledgeVo);
        } catch (Exception e) {
            log.error("知识点保存失败", e);
            return ResultCodeVo.errorCode("知识点保存失败");
        }
    }

    /**
     * 
     * 知识点修改 @param map @param gid 知识点id @return
     * 
     * @exception
     */
    @RequestMapping(value = "/editKnowledge.html")
    public String toKnowLedgeEdit(ModelMap map, String gid) {
        try {
            ShareKnowledgePointVo vo = textbookKnowledgePointService.getKnowLedgePointTextBook(gid);
            map.put("knowledge", vo);
            return "/manage/textbook/textbook_knowledge_point_edit.html";
        } catch (Exception e) {
            log.error("知识点修改页面跳转 知识点出错", e);
            return "/manage/textbook/textbook_knowledge_point_edit.html";
        }
    }

    /**
     * 更新知识点
     * 
     * @param request @param knowledgeVo @return 结果vo
     * 
     * @exception
     */
    @RequestMapping("/updateKnowledge.html")
    @ResponseBody
    public ResultCodeVo updateKnowLedge(HttpServletRequest request, String name, String description, String gid) {
        if (StringUtils.isEmpty(name)) {
            return ResultCodeVo.errorCode("知识点名称不能为空");
        }
        try {
            return textbookKnowledgePointService.updateTextBookKnowLedge(name, description, gid);
        } catch (Exception e) {
            log.error("知识点保存失败", e);
            return ResultCodeVo.errorCode("知识点保存失败");
        }
    }

    /**
     * 知识点删除
     * 
     * @param gid 知识点id @return
     * 
     * @exception
     */
    @RequestMapping(value = "/delKnowledge.html")
    @ResponseBody
    public ResultCodeVo delKnowLedge(String gid) {
        try {
            textbookKnowledgePointService.delKnowLedge(gid);
            return ResultCodeVo.rightCode("删除成功");
        } catch (Exception e) {
            log.error("知识点删除出错", e);
            return ResultCodeVo.errorCode("知识点删除出错");
        }
    }

    /**
     * 查询根节点 ()<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param resId 资源id
     * @return
     */
    @RequestMapping("/getKnowledgeRoot.html")
    public @ResponseBody List<ZtreeHelpVo> getKnowLedgeRoot(String sectionCode, String subjectCode, Integer resId) {
        try {
            return textbookKnowledgePointService.getKnowledgeTreeRoot(subjectCode, sectionCode, resId);
        } catch (Exception e) {
            log.error("获取根节点异常！", e);
            return null;
        }
    }

    /**
     * 查询所有的 ()<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param resId 资源id
     * @return
     */
    @RequestMapping("/getKnowLedgeAll.html")
    public @ResponseBody ResultCodeVo getAll(String subjectCode, String sectionCode, Integer resId, String gradeCode,
                    String textbookVerCode, String termCode) {
        try {
            return ResultCodeVo.rightCode("查询成功", textbookKnowledgePointService.getAll(subjectCode, sectionCode, resId,
                            gradeCode, textbookVerCode, termCode));
        } catch (Exception e) {
            log.error("获取根节点异常！", e);
            return null;
        }
    }

    /**
     * 
     * 选中的章节
     * 
     * @param resId
     * @param subjectCode
     * @param sectionCode
     * @param gradeCode
     * @param textbookVerCode
     * @param textbookCode
     * @return
     */
    @RequestMapping("/getSelectedKnowledge.html")
    public @ResponseBody List<ZtreeHelpVo> getSelectedKnowledgeList(String sectionCode, String subjectCode,
                    Integer resId, Integer resTypeL1) {
        try {
            return textbookKnowledgePointService.getResRelateKnowLedgeParent(resId, sectionCode, subjectCode,
                            resTypeL1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 查询子节点
     * 
     * @param code 父节点
     * @return
     */
    @RequestMapping("/getContentKnowledge.html")
    public @ResponseBody List<ZtreeHelpVo> getContentKnowLedge(String code) {
        try {
            return textbookKnowledgePointService.getContentKnowLedge(code);
        } catch (Exception e) {
            log.error("获取子节点异常！", e);
            return null;
        }
    }

    /**
     * 更新节点顺序 ()<br>
     * 
     * @param gid 主键
     * @param kpSerialCode 体系
     * @param type 移动类型 1 上移动 2下移
     * @return
     */
    @RequestMapping("/updateKnowledgeDispOrder.html")
    @ResponseBody
    public ResultCodeVo updateDispOrder(String kpSerialCode, String gid, Integer type) {
        return textbookKnowledgePointService.updateDispOrder(kpSerialCode, gid, type);
    }
}
