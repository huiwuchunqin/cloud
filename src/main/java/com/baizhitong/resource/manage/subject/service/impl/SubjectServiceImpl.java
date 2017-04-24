package com.baizhitong.resource.manage.subject.service.impl;

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
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.dao.share.ShareRelSectionSubjectDao;
import com.baizhitong.resource.manage.subject.service.SubjectService;
import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.resource.model.share.ShareRelSectionSubject;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 学科业务接口实现
 * 
 * @author gaow
 * @date:2015年12月14日 下午4:51:53
 */
@Service(value = "subjectService")
public class SubjectServiceImpl extends BaseService implements SubjectService {
    /**** 学科dao ***/
    @Autowired
    ShareCodeSubjectDao       shareCodeSubjectDao;
    /**** 学段学科关系dao ***/
    @Autowired
    ShareRelSectionSubjectDao shareRelSectionSubjectDao;

    /**
     * 分页查询学科信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月14日 下午4:55:00
     */
    public Page<Map<String, Object>> selectSubjectPage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception {
        return shareCodeSubjectDao.selectSubjectPage(param, pageSize, pageNo);
    }

    /**
     * 查询学科列表信息
     * 
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:41:23
     */
    public List<ShareCodeSubjectVo> selectSubjectList(String sectionCode) throws Exception {
        String prefix = "";
        if (StringUtils.isNotEmpty(sectionCode)) {
            prefix = sectionCode.substring(0, 1);
        }
        List<Map<String, Object>> subjectList = shareCodeSubjectDao.selectSubjectWithPrefix(prefix);
        ShareCodeSubjectVo subjectVo = new ShareCodeSubjectVo();
        return subjectVo.getMapToVoList(subjectList);
    }

    /**
     * 获取资源相关学科
     * 
     * @param resId
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:25:25
     */
    public ShareCodeSubjectVo getResRelateSubject(Integer resId, Integer resTypeL1) throws Exception {
        List<Map<String, Object>> subjectList = shareCodeSubjectDao.getResRelateSubject(resId, resTypeL1);
        ShareCodeSubjectVo subjectVo = new ShareCodeSubjectVo();
        List<ShareCodeSubjectVo> list = subjectVo.getMapToVoList(subjectList);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    /**
     * 保存学科 ()<br>
     * 
     * @param sectionCode 学段编码
     * @param name 学科名称
     * @param description 备注
     * @return
     */
    public ResultCodeVo saveSubject(String sectionCode, String name, String description) {
        List<Map<String, Object>> sameNameSubject = shareCodeSubjectDao.getSameNameSubject(sectionCode, name.trim());
        if (sameNameSubject != null && sameNameSubject.size() > 0)
            return ResultCodeVo.errorCode("同学段已经存在同名的学科");
        try {
           
            UserInfoVo userInfoVo =getUserInfoVo();
            String modifyIP = getIp();
            /*********** 学科 ************/
            ShareCodeSubject subject = new ShareCodeSubject();
            subject.setGid(UUID.randomUUID().toString());
            subject.setName(name);
            subject.setFlagSysSubject(0);
            subject.setDescription(description);
            subject.setModifyIP(modifyIP);
            subject.setModifyPgm("subjectService");
            subject.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            subject.setSysVer(0);
            subject.setFlagDelete(0);
            String subjectCode = getNextCode(sectionCode);
            Integer dispOrder = shareCodeSubjectDao.getNextDispOrder();
            if (StringUtils.isEmpty(subjectCode))
                return ResultCodeVo.errorCode("该学段的学科数量已经达到上限不能继续添加");
            subject.setCode(subjectCode);
            subject.setDispOrder(dispOrder);
            shareCodeSubjectDao.saveSubject(subject);
            /*********** 学科学段关系 ************/
            ShareRelSectionSubject relSectionSubject = new ShareRelSectionSubject(UUID.randomUUID().toString(),
                            sectionCode, subjectCode, userInfoVo.getUserName(),
                            DateUtils.getTimestamp(DateUtils.formatDate(new Date())), modifyIP, 0);
            shareRelSectionSubjectDao.saveSectionSubjectRef(relSectionSubject);
            return ResultCodeVo.rightCode("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultCodeVo.rightCode("保存失败");
        }

    }

    /**
     * 
     * 查询下个编码值
     * 
     * @param sectionCode
     * @return
     */
    public String getNextCode(String sectionCode) {
        String maxCode = shareCodeSubjectDao.getMaxCode(sectionCode);
        String nextCode = "";
        if (StringUtils.isEmpty(maxCode)) {
            return sectionCode + "70"; // 从70开始
        }
        nextCode = (Integer.parseInt(maxCode) + 1) + "";
        if (!maxCode.substring(0, 1).equals(nextCode.substring(0, 1))) { // 300最多399
                                                                         // 400最多499如果百位升级返回空
            return "";
        } else {
            return nextCode;
        }

    }

}
