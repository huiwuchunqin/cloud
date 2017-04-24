package com.baizhitong.resource.manage.res.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.dao.res.ResMediaDao;
import com.baizhitong.resource.dao.res.ResMediaSpecialDao;
import com.baizhitong.resource.dao.res.ResSettingsHomeDao;
import com.baizhitong.resource.dao.share.ShareResTypeL2Dao;
import com.baizhitong.resource.manage.res.service.ResSettingsHomeService;
import com.baizhitong.resource.model.res.ResSettingsHome;
import com.baizhitong.resource.model.share.ShareResTypeL2;
import com.baizhitong.utils.DateUtils;

/**
 * 
 * 资源设置-首页显示Service实现
 * 
 * @author creator zhangqiang 2016年7月27日 下午1:33:39
 * @author updater
 *
 * @version 1.0.0
 */
@Service(value = "resSettingsHomeService")
public class ResSettingsHomeServiceImpl extends BaseService implements ResSettingsHomeService {

    private @Autowired ResSettingsHomeDao resSettingsHomeDao;
    private @Autowired ShareResTypeL2Dao  shareResTypeL2Dao;
    private @Autowired ResMediaDao        resMediaDao;
    private @Autowired ResMediaSpecialDao resMediaSpecialDao;

    /**
     * 
     * (分页查询资源设置显示信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> queryResSettingsHomeInfoPage(Map<String, Object> param) throws Exception {
        Page<Map<String, Object>> mapPage = resSettingsHomeDao.selectPage(param);
        if (mapPage != null) {
            List<Map<String, Object>> mapList = mapPage.getRows();
            // 遍历，格式化显示类别
            for (int i = 0; i < mapList.size(); i++) {
                Object obj = mapList.get(i).get("setType");
                if (obj != null) {
                    String setType = obj.toString();
                    if ("0".equals(setType)) {
                        mapList.get(i).put("setTypeName", "顶部横幅");
                    } else if ("1".equals(setType)) {
                        mapList.get(i).put("setTypeName", "最新");
                    } else if ("2".equals(setType)) {
                        mapList.get(i).put("setTypeName", "最热");
                    } else if ("11".equals(setType)) {
                        mapList.get(i).put("setTypeName", "特色资源");
                    } else {
                        ShareResTypeL2 entity = shareResTypeL2Dao.selectByCode(setType);
                        if (entity != null) {
                            mapList.get(i).put("setTypeName", entity.getName());
                        }
                    }
                } else {
                    continue;
                }
            }
            mapPage.setRows(mapList);
        }
        return mapPage;
    }

    /**
     * 
     * (分页查询视频列表信息)<br>
     * 
     * @param param 查询参数
     * @param rows 每页大小
     * @param page 当前页
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> queryMediaPageInfo(Map<String, Object> param, Integer rows, Integer page)
                    throws Exception {
        return resMediaDao.selectPageByResSetting(param, rows, page);
    }

    /**
     * 
     * (添加或修改资源首页设置显示)<br>
     * 
     * @param entity 实体
     * @return
     */
    @Override
    public ResultCodeVo addResSettingsHome(ResSettingsHome entity) {
       
        UserInfoVo userInfo =getUserInfoVo();
        Timestamp timeNow = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        // 新增
        if (null == entity.getId()) {
            ResSettingsHome resSettingsHome = resSettingsHomeDao.selectByResCodeAndSetType(entity.getResCode(),
                            entity.getSetType());
            if (resSettingsHome != null) {
                return ResultCodeVo.errorCode("您已经设置过该视频及对应的显示类别，请勿重复设置！");
            }
            if (CoreConstants.SET_TYPE_HEAD.equals(entity.getSetType())) {
                if (StringUtils.isEmpty(entity.getCoverPath())) {
                    return ResultCodeVo.errorCode("请上传封面图片！");
                }
                if (StringUtils.isEmpty(entity.getThumbnailPath())) {
                    return ResultCodeVo.errorCode("请上传缩略图图片！");
                }
            } else {
                if (StringUtils.isEmpty(entity.getCoverPath())) {
                    return ResultCodeVo.errorCode("请上传封面图片！");
                }
            }
            if (CoreConstants.FLAG_AVAILABLE_YES == entity.getFlagAvailable().intValue()) {
                if (CoreConstants.SET_TYPE_HEAD.equals(entity.getSetType())) {
                    List<ResSettingsHome> headList = resSettingsHomeDao.selectList(entity.getSetType(), "", "");
                    if (headList != null && headList.size() >= 6) {
                        return ResultCodeVo.errorCode("该类型下可以设置显示的资源已达上限，不能继续新增！");
                    }
                } else if (CoreConstants.SET_TYPE_HOT.equals(entity.getSetType())
                                || CoreConstants.SET_TYPE_NEW.equals(entity.getSetType())) {
                    List<ResSettingsHome> list = resSettingsHomeDao.selectList(entity.getSetType(), "", "");
                    if (list != null && list.size() == 1) {
                        return ResultCodeVo.errorCode("该类型下可以设置显示的资源已达上限，不能继续新增！");
                    }
                } else if (CoreConstants.RES_TYPE_SPECIAL_MEDIA.equals(Integer.parseInt(entity.getSetType()))) {
                    List<ResSettingsHome> list = resSettingsHomeDao.selectList(entity.getSetType(),
                                    entity.getResSpecialTypeL2());
                    if (list != null && list.size() == 1) {
                        return ResultCodeVo.errorCode("该类型下可以设置显示的资源已达上限，不能继续新增！");
                    }
                } else {
                    List<ResSettingsHome> otherlist = resSettingsHomeDao.selectList(entity.getSetType(),
                                    entity.getSectionCode(), entity.getSubjectCode());
                    if (otherlist != null && otherlist.size() == 1) {
                        return ResultCodeVo.errorCode("该类型下可以设置显示的资源已达上限，不能继续新增！");
                    }
                }
            }
            // 获取当前显示类型下已有的最大排序值
            int dispOrder = resSettingsHomeDao.selectMaxOrderBySetType(entity.getSetType());
            if (dispOrder > 0) {
                // 当前排序值+1
                entity.setDispOrder(dispOrder + 1);
            } else {
                entity.setDispOrder(1);
            }
            entity.setSettingTime(timeNow);
            entity.setSettingUserName(userInfo.getUserName());
            entity.setModifier(userInfo.getUserName());
            entity.setModifyTime(timeNow);
            entity.setModifierIP(getIp());
        } else {
            // 修改
            entity.setModifier(userInfo.getUserName());
            entity.setModifyTime(timeNow);
            entity.setModifierIP(getIp());
        }
        return resSettingsHomeDao.add(entity) ? ResultCodeVo.rightCode("保存成功！") : ResultCodeVo.errorCode("保存失败！");
    }

