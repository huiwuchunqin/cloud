package com.baizhitong.resource.manage.authority.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeGradeDao;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeSectionDao;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeSubjectDao;
import com.baizhitong.resource.manage.authority.service.CommonService;

/**
 * 
 * 权限共通Service实现类
 * 
 * @author creator ZhangQiang 2016年9月28日 下午3:20:36
 * @author updater
 *
 * @version 1.0.0
 */
@Service(value = "commonService")
public class CommonServiceImpl extends BaseService implements CommonService {

    private @Autowired SettingUserPriviledgeSectionDao priviledgeSectionDao;
    private @Autowired SettingUserPriviledgeSubjectDao priviledgeSubjectDao;
    private @Autowired SettingUserPriviledgeGradeDao   priviledgeGradeDao;

    /**
     * 
     * (获取用户可以审核的学段集合)<br>
     * 处理说明：拼接返回字符串，用逗号分隔
     * 
     * @param userCode 用户编码
     * @return
     */
    @Override
    public String queryUserAuthSectionCodes(String userCode) {
        List<Map<String, Object>> mapList = priviledgeSectionDao.selectSectionListByUserCode(userCode);
        String sectionCodes = "";
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                sectionCodes += MapUtils.getString(map, "code") + ",";
            }
        }
        if (StringUtils.isNotEmpty(sectionCodes)) {
            sectionCodes = sectionCodes.substring(0, sectionCodes.length() - 1);
        } else {
            // 没查询到数据的情况，默认为0
            sectionCodes = "0";
        }
        return sectionCodes;
    }

    /**
     * 
     * (获取用户可以审核的学科集合)<br>
     * 处理说明：拼接返回字符串，用逗号分隔
     * 
     * @param userCode 用户编码
     * @return
     */
    @Override
    public String queryUserAuthSubjectCodes(String userCode) {
        List<Map<String, Object>> mapList = priviledgeSubjectDao.selectListByUserCode(userCode);
        String subjectCodes = "";
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                subjectCodes += MapUtils.getString(map, "subjectCode") + ",";
            }
        }
        if (StringUtils.isNotEmpty(subjectCodes)) {
            subjectCodes = subjectCodes.substring(0, subjectCodes.length() - 1);
        } else {
            // 没查询到数据的情况，默认为0
            subjectCodes = "0";
        }
        return subjectCodes;
    }

    /**
     * 
     * (获取用户可以审核的年级集合)<br>
     * 处理说明：拼接返回字符串，用逗号分隔
     * 
     * @param userCode 用户编码
     * @return
     */
    @Override
    public String queryUserAuthGradeCodes(String userCode) {
        List<Map<String, Object>> mapList = priviledgeGradeDao.selectListByUserCode(userCode);
        String gradeCodes = "";
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                gradeCodes += MapUtils.getString(map, "gradeCode") + ",";
            }
        }
        if (StringUtils.isNotEmpty(gradeCodes)) {
            gradeCodes = gradeCodes.substring(0, gradeCodes.length() - 1);
        } else {
            // 没查询到数据的情况，默认为0
            gradeCodes = "0";
        }
        return gradeCodes;
    }

}
