package com.baizhitong.resource.manage.studyYear.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.dao.share.ShareCodeYearTermDao;
import com.baizhitong.resource.dao.share.ShareOrgYearTermDao;
import com.baizhitong.resource.dao.share.SharePlatformYearTermDao;
import com.baizhitong.resource.manage.studyYear.service.IPlatfromYearTermService;
import com.baizhitong.resource.model.share.ShareCodeYearTerm;
import com.baizhitong.resource.model.share.ShareOrgYearTerm;
import com.baizhitong.resource.model.share.SharePlatformYearTerm;
import com.baizhitong.utils.DataUtils;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.ObjectUtils;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.TimeUtils;

@Service
public class PlatformYearTermServiceImpl extends BaseService implements IPlatfromYearTermService {
    private @Autowired SharePlatformYearTermDao sharePlatformYearTermDao;
    private @Autowired ShareCodeYearTermDao     studyYearTermDao;
    private @Autowired ShareOrgDao              shareOrgDao;
    private @Autowired ShareOrgYearTermDao      shareOrgYearTermDao;

    /*
     * 查询学年学期分页列表 ()<br>
     * 
     * @param mapList
     * 
     * @return
     */
    public Page getYearTermList(Map<String, Object> mapList) {
        return sharePlatformYearTermDao.getYearTermList(mapList);
    }

