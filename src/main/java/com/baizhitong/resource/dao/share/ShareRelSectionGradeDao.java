package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.share.ShareRelSectionGrade;

public interface ShareRelSectionGradeDao {
    /**
     * 删除学段年级关系
     * 
     * @param section
     * @author gaow
     * @date:2015年12月15日 下午2:08:31
     */
    public void delSectionGrade(String section);

    /**
     * 保存学段年级关系
     * 
     * @param relSectionGradeList
     * @author gaow
     * @date:2015年12月15日 下午2:10:42
     */
    public void saveSectionGradeRef(List<ShareRelSectionGrade> relSectionGradeList) throws Exception;

    /**
     * 
     * (根据学段查询学段年级关系列表)<br>
     * 
     * @param sectionCode
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectGradeListBySectionCode(String sectionCode) throws Exception;
}