    /**
     * 
     * 删除首页显示资源
     * 
     * @param id 主键id
     * @return
     */
    @Override
    public int deleteResSettingsHome(String id) throws Exception {
        return resSettingsHomeDao.delete(id);
    }

    /**
     * 
     * (修改资源在首页是否使用)<br>
     * 
     * @param id 主键id
     * @param setType 显示类别
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param flagAvailable 是否使用
     * @return
     * @throws Exception
     */
    @Override
    public ResultCodeVo changeFlagAvailable(String id, String setType, String sectionCode, String subjectCode,
                    Integer flagAvailable, String resSpecialTypeL2) throws Exception {
        int updateCount = 0;
        if (CoreConstants.FLAG_AVAILABLE_YES == flagAvailable.intValue()) {
            if (CoreConstants.SET_TYPE_HEAD.equals(setType)) {
                List<ResSettingsHome> headList = resSettingsHomeDao.selectList(setType, "", "");
                if (headList != null && headList.size() >= 6) {
                    return ResultCodeVo.errorCode("该类型下可以设置显示的资源已达上限，不能继续操作！");
                } else {
                    updateCount = resSettingsHomeDao.updateFlagAvailable(Integer.parseInt(id), flagAvailable);
                    if (1 == updateCount) {
                        return ResultCodeVo.rightCode("设置成使用，操作成功！");
                    } else {
                        return ResultCodeVo.errorCode("设置成使用，操作失败！");
                    }
                }
            } else if (CoreConstants.SET_TYPE_HOT.equals(setType) || CoreConstants.SET_TYPE_NEW.equals(setType)) {
                List<ResSettingsHome> list = resSettingsHomeDao.selectList(setType, "", "");
                if (list != null && list.size() == 1) {
                    return ResultCodeVo.errorCode("该类型下可以设置显示的资源已达上限，不能继续操作！");
                } else {
                    updateCount = resSettingsHomeDao.updateFlagAvailable(Integer.parseInt(id), flagAvailable);
                    if (1 == updateCount) {
                        return ResultCodeVo.rightCode("设置成使用，操作成功！");
                    } else {
                        return ResultCodeVo.errorCode("设置成使用，操作失败！");
                    }
                }
            } else if (CoreConstants.RES_TYPE_SPECIAL_MEDIA.equals(Integer.parseInt(setType))) {
                List<ResSettingsHome> list = resSettingsHomeDao.selectList(setType, resSpecialTypeL2);
                if (list != null && list.size() == 1) {
                    return ResultCodeVo.errorCode("该类型下可以设置显示的资源已达上限，不能继续操作！");
                } else {
                    updateCount = resSettingsHomeDao.updateFlagAvailable(Integer.parseInt(id), flagAvailable);
                    if (1 == updateCount) {
                        return ResultCodeVo.rightCode("设置成使用，操作成功！");
                    } else {
                        return ResultCodeVo.errorCode("设置成使用，操作失败！");
                    }
                }
            } else {
                List<ResSettingsHome> otherlist = resSettingsHomeDao.selectList(setType, sectionCode, subjectCode);
                if (otherlist != null && otherlist.size() == 1) {
                    return ResultCodeVo.errorCode("该类型下可以设置显示的资源已达上限，不能继续操作！");
                } else {
                    updateCount = resSettingsHomeDao.updateFlagAvailable(Integer.parseInt(id), flagAvailable);
                    if (1 == updateCount) {
                        return ResultCodeVo.rightCode("设置成使用，操作成功！");
                    } else {
                        return ResultCodeVo.errorCode("设置成使用，操作失败！");
                    }
                }
            }
        } else {
            updateCount = resSettingsHomeDao.updateFlagAvailable(Integer.parseInt(id), flagAvailable);
            if (1 == updateCount) {
                return ResultCodeVo.rightCode("取消使用，操作成功！");
            } else {
                return ResultCodeVo.errorCode("取消使用，操作失败！");
            }
        }
    }

    /**
     * 
     * (分页查询特色资源列表信息)<br>
     * 
     * @param param
     * @param rows
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> queryMediaSpecialPageInfo(Map<String, Object> param, Integer rows, Integer page)
                    throws Exception {
        return resMediaSpecialDao.selectPageByResSetting(param, rows, page);
    }

}
