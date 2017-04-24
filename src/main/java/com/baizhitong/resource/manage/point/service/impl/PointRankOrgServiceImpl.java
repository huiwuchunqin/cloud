package com.baizhitong.resource.manage.point.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.dao.point.PointRankOrgDao;
import com.baizhitong.resource.dao.point.PointUserStatusDao;
import com.baizhitong.resource.manage.point.service.IPointRankOrgService;
import com.baizhitong.resource.model.point.PointRankOrg;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.TimeUtils;

/**
 * 机构积分头衔接口 PointRankOrgServiceImpl
 * 
 * @author creator BZT 2016年6月24日 下午3:21:46
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointRankOrgServiceImpl extends BaseService implements IPointRankOrgService {

    private @Autowired PointRankOrgDao    pointRankOrgDao;
    /** 用户积分头衔状态dao */
    private @Autowired PointUserStatusDao pointUserStatusDao;

    /**
     * 查询机构积分头衔等级列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPageList(Map<String, Object> param, Integer page, Integer rows) {
        // TODO Auto-generated method stub
       
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        param.put("orgCode", companyInfoVo.getOrgCode());
        return pointRankOrgDao.getPage(param, rows, page);
    }

    /**
     * 根据id查询 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRankOrg getById(Integer id) {
        return pointRankOrgDao.getById(id);
    }

    /**
     * 新增机构头衔等级 ()<br>
     * 
     * @param rank
     * @return
     */
    public ResultCodeVo add(PointRankOrg rank) {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        PointRankOrg entity = pointRankOrgDao.selectByOrgCodeAndTotalPoint(userInfoVo.getOrgCode(),
                        rank.getTotalPoint(), rank.getUserRole());
        if (rank.getId() == null) {// 新增
            long rankCount=pointRankOrgDao.orgRankCount(userInfoVo.getOrgCode(),rank.getUserRole());
            if(rankCount>=10){
                return ResultCodeVo.errorCode("每个角色最多有10个积分头衔！");  
            }
            if (entity != null) {
                return ResultCodeVo.errorCode("已存在相同积分的头衔！");
            }
            
            String maxOrgRankCode = pointRankOrgDao.getMaxCode(userInfoVo.getOrgCode());
            Integer orgRankCode = 1000;
            if (StringUtils.isNotEmpty(maxOrgRankCode)) {
                orgRankCode = Integer.parseInt(maxOrgRankCode.trim()) + 1;
            }
            rank.setBizVersion(Integer.parseInt(TimeUtils.formatDate(new Date(), "YYYYMMDD")));
            rank.setExpireTime(Long.valueOf("99999999999999"));
            rank.setModifier(userInfoVo.getUserName());
            rank.setModifierIP(getIp());
            rank.setRankCode("--");
            rank.setOrgRankCode(orgRankCode.toString());
            rank.setModifyTime(new Timestamp(new Date().getTime()));
            rank.setOrgCode(userInfoVo.getOrgCode());
            rank.setSettingType(CoreConstants.SETTING_TYPE_NOT_EXTEND);
            rank.setStartTime(Long.parseLong(TimeUtils.sysdate("yyyyMMddHHmmss")));
            rank.setSysVersion(1);
            boolean success = pointRankOrgDao.add(rank);
            return success ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
        } else {// 修改
            if (entity != null && rank.getId().intValue() != entity.getId().intValue()) {
                return ResultCodeVo.errorCode("积分不能与其它头衔相同！");
            }
            PointRankOrg old = pointRankOrgDao.getById(rank.getId());
            old.setModifier(userInfoVo.getUserName());
            old.setModifierIP(getIp());
            old.setSettingType(CoreConstants.SETTING_TYPE_NOT_EXTEND);
            old.setModifyTime(new Timestamp(new Date().getTime()));
            old.setUserRole(rank.getUserRole());
            old.setRankName(rank.getRankName());
            old.setGoodAmount(rank.getGoodAmount());
            old.setGoodsName(rank.getGoodsName());
            old.setMemo(rank.getMemo());
            old.setThumbnailJson(rank.getThumbnailJson());
            old.setTotalPoint(rank.getTotalPoint());
            boolean success = pointRankOrgDao.add(old);
            pointUserStatusDao.update(old.getOrgRankCode(), old.getRankName(), old.getOrgCode());
            return success ? ResultCodeVo.rightCode("修改成功") : ResultCodeVo.errorCode("修改失败");
        }
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    public ResultCodeVo delete(Integer id) {
        // TODO Auto-generated method stub
        int count = pointRankOrgDao.delete(id);
        return count > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }
}
