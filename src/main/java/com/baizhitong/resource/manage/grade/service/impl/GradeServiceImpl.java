package com.baizhitong.resource.manage.grade.service.impl;

import java.util.List;
import java.util.Map;

import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.UpdateIndexCommand;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.dao.share.ShareCodeGradeDao;
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;
import com.baizhitong.utils.StringUtils;

/**
 * 年级业务接口
 * 
 * @author gaow
 * @date:2015年12月21日 上午9:53:20
 */
@Service(value = "gradeService")
public class GradeServiceImpl implements GradeService {
    private @Autowired ShareCodeGradeDao shareCodeGradeDao;

    /**
     * 分页查询年级信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月14日 下午4:55:00
     */
    public Page<Map<String, Object>> selectGradePage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception {
        return shareCodeGradeDao.selectGradePage(param, pageSize, pageNo);
    }

    /**
     * 查询年级列表信息
     * 
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 上午11:41:23
     */
    public List<ShareCodeGradeVo> selectGradeList(String sectionCode) throws Exception {
        String prefix = "";
        if (StringUtils.isNotEmpty(sectionCode)) {
            prefix = sectionCode.substring(0, 1);
        }
        List<Map<String, Object>> gradeList = shareCodeGradeDao.selectGradeWithPrefix(prefix);
        ShareCodeGradeVo gradeVo = new ShareCodeGradeVo();
        return gradeVo.getMapToVoList(gradeList);
    }

    /**
     * 获取年级相关资源
     * 
     * @param resId 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:23:28
     */
    public ShareCodeGradeVo getResRelateGrade(Integer resId, Integer resTypeL1) throws Exception {
        List<Map<String, Object>> gradeList = shareCodeGradeDao.getResRelateGrade(resId, resTypeL1);
        ShareCodeGradeVo gradeVo = new ShareCodeGradeVo();
        List<ShareCodeGradeVo> list = gradeVo.getMapToVoList(gradeList);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    /**
     * 查询学科年级 ()<br>
     * 
     * @param subjectCode
     * @return
     * @throws Exception
     */
    public List<ShareCodeGradeVo> getSubjectGrade(String subjectCode) throws Exception {
        List<Map<String, Object>> gradeList = shareCodeGradeDao.getSubjectGrade(subjectCode);
        ShareCodeGradeVo gradeVo = new ShareCodeGradeVo();
        return gradeVo.getMapToVoList(gradeList);
    }
}
