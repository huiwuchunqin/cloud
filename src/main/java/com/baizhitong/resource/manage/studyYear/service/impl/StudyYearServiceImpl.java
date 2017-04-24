package com.baizhitong.resource.manage.studyYear.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.dao.share.ShareCodeStudyYearDao;
import com.baizhitong.resource.manage.studyYear.service.IStudyYearService;
import com.baizhitong.resource.model.share.ShareCodeStudyYear;

/**
 * 学年学期接口实现 StudyYearServiceImpl TODO
 * 
 * @author creator BZT 2016年4月28日 下午5:32:30
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class StudyYearServiceImpl implements IStudyYearService {
    private @Autowired ShareCodeStudyYearDao studyYearDao;

    /**
     * 查询学年列表 ()<br>
     * 
     * @param param
     * @return
     */
    public List<ShareCodeStudyYear> getStudyYearList(Map<String, Object> param) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 查询学年分页信息 ()<br>
     * 
     * @param param
     * @return
     */
    public Page getStudyYearPage(Map<String, Object> param) {
        // TODO Auto-generated method stub
        return studyYearDao.getStudyYearPage(param);
    }

}
