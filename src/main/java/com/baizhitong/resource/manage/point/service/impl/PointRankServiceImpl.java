package com.baizhitong.resource.manage.point.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.point.PointRankDao;
import com.baizhitong.resource.dao.point.PointRankOrgDao;
import com.baizhitong.resource.dao.point.PointUserStatusDao;
import com.baizhitong.resource.manage.point.service.IPointRankService;
import com.baizhitong.resource.model.point.PointRank;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.TimeUtils;

/**
 * 积分头衔等级 PointRankServiceImpl TODO
 * 
 * @author creator gaow 2016年6月24日 下午1:10:11
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointRankServiceImpl extends BaseService implements IPointRankService {
    /** 积分头衔dao */
    private @Autowired PointRankDao       pointRankDao;
    /** 机构积分头衔dao */
    private @Autowired PointRankOrgDao    pointRankOrgDao;
    /** 用户积分头衔状态dao */
    private @Autowired PointUserStatusDao pointUserStatusDao;

    /**
     * 查询积分头衔等级 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPageList(Map<String, Object> param, Integer page, Integer rows) {
        return pointRankDao.getPage(param, rows, page);
    }

    /**
     * 新增头衔等级 ()<br>
     * 
     * @param rank
     * @return
     */
    public ResultCodeVo add(PointRank rank) {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        List<PointRank> getSamePointList = pointRankDao.getSamePointList(rank.getTotalPoint(), rank.getUserRole());
        if (rank.getId() == null) {
            
            long rankCount=pointRankDao.rankCount(rank.getUserRole());
            if(rankCount>=10){
                return ResultCodeVo.errorCode("每个角色最多有10个积分头衔");
            }
            
            if (getSamePointList != null && getSamePointList.size() > 0) {
                return ResultCodeVo.errorCode("已经存在相同积分的头衔");
            }
            String maxRankCode = pointRankDao.getMaxCode();
            Integer rankCode = 1000;
            if (StringUtils.isNotEmpty(maxRankCode)) {
                rankCode = Integer.parseInt(maxRankCode.trim()) + 1;
            }
            rank.setBizVersion(Integer.parseInt(TimeUtils.formatDate(new Date(), "YYYYMMDD")));
            rank.setDispOrder(0);
            rank.setSysVersion(1);
            rank.setRankCode(rankCode.toString());
            rank.setStartTime(Long.parseLong(TimeUtils.sysdate("yyyyMMddHHmmss")));
            rank.setExpireTime(Long.valueOf("99999999999999"));
            rank.setModifier(userInfoVo.getUserName());
            rank.setModifierIP(getIp());
            rank.setModifyTime(new Timestamp(new Date().getTime()));
            // 插入平台积分头衔等级
            boolean success = pointRankDao.add(rank);
            
            //机构积分头衔每个角色只能有10个
            long orgRankCount=pointRankOrgDao.orgRankCount(userInfoVo.getOrgCode(),rank.getUserRole());
            if(orgRankCount<10){
                // 插入机构积分头衔等级
                 pointRankDao.addOrgPointRank(rank);  
            }
      
            return success ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
        } else {
            if (getSamePointList != null && getSamePointList.size() > 1) {// 必定存在2个以上因为自己也是一个
                return ResultCodeVo.errorCode("已经存在相同积分的头衔");
            }
            PointRank old = pointRankDao.getById(rank.getId());
            old.getModifier();
            old.setModifierIP(getIp());
            old.setModifyTime(new Timestamp(new Date().getTime()));
            old.setUserRole(rank.getUserRole());
            old.setRankName(rank.getRankName());
            old.setMemo(rank.getMemo());
            old.setGoodAmount(rank.getGoodAmount());
            old.setGoodsName(rank.getGoodsName());
            old.setThumbnailJson(rank.getThumbnailJson());
            old.setTotalPoint(rank.getTotalPoint());
            // 修改平台
            boolean success = pointRankDao.add(old);
            // 修改机构
            int count = pointRankDao.updateOrgPointRank(old);
            pointUserStatusDao.platRelateOrgUpdate(old.getRankCode(), old.getRankName());
            return success && count > 0 ? ResultCodeVo.rightCode("修改成功") : ResultCodeVo.errorCode("修改失败");
        }
    }

    /**
     * 查询积分头衔等级 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRank getById(Integer id) {
        return pointRankDao.getById(id);
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    public ResultCodeVo delete(Integer id, String rankCode) {
        // TODO Auto-generated method stub
        int count = pointRankDao.delete(id);
        pointRankOrgDao.deleteOrgRank(rankCode);
        return count > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }
}
