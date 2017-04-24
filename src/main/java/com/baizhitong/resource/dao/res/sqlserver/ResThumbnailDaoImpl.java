package com.baizhitong.resource.dao.res.sqlserver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.res.ResThumbnailDao;
import com.baizhitong.resource.model.res.ResThumbnail;

@Component
public class ResThumbnailDaoImpl extends BaseSqlServerDao<ResThumbnail> implements ResThumbnailDao {
    /**
     * 查询资源缩略图 ()<br>
     * 
     * @param resCode
     * @return
     */
    public List<ResThumbnail> getThumbnailList(String resCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("resCode", resCode);
        qr.addAscOrder("dispOrder");
        return super.find(qr);
    }

}
