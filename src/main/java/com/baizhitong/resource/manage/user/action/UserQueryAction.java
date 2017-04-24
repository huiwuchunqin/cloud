package com.baizhitong.resource.manage.user.action;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.manage.user.service.UserQueryService;
import com.baizhitong.resource.model.vo.share.ShareUserCommonVo;
import com.baizhitong.resource.model.vo.share.ShareUserStudentVo;
import com.baizhitong.resource.model.vo.share.ShareUserTeacherVo;

/**
 * 用户信息控制器
 * 
 * @author zhangqiang
 * @date 2015年12月16日 上午10:31:27
 */
@Controller
@RequestMapping("/manage/user")
public class UserQueryAction extends BaseAction {
    /** 用户信息查询接口 */
    private @Autowired UserQueryService userQueryService;

    /**
     * 跳转到用户管理信息页面
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月16日 上午10:52:14
     */
    @RequestMapping(value = "/info.html")
    public String jumpToUserInfoPage(HttpServletRequest request, ModelMap map) {
        return "/manage/user/user_info.html";
    }

    /**
     * 获取用户分页信息
     * 
     * @param request 请求
     * @param userRole 用户身份
     * @param loginAccount 登录账号
     * @param userName 用户姓名
     * @param rows 每页大小
     * @param page 页码
     * @return
     * @author zhangqiang
     * @date 2015年12月16日 下午1:08:10
     */
    @RequestMapping("/search.html")
    public @ResponseBody Page<ShareUserCommonVo> getUserPageInfo(HttpServletRequest request, String userRole,
                    String loginAccount, String userName, Integer rows, Integer page) {
        if (rows == null)
            rows = 20;
        if (page == null)
            page = 1;
        try {
            return userQueryService.queryUserPageInfo(userRole, loginAccount, userName, rows, page);
        } catch (Exception e) {
            log.error("获取用户信息失败！", e);
            return null;
        }
    }

    /**
     * 跳转到教师基本信息页面
     * 
     * @param request 请求
     * @param teacherCode 教师用户编码
     * @return
     * @author zhangqiang
     * @date 2015年12月16日 下午2:41:28
     */
    @RequestMapping("/getTeacherInfo.html")
    public String jumpToTeacherPage(HttpServletRequest request, String teacherCode, ModelMap map) {
        try {
            ShareUserTeacherVo teacher = userQueryService.getTeacherByTeacherCode(teacherCode);
            map.addAttribute("teacher", teacher);
        } catch (Exception e) {
            log.error("获取教师基本信息失败！", e);
        }
        return "/manage/user/teacher.html";
    }

    /**
     * 跳转到学生基本信息页面
     * 
     * @param request 请求
     * @param studentCode 学生代码
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月16日 下午5:20:26
     */
    @RequestMapping("/getStudentInfo.html")
    public String jumpToStudentPage(HttpServletRequest request, String studentCode, ModelMap map) {
        try {
            ShareUserStudentVo student = userQueryService.getStudentByStudentCode(studentCode);
            map.addAttribute("student", student);
        } catch (Exception e) {
            log.error("获取学生基本信息失败！", e);
        }
        return "/manage/user/student.html";
    }
}
