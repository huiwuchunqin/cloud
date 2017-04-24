package com.baizhitong.resource.dao.share.sqlserver;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareCodeYearTermDao;
import com.baizhitong.resource.model.share.ShareCodeYearTerm;

/**
 * 学年学期DAO实现类
 * 
 * @author creator BZT 2016年4月28日 下午5:23:39
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ShareCodeYearTermDaoImpl extends BaseSqlServerDao<ShareCodeYearTerm> implements ShareCodeYearTermDao {

    /**
     * 查询学期列表 ()<br>
     * 
     * @param map
     * @return
     */
    public List<ShareCodeYearTerm> getTermList(Map<String, Object> map) {
        QueryRule qr = QueryRule.getInstance();
        qr.addDescOrder("yearTermCode");
        return super.find(qr);
    }

    /**
     * 查询学期分页列表 ()<br>
     * 
     * @param map
     * @return
     */
    public Page getTermPageList(Map<String, Object> map) {
        Integer pageSize = MapUtils.getInteger(map, "rows");
        Integer pageNumber = MapUtils.getInteger(map, "page");
        QueryRule qr = QueryRule.getInstance();
        qr.addAscOrder("yearTermCode");
        return super.find(qr, pageNumber, pageSize);
    }

    /**
     * 查询学期 ()<br>
     * 
     * @param yearTermCode
     * @return
     */
    public ShareCodeYearTerm getCodeYearTerm(String yearTermCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("yearTermCode", yearTermCode);
        return super.findUnique(qr);
    }

    /*
     * 保存学期
     */
    public boolean addOrUpdateTerm(ShareCodeYearTerm shareCodeYearTerm) {
        try {
            return super.saveOne(shareCodeYearTerm);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /*
     * 删除学期
     */
    public void deleteCodeYearTerm(String gid) {
        String sql = "delete from share_code_year_term where gid=?";
        super.update(sql, gid);
    }

    /*
     * 查询学期
     */
    public ShareCodeYearTerm getCodeYearTermByGid(String gid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("gid", gid);
        return super.findUnique(qr);
    }

}
