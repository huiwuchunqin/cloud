package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.share.ShareRelSectionSubject;

public interface ShareRelSectionSubjectDao {
    /**
     * 删除学段学科关系
     * 
     * @param section
     * @author gaow
     * @date:2015年12月15日 下午2:08:31
     */
    public void delSectionSubject(String section);

    /**
     * 保存学段学科关系
     * 
     * @param relSectionSubjectList
     * @author gaow
     * @date:2015年12月15日 下午2:10:42
     */
    public void saveSectionSubjectRef(List<ShareRelSectionSubject> relSectionSubjectList) throws Exception;

    /**
     * 保存学段学科关系
     * 
     * @param relSectionSubject
     * @author gaow
     * @date:2015年12月15日 下午2:10:42
     */
    public void saveSectionSubjectRef(ShareRelSectionSubject relSectionSubjectList) throws Exception;

    /**
     * 
     * (根据学段查询学科)<br>
     * 
     * @param sectionCode
     * @return
     */
    public List<Map<String, Object>> selectListBySectionCode(String sectionCode);
}
