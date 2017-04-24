package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareKnowledgeSerialDao;
import com.baizhitong.resource.model.share.ShareKnowledgePointSerial;
import com.baizhitong.utils.StringUtils;

@Service
public class ShareKnowledgeSerialDaoImpl extends BaseSqlServerDao<ShareKnowledgePointSerial>
                implements ShareKnowledgeSerialDao {
    /**
     * 
     * 保存知识点体系
     * 
     * @param serial
     * @throws Exception
     */
    public boolean saveKnowledgeSerial(ShareKnowledgePointSerial serial) throws Exception {
        return super.saveOne(serial);
    }

    /**
     * 查询知识点体系
     * 
     * @param gid
     * @return
     * @throws Exception
     */
    public ShareKnowledgePointSerial getKnowledgeSerial(String gid) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gid", gid);
        return super.get(param);
    }

    /**
     * 
     * 查询知识体系
     * 
     * @param codes 体系编码
     * @return
     * @throws Exception
     */
    public List<ShareKnowledgePointSerial> getKnowledgeSerialList(List<String> codes) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        if (codes != null && codes.size() > 0) {
            qr.andIn("code", codes);
        }
        qr.andEqual("flagDelete", 0);
        qr.addAscOrder("sectionCode");
        qr.addAscOrder("subjectCode");
        return super.find(qr);
    }

    @Override
    public List<ShareKnowledgePointSerial> getSameNameKnowledgeSerialList(String name, String subjectCode,
                    String sectionCode, String gid) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("subjectCode", subjectCode);
        qr.andEqual("name", name);
        qr.andEqual("flagDelete", 0);
        if (StringUtils.isNotEmpty(gid)) {
            qr.andNotEqual("gid", gid);
        }
        return super.find(qr);
    }

    /**
     * 根据学科查知识体系 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    public List<ShareKnowledgePointSerial> getKnowledgePointSerialBySubjectCode(String subjectCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("subjectCode", subjectCode);
        qr.andEqual("flagDelete", 0);
        return super.find(qr);
    }
}