    /**
     * 新增或更新学年学期 ()<br>
     * 
     * @param yearTerm
     * @return
     */
    public ResultCodeVo updateOrAddYearTerm(SharePlatformYearTerm yearTerm, String startDate, String endDate) {
        UserInfoVo userInfoVo =getUserInfoVo(); 
        ShareCodeYearTerm studyYearTerm = studyYearTermDao.getCodeYearTerm(yearTerm.getYearTermCode());
        if (studyYearTerm == null) {
            return ResultCodeVo.errorCode("没有查到学期！");
        }
        yearTerm.setModifier(userInfoVo.getUserName());
        yearTerm.setModifyIP(getIp());
        yearTerm.setModifyPgm("platformYearTermService");
        yearTerm.setModifyTime(new Timestamp(new Date().getTime()));
        yearTerm.setStudyYearCode(studyYearTerm.getYearBegin().toString());
        yearTerm.setSysVer(1);
        yearTerm.setTermCode(studyYearTerm.getTermCode());
        String start = getBaseDate(startDate);
        String end = getBaseDate(endDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            yearTerm.setStartDate(new Timestamp(sdf.parse(startDate).getTime()));
            yearTerm.setEndDate(new Timestamp(sdf.parse(endDate).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long totalWeekNum = TimeUtils.getDaysFrom2Dates(end, start, "yyyy-MM-dd") / 7 + 1;
        yearTerm.setTotalWeekNum((int) totalWeekNum);
        // 新增
        if (StringUtils.isEmpty(yearTerm.getGid())) {
            SharePlatformYearTerm exist = sharePlatformYearTermDao.getPlatformYearTerm(yearTerm.getYearTermCode(),
                            yearTerm.getSectionCode());
            if (ObjectUtils.isNotNull(exist))
                return ResultCodeVo.errorCode("学期已经存在！");
            //校验学年学期是否可以新增
            Map<String, Object> lastYearTerm = sharePlatformYearTermDao.selectLastOrNextYearTermBySectionCode(
                            yearTerm.getSectionCode(), yearTerm.getYearTermCode(), 1);
            Map<String, Object> nextYearTerm = sharePlatformYearTermDao.selectLastOrNextYearTermBySectionCode(
                            yearTerm.getSectionCode(), yearTerm.getYearTermCode(), 2);
            if(MapUtils.isNotEmpty(lastYearTerm)){
                String lastStartDate=MapUtils.getString(lastYearTerm, "startDate");
                String lastEndDate=MapUtils.getString(lastYearTerm, "endDate");
                try {
                    long lastStartTime=sdf.parse(lastStartDate).getTime();
                    long lastEndTime=sdf.parse(lastEndDate).getTime()+24*3600*1000;//结束时间往后推一天
                    if (!(yearTerm.getStartDate().getTime() > lastStartTime
                                    && yearTerm.getStartDate().getTime() <= lastEndTime
                                    && yearTerm.getEndDate().getTime() > lastEndTime)) {
                        return ResultCodeVo.errorCode("当前学期的开始和结束时间与上学期的不对应，新增失败！");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(MapUtils.isNotEmpty(nextYearTerm)){
                String nextStartDate=MapUtils.getString(nextYearTerm, "startDate");
                String nextEndDate=MapUtils.getString(nextYearTerm, "endDate");
                try {
                    long nextStartTime=sdf.parse(nextStartDate).getTime()-24*3600*1000;//开始时间往前推一天
                    long nextEndTime=sdf.parse(nextEndDate).getTime();
                    if (!(yearTerm.getEndDate().getTime() < nextEndTime
                                    && yearTerm.getEndDate().getTime() >= nextStartTime
                                    && yearTerm.getStartDate().getTime() < nextStartTime)) {
                        return ResultCodeVo.errorCode("当前学期的开始和结束时间与下学期的不对应，新增失败！");
                    }
                } catch (ParseException e) { 
                    e.printStackTrace();
                }
            }
            yearTerm.setGid(UUID.randomUUID().toString());
            // 插入机构学期
            List<Map<String, Object>> orgList = shareOrgDao.getOrgList(yearTerm.getSectionCode());
            ShareOrgYearTerm orgYearTerm = new ShareOrgYearTerm();
            DataUtils.copySimpleObject(yearTerm, orgYearTerm);
            if (ObjectUtils.isNotNull(orgList)&&orgList.size() > 0) {
                for (Map<String, Object> org : orgList) {
                    String orgCode = MapUtils.getString(org, "orgCode");
                    orgYearTerm.setOrgCode(orgCode);
                    orgYearTerm.setModifier(userInfoVo.getUserName());
                    orgYearTerm.setModifyIP(getIp());
                    orgYearTerm.setModifyPgm("platformYearService");
                    orgYearTerm.setSectionCode(yearTerm.getSectionCode());
                    orgYearTerm.setEndDate(yearTerm.getEndDate());
                    orgYearTerm.setStartDate(yearTerm.getStartDate());
                    orgYearTerm.setOrgYearTermCode(orgCode + yearTerm.getYearTermCode());
                    orgYearTerm.setGid(UUID.randomUUID().toString());
                    orgYearTerm.setModifyTime(new Timestamp(new Date().getTime()));
                    ShareOrgYearTerm existOrgYearTerm = shareOrgYearTermDao.getTerm(yearTerm.getYearTermCode(), orgCode,
                                    yearTerm.getSectionCode());
                    if (ObjectUtils.isNull(existOrgYearTerm)) {
                        shareOrgYearTermDao.updateOrAddYearTerm(orgYearTerm);
                    }
                }
            }
            // 没有学段的机构，默认同小学的处理逻辑
            if (CoreConstants.SECTION_PRIMARY.equals(yearTerm.getSectionCode())) {
                // 查询没有学段的机构列表
                List<Map<String, Object>> noSectionOrgList = shareOrgDao.selectNoSectionOrgList();
                if (ObjectUtils.isNotNull(noSectionOrgList) && noSectionOrgList.size() > 0) {
                    for (Map<String, Object> noSectionOrg : noSectionOrgList) {
                        String orgCode = MapUtils.getString(noSectionOrg, "orgCode");
                        orgYearTerm.setOrgCode(orgCode);
                        orgYearTerm.setModifier(userInfoVo.getUserName());
                        orgYearTerm.setModifyIP(getIp());
                        orgYearTerm.setModifyPgm("platformYearService");
                        orgYearTerm.setSectionCode(yearTerm.getSectionCode());
                        orgYearTerm.setEndDate(yearTerm.getEndDate());
                        orgYearTerm.setStartDate(yearTerm.getStartDate());
                        orgYearTerm.setOrgYearTermCode(orgCode + yearTerm.getYearTermCode());
                        orgYearTerm.setGid(UUID.randomUUID().toString());
                        orgYearTerm.setModifyTime(new Timestamp(new Date().getTime()));
                        ShareOrgYearTerm existOrgYearTerm = shareOrgYearTermDao.getTerm(yearTerm.getYearTermCode(),
                                        orgCode, yearTerm.getSectionCode());
                        if (ObjectUtils.isNull(existOrgYearTerm)) {
                            shareOrgYearTermDao.updateOrAddYearTerm(orgYearTerm);
                        }
                    }
                }
            }
        } else {
            //校验平台学年学期修改
            Map<String, Object> lastYearTerm = sharePlatformYearTermDao.selectLastOrNextYearTermBySectionCode(
                            yearTerm.getSectionCode(), yearTerm.getYearTermCode(), 1);
            Map<String, Object> nextYearTerm = sharePlatformYearTermDao.selectLastOrNextYearTermBySectionCode(
                            yearTerm.getSectionCode(), yearTerm.getYearTermCode(), 2);
            if(MapUtils.isNotEmpty(lastYearTerm)){
                String lastStartDate=MapUtils.getString(lastYearTerm, "startDate");
                String lastEndDate=MapUtils.getString(lastYearTerm, "endDate");
                try {
                    long lastStartTime=sdf.parse(lastStartDate).getTime();
                    long lastEndTime=sdf.parse(lastEndDate).getTime()+24*3600*1000;//结束时间往后推一天
                    if (!(yearTerm.getStartDate().getTime() > lastStartTime
                                    && yearTerm.getStartDate().getTime() <= lastEndTime
                                    && yearTerm.getEndDate().getTime() > lastEndTime)) {
                        return ResultCodeVo.errorCode("当前修改的学期与上学期的开始结束时间不对应，修改失败！");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(MapUtils.isNotEmpty(nextYearTerm)){
                String nextStartDate=MapUtils.getString(nextYearTerm, "startDate");
                String nextEndDate=MapUtils.getString(nextYearTerm, "endDate");
                try {
                    long nextStartTime=sdf.parse(nextStartDate).getTime()-24*3600*1000;//开始时间往前推一天
                    long nextEndTime=sdf.parse(nextEndDate).getTime();
                    if (!(yearTerm.getEndDate().getTime() < nextEndTime
                                    && yearTerm.getEndDate().getTime() >= nextStartTime
                                    && yearTerm.getStartDate().getTime() < nextStartTime)) {
                        return ResultCodeVo.errorCode("当前修改的学期与下学期的开始结束时间不对应，修改失败！");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            // 修改机构学年学期
            shareOrgYearTermDao.updateOrgYearTerm(yearTerm.getYearTermCode(), yearTerm.getSectionCode(),
                            yearTerm.getStartDate(), yearTerm.getEndDate(), yearTerm.getTotalWeekNum());
        }
        int count = sharePlatformYearTermDao.updateOrAddYearTerm(yearTerm);
        // 将平台信息写入Cookie
        BeanHelper.writePlatformToCookie(UUID.randomUUID().toString());
        if (count > 0) {
            return ResultCodeVo.rightCode("保存成功！");
        } else {
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 
     * (根据学段查询学年学期列表)<br>
     * 
     * @param sectionCodeStr 学段编码
     * @return 学年学期列表
     */
    public List<Map<String, Object>> selectYearTerm(String sectionCodeStr) {
        return sharePlatformYearTermDao.selectPlatformList(sectionCodeStr);
    }

    /**
     * 删除平台学年学期 <br>
     * 
     * @param gid
     * @return
     */
    public ResultCodeVo deleteYearTerm(String gid) {
        SharePlatformYearTerm platformYearTerm = sharePlatformYearTermDao.getPlatformYearTermById(gid);
        if (ObjectUtils.isNull(platformYearTerm)){
            return ResultCodeVo.errorCode("没有查询到学年学期！");
        }else{
            //生成当前日期，不带时分秒
            String date = DateUtils.getDate("yyyy-MM-dd");
            long currentTime=DateUtils.getDateTime(date, "yyyy-MM-dd").getTime();
            long startTime=platformYearTerm.getStartDate().getTime();
            if(startTime<=currentTime){
                return ResultCodeVo.errorCode("当前选中的学年学期已经生效，不能删除！");
            }else{
                int orgCount = shareOrgYearTermDao.deleteOrgYearTerm(platformYearTerm.getYearTermCode(), null, platformYearTerm.getSectionCode());
                int platCount = sharePlatformYearTermDao.deleteYearTerm(gid);
                // 将平台信息写入Cookie
                BeanHelper.writePlatformToCookie(UUID.randomUUID().toString());
                return platCount > 0 && orgCount > 0 ? ResultCodeVo.rightCode("删除成功！") : ResultCodeVo.errorCode("删除失败！");
            }
        }
    }

    /**
     * 获取基准日期 ()<br>
     * 
     * @param date
     * @return
     */
    public String getBaseDate(String date) {
        int dayOfWeek = TimeUtils.getWeekDay(date) + 1;
        if (dayOfWeek == 8) {
            dayOfWeek = 1;
        }
        String dateStr = DateUtils.getSomeDate(date, -1 * (dayOfWeek - 1), "yyyy-MM-dd");
        return dateStr;
    }
}
