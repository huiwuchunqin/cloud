package com.baizhitong.resource.dao.share.sqlserver;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareResCheckAdviceDao;
import com.baizhitong.resource.model.share.ShareResCheckAdvice;

/**
 * 资源审核意见dao ShareResCheckAdviceDaoImpl TODO
 * 
 * @author creator gaow 2017年1月13日 下午5:42:14
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class ShareResCheckAdviceDaoImpl extends BaseSqlServerDao<ShareResCheckAdvice>
                implements ShareResCheckAdviceDao {
    /**
     * 资源审核建议 ()<br>
     * 
     * @return
     */
    public List<ShareResCheckAdvice> list() {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("flagAvailable", 1);
        qr.addAscOrder("dispOrder");
        return super.find(qr);
    }

}
