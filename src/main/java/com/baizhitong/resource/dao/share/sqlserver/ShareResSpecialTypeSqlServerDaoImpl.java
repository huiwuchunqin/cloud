package com.baizhitong.resource.dao.share.sqlserver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareResSpecialTypeDao;
import com.baizhitong.resource.model.share.ShareResSpecialType;

/**
 * 
 * 特色资源分类DAO实现
 * 
 * @author creator zhangqiang 2016年8月9日 下午2:39:56
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ShareResSpecialTypeSqlServerDaoImpl extends BaseSqlServerDao<ShareResSpecialType>
                implements ShareResSpecialTypeDao {

    /**
     * 
     * (根据当前层数为1级，查询列表数据)<br>
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List<ShareResSpecialType> selectListByLevel1() throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("level", 1);
        qr.andEqual("flagDelete", 0);
        qr.addAscOrder("dispOrder");
        return super.find(qr);
    }

    /**
     * 
     * (根据父节点查询子分类列表)<br>
     * 
     * @param pcode 父编码
     * @return
     * @throws Exception
     */
    @Override
    public List<ShareResSpecialType> selectListByPCode(String pcode) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("pcode", pcode);
        qr.andEqual("flagDelete", 0);
        qr.addAscOrder("dispOrder");
        return super.find(qr);
    }

}
