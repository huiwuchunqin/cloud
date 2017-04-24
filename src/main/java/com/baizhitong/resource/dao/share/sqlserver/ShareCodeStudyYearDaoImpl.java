package com.baizhitong.resource.dao.share.sqlserver;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareCodeStudyYearDao;
import com.baizhitong.resource.model.share.ShareCodeStudyYear;

/**
 * 学年dao实现类 ShareCodeStudyYearDaoImpl TODO
 * 
 * @author creator BZT 2016年4月28日 下午5:22:53
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class ShareCodeStudyYearDaoImpl extends BaseSqlServerDao<ShareCodeStudyYear> implements ShareCodeStudyYearDao {

    /**
     * 查询学年列表 ()<br>
     * 
     * @param param
     * @return
     */
    public List<ShareCodeStudyYear> getStudyYearList(Map<String, Object> param) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 查询学年分页信息 ()<br>
     * 
     * @param param
     * @return
     */
    public Page getStudyYearPage(Map<String, Object> param) {
        Integer pageSize = MapUtils.getInteger(param, "rows");
        Integer pageNumber = MapUtils.getInteger(param, "page");
        QueryRule qr = QueryRule.getInstance();
        qr.addAscOrder("studyYearCode");
        return super.find(qr, pageNumber, pageSize);
    }

    /**
     * 
     * 保存学年
     * 
     * @param shareCodeStudyYear
     * @return
     */
    public boolean addStudyYear(ShareCodeStudyYear shareCodeStudyYear) {
        // TODO Auto-generated method stub
        try {
            return super.saveOne(shareCodeStudyYear);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询学年 ()<br>
     * 
     * @param yearCode
     * @return
     */
    public ShareCodeStudyYear getYear(String yearCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("studyYearCode", yearCode);
        return super.findUnique(qr);
    }

    /**
     * 删除学年 ()<br>
     * 
     * @param studyYearCode
     */
    public void deleteStudyYear(String studyYearCode) {
        String sql = "delete from share_code_study_year where studyYearCode=?";
        super.update(sql, studyYearCode);
    }

    /**
     * 
     * (查询所有学年列表)<br>
     * 
     * @return
     */
    @Override
    public List<ShareCodeStudyYear> selectList() {
        QueryRule qr = QueryRule.getInstance();
        qr.addAscOrder("dispOrder");
        return super.find(qr);
    }
}
