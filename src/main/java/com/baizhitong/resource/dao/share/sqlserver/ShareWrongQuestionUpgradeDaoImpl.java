package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareWrongQuestionUpgradeDao;

/**
 * 机构学科错题原因 ShareWrongQuestionUpgradeDaoImpl TODO
 * 
 * @author creator gaow 2016年12月1日 下午2:37:56
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class ShareWrongQuestionUpgradeDaoImpl extends BaseSqlServerDao<HashMap>
                implements ShareWrongQuestionUpgradeDao {
    /**
     * ()<br>
     * 
     * @param orgCode
     * @return
     */
    public int insert(String orgCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_wrong_question_upgrade");
        sql.append("        (    gid,    orgCode,    subjectCode,    wrongTypeCode,    serverity,    closeNeedRightTimes,    upgradeNeedWrongTimes,    answerAgainThreshold,    cleanMethodDesc,    memo,    flagDelete,    modifyPgm,    modifier,    modifyTime,    modifyIP   ) SELECT");
        sql.append("            NEWID()");
        sql.append("            ,:orgCode");
        sql.append("            ,s.code");
        sql.append("            ,c.code");
        sql.append("            ,1");
        sql.append("            ,1");
        sql.append("            ,0");
        sql.append("            ,0");
        sql.append("            ,''");
        sql.append("            ,''");
        sql.append("            ,0");
        sql.append("            ,'init'");
        sql.append("            ,'init'");
        sql.append("            ,getdate()");
        sql.append("            ,'init'   ");
        sql.append("        FROM");
        sql.append("            share_code_wrong_type c");
        sql.append("            ,share_code_subject s   ");
        sqlParam.put("orgCode", orgCode);
        return super.update(sql.toString(), sqlParam);

    }

}
