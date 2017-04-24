package com.baizhitong.resource.manage.studyYear.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.share.ShareCodeStudyYearDao;
import com.baizhitong.resource.dao.share.ShareCodeYearTermDao;
import com.baizhitong.resource.manage.studyYear.service.IStudyYearTermService;
import com.baizhitong.resource.model.share.ShareCodeStudyYear;
import com.baizhitong.resource.model.share.ShareCodeYearTerm;
import com.baizhitong.utils.StringUtils;

/**
 * 学年学期接口实现 StudyYearTermServiceImpl
 * 
 * @author creator BZT 2016年4月28日 下午5:40:49
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class StudyYearTermServiceImpl extends BaseService implements IStudyYearTermService {
    private @Autowired ShareCodeYearTermDao  studyYearTermDao;
    private @Autowired ShareCodeStudyYearDao studyYearDao;

    /**
     * 查询学期列表 ()<br>
     * 
     * @param map
     * @return
     */
    public List<ShareCodeYearTerm> getTermList(Map<String, Object> map) {
        return studyYearTermDao.getTermList(map);
    }

    /**
     * 查询学期分页列表 ()<br>
     * 
     * @param map
     * @return
     */
    public Page getTermPageList(Map<String, Object> map) {
        return studyYearTermDao.getTermPageList(map);
    }

    /**
     * 查询学期 ()<br>
     * 
     * @param yearTermCode
     * @return
     */
    public ShareCodeYearTerm getCodeYearTerm(String yearTermCode) {
        return studyYearTermDao.getCodeYearTerm(yearTermCode);
    }

    /**
     * 
     * (保存学年学期)<br>
     * 
     * @param shareCodeYearTerm
     * @return
     */
    public ResultCodeVo addTerm(ShareCodeYearTerm shareCodeYearTerm) {
       
        /*--------------学年学期-----------------*/
        ShareCodeYearTerm termExit = studyYearTermDao
                        .getCodeYearTerm(shareCodeYearTerm.getYearBegin() + shareCodeYearTerm.getTermCode());
        if (termExit != null && StringUtils.isEmpty(shareCodeYearTerm.getGid()))
            return ResultCodeVo.errorCode("学期已经存在！");
        // 新增
        /*
         * if(StringUtils.isEmpty(shareCodeYearTerm.getGid())){ String
         * startStr=shareCodeYearTerm.getYearBegin().toString()+"0901"; Integer
         * tomorrowYear=shareCodeYearTerm.getYearBegin()+1; String
         * endStr=tomorrowYear.toString()+"0831"; Integer startDate=Integer.parseInt(startStr);
         * Integer endDate=Integer.parseInt(endStr); Integer
         * currentDate=Integer.parseInt(DateUtils.getDate("yyyyMMdd"));
         * if(currentDate<startDate||currentDate>endDate){ return
         * ResultCodeVo.errorCode("当前时间不属于选择的学年，不可新增！"); } }
         */
        shareCodeYearTerm.setModifyIP(getIp());
        shareCodeYearTerm.setModifyPgm("studyYearTermService");
        shareCodeYearTerm.setModifyTime(new Timestamp(new Date().getTime()));
        shareCodeYearTerm.setSysVer(0);
        // 新增
        boolean yearCount = true;
        if (StringUtils.isEmpty(shareCodeYearTerm.getGid())) {
            shareCodeYearTerm.setGid(UUID.randomUUID().toString());
            /*--------------学年-----------------*/
            ShareCodeStudyYear yearExist = studyYearDao.getYear(shareCodeYearTerm.getYearBegin().toString());
            if (yearExist == null) {
                ShareCodeStudyYear year = new ShareCodeStudyYear();
                year.setStudyYearCode(shareCodeYearTerm.getYearBegin().toString());
                year.setStudyYearName(shareCodeYearTerm.getYearBegin() + "到" + shareCodeYearTerm.getYearEnd());
                year.setYearBegin(shareCodeYearTerm.getYearBegin());
                year.setYearEnd(shareCodeYearTerm.getYearEnd());
                year.setModifyPgm("studyYearTermService");
                year.setGid(UUID.randomUUID().toString());
                year.setModifyTime(new Timestamp(new Date().getTime()));
                year.setModifyIP(getIp());
                year.setSysVer(0);
                yearCount = studyYearDao.addStudyYear(year);
            }
        }
        shareCodeYearTerm.setYearTermCode(shareCodeYearTerm.getYearBegin() + shareCodeYearTerm.getTermCode());
        boolean termCount = studyYearTermDao.addOrUpdateTerm(shareCodeYearTerm);
        if (termCount && yearCount) {
            return ResultCodeVo.rightCode("学年学期保存成功！");
        } else {
            return ResultCodeVo.errorCode("学年学期保存失败！");
        }
    }

    /*
     * 删除学期
     */
    public ResultCodeVo deleteCodeYearTerm(String gid) {
        ShareCodeYearTerm term = studyYearTermDao.getCodeYearTermByGid(gid);
        studyYearTermDao.deleteCodeYearTerm(gid);
        if (term != null) {
            studyYearDao.deleteStudyYear(term.getYearBegin().toString());
        }
        return ResultCodeVo.rightCode("删除成功");
    }
}
