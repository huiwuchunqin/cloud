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
import com.baizhitong.resource.dao.share.ShareCodeYearTermDao;
import com.baizhitong.resource.dao.share.ShareOrgYearTermDao;
import com.baizhitong.resource.dao.share.SharePlatformYearTermDao;
import com.baizhitong.resource.manage.studyYear.service.IOrgYearTermService;
import com.baizhitong.resource.model.share.ShareCodeYearTerm;
import com.baizhitong.resource.model.share.ShareOrgYearTerm;
import com.baizhitong.resource.model.share.SharePlatformYearTerm;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.DataUtils;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.ObjectUtils;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.TimeUtils;

@Service(value = "orgYearTermService")
public class OrgYearTermServiceImpl extends BaseService implements IOrgYearTermService {
    private @Autowired ShareOrgYearTermDao      shareOrgYearTermDao;
    private @Autowired ShareCodeYearTermDao     studyYearTermDao;
    private @Autowired SharePlatformYearTermDao sharePlatformYearTermDao;

    /*
     * 查询学年学期
     */
    public Page getOrgYearTermList(Map<String, Object> mapList) {
        return shareOrgYearTermDao.getOrgYearTermList(mapList);
    }

    /**
     * 
     * (更新或者新增机构学年学期)<br>
     * @param yearTerm 机构学年学期
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public ResultCodeVo updateOrAddYearTerm(ShareOrgYearTerm yearTerm, String startDate, String endDate) {
        UserInfoVo userInfoVo =getUserInfoVo(); 
        ShareCodeYearTerm studyYearTerm = studyYearTermDao.getCodeYearTerm(yearTerm.getYearTermCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (ObjectUtils.isNotNull(studyYearTerm)) {
            yearTerm.setModifier(userInfoVo.getUserName());
            yearTerm.setModifyIP(getIp());
            yearTerm.setModifyPgm("orgYearTermService"); 
            yearTerm.setModifyTime(new Timestamp(new Date().getTime()));
            yearTerm.setStudyYearCode(studyYearTerm.getYearBegin().toString());
            yearTerm.setSysVer(1);
            yearTerm.setOrgCode(userInfoVo.getOrgCode());
            yearTerm.setOrgYearTermCode(userInfoVo.getOrgCode() + yearTerm.getYearTermCode());
            yearTerm.setTermCode(studyYearTerm.getTermCode());
            String start = getBaseDate(startDate);
            String end = getBaseDate(endDate);
            try {
                yearTerm.setStartDate(new Timestamp(sdf.parse(startDate).getTime()));
                yearTerm.setEndDate(new Timestamp(sdf.parse(endDate).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long totalWeekNum = TimeUtils.getDaysFrom2Dates(end, start, "yyyy-MM-dd") / 7 + 1;
            yearTerm.setTotalWeekNum((int) totalWeekNum);
        }
        if (StringUtils.isEmpty(yearTerm.getGid())) {
            // 新增
            //校验机构学年学期是否可以新增
            Map<String, Object> lastYearTerm = shareOrgYearTermDao.selectLastOrNextYearTermBySectionCode(
                            yearTerm.getYearTermCode(), 1, yearTerm.getOrgCode());
            Map<String, Object> nextYearTerm = shareOrgYearTermDao.selectLastOrNextYearTermBySectionCode(
                            yearTerm.getYearTermCode(), 2, yearTerm.getOrgCode());
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
        } else {
            // 修改
            //校验机构学年学期修改
            Map<String, Object> lastYearTerm = shareOrgYearTermDao.selectLastOrNextYearTermBySectionCode(
                            yearTerm.getYearTermCode(), 1, yearTerm.getOrgCode());
            Map<String, Object> nextYearTerm = shareOrgYearTermDao.selectLastOrNextYearTermBySectionCode(
                            yearTerm.getYearTermCode(), 2, yearTerm.getOrgCode());
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
            yearTerm.setFlagUpdByOrg(CoreConstants.FLAG_UPD_BY_ORG_YES);
        }
        boolean success = shareOrgYearTermDao.updateOrAddYearTerm(yearTerm);
        if (success) {
            return ResultCodeVo.rightCode("保存成功！");
        } else {
            return ResultCodeVo.errorCode("保存失败！");
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

    /**
     * 添加学期 ()<br>
     * 
     * @param gid
     * @return
     */
    public ResultCodeVo addOrgTerm(String gid, String orgCode) {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        if (StringUtils.isEmpty(orgCode)) {
            orgCode = userInfoVo.getOrgCode();
        }
        SharePlatformYearTerm platformYearTerm = sharePlatformYearTermDao.getPlatformYearTermById(gid);
        if (platformYearTerm == null)
            return ResultCodeVo.errorCode("没有查询到平台学期");

        ShareOrgYearTerm exitOrgTerm = shareOrgYearTermDao.getTerm(platformYearTerm.getYearTermCode(), orgCode,
                        platformYearTerm.getSectionCode());
        if (exitOrgTerm != null)
            return ResultCodeVo.errorCode("已经存在");

        ShareOrgYearTerm orgYearTerm = new ShareOrgYearTerm();
        DataUtils.copySimpleObject(platformYearTerm, orgYearTerm);
        orgYearTerm.setOrgCode(orgCode);
        orgYearTerm.setEndDate(platformYearTerm.getEndDate());
        orgYearTerm.setStartDate(platformYearTerm.getStartDate());
        orgYearTerm.setOrgYearTermCode(orgCode + platformYearTerm.getYearTermCode());
        orgYearTerm.setGid(UUID.randomUUID().toString());
        orgYearTerm.setModifyTime(new Timestamp(new Date().getTime()));
        boolean success = shareOrgYearTermDao.updateOrAddYearTerm(orgYearTerm);
        return success ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
    }

    /**
     * 删除机构学年学期<br>
     * 
     * @param gid
     * @return
     */
    public ResultCodeVo deleteOrgYearTerm(String gid) {
        ShareOrgYearTerm orgYearTerm=shareOrgYearTermDao.selectByGid(gid);
        if(ObjectUtils.isNotNull(orgYearTerm)){
            //生成当前日期，不带时分秒
            String date = DateUtils.getDate("yyyy-MM-dd");
            long currentTime=DateUtils.getDateTime(date, "yyyy-MM-dd").getTime();
            long startTime=orgYearTerm.getStartDate().getTime();
            if(startTime<=currentTime){
                return ResultCodeVo.errorCode("当前选中的学年学期已经生效，不能删除！");
            }else{
                int count = shareOrgYearTermDao.deleteYearTerm(gid);
                return count > 0 ? ResultCodeVo.rightCode("删除成功！") : ResultCodeVo.errorCode("删除失败！");
            }
        }else{
            return ResultCodeVo.errorCode("学年学期不存在！");
        }
    }

    /**
     * 得到当前的机构学期 ()<br>
     * 
     * @return
     */
    public ShareOrgYearTerm getCurrentOrgYearTerm() {
       
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        UserInfoVo user =getUserInfoVo();
        String sectionCode = "";
        if (user != null)
            sectionCode = user.getUserSectionCode();
        return shareOrgYearTermDao.getTerm(BeanHelper.getYearTermCode(), companyInfoVo.getOrgCode(), sectionCode);
    }

    /**
     * 查询学年学期列表 ()<br>
     * 
     * @return
     */
    public List<Map<String, Object>> getOrgYearTermList(String orgCode, String sectionCode) {
        return shareOrgYearTermDao.getOrgYearTermList(orgCode, sectionCode);
    }

}
