package com.baizhitong.resource.manage.res.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.UserHelper;
import com.baizhitong.resource.dao.res.ResShareCheckDao;
import com.baizhitong.resource.dao.test.QuestionDao;
import com.baizhitong.resource.manage.authority.service.CommonService;
import com.baizhitong.resource.manage.res.service.QuestionService;
import com.baizhitong.resource.model.res.ResShareCheck;
import com.baizhitong.resource.model.test.Test;
import com.baizhitong.utils.StringUtils;

@Service
public class QuestionServiceImpl extends BaseService implements QuestionService {
    @Autowired
    QuestionDao           questionDao;
    @Autowired
    ResShareCheckDao      shareCheckDao;
    /** 权限共通服务 */
    @Autowired
    private CommonService commonService;

    /**
     * 查询测验信息 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getQuestionPage(Map<String, Object> param, Integer page, Integer rows) {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        if (isEduStaff()) {
            String sectionCodes = commonService.queryUserAuthSectionCodes(userInfoVo.getUserCode());
            String subjectCodes = commonService.queryUserAuthSubjectCodes(userInfoVo.getUserCode());
            String gradeCodes = commonService.queryUserAuthGradeCodes(userInfoVo.getUserCode());
            param.put("sectionCodes", sectionCodes);
            param.put("subjectCodes", subjectCodes);
            param.put("gradeCodes", gradeCodes);
        }
        Page pageList = questionDao.getQuestionPage(param, page, rows);
        List<Map<String, Object>> mapList = pageList.getRows();
        String commonUrl = SystemConfig.questionPreviewUrl;
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                String questionCode = MapUtils.getString(map, "questionCode");
                String resAccessPath = MapUtils.getString(map, "resAccessPath");
                Integer shareCheckStatus = MapUtils.getInteger(map, "shareCheckStatus");
                List<ResShareCheck> checkLists = shareCheckDao.getRes(questionCode);
                Map<String, Object> checkMap = shareCheckDao.getRecentlyInfo(questionCode, shareCheckStatus);
                map.put("checkComments", MapUtils.getString(checkMap, "checkComments"));
                map.put("checkerName", MapUtils.getString(checkMap, "checkerName"));
                map.put("applyTime", MapUtils.getString(checkMap, "applyTime"));
                /*
                 * if(checkLists!=null&&checkLists.size()>0){ String
                 * accessPath=checkLists.get(0).getResAccessPath();
                 * if(StringUtils.isNotEmpty(accessPath)&&accessPath.indexOf("http")<0){
                 * accessPath=SystemConfig.getWebUrl()+accessPath; } map.put("resAccessPath",
                 * accessPath); }else{
                 */
                if (StringUtils.isEmpty(resAccessPath)) {
                    String path = commonUrl.replaceAll("(resCode[^&]*)", "resCode=" + questionCode);
                    String uid = UserHelper.encrypt(userInfoVo.getUserCode());
                    path = path + "&uid=" + uid;
                    map.put("resAccessPath", path);
                }
                /* } */
            }
        }
        return pageList;
    }

}
