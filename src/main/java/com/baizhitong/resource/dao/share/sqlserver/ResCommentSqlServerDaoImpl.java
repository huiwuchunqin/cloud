package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ResCommentDao;
import com.baizhitong.resource.model.res.ResComment;

/**
 * 资源评论接口
 * 
 * @author creator lushunming 2015/12/07
 * @author updater
 */
@Service("resCommentSqlServerDao")
public class ResCommentSqlServerDaoImpl extends BaseSqlServerDao<ResComment> implements ResCommentDao {
    /**
     * 根据资源id和类型来查询资源评论列表
     * 
     * @param resID 资源id
     * @param resTypeL1 资源类型
     * @param pageNo当前页号
     * @param pageSize 页面大小
     * @return
     */
    @Override
    public Page<Map<String, Object>> selectResCommentPage(Integer resID, Integer resTypeL1, Integer pageNo,
                    Integer pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" rc.comment,");
        sql.append(" rc.commentatorCode,");
        sql.append(" rc.commentTime,");
        sql.append(" rc.flagAnonymous,");
        sql.append(" rc.id,");
        sql.append(" rc.repliedCommentID,");
        sql.append(" rc.repliedUserCode,");
        sql.append(" rc.resID,");
        sql.append(" rc.resTypeL1,");
        sql.append(" rc.resTypeL2,");
        sql.append(" csuc.loginAccount AS commentatorName,");
        sql.append(" rsuc.loginAccount AS repliedName,");
        sql.append(" csuc.userName AS commentatorUserName,");
        sql.append(" rsuc.userName AS repliedUserName,");
        sql.append(" csuc.avatarsImg AS commentatorAvatarsImg,");
        sql.append(" rsuc.avatarsImg AS repliedAvatarsImg");
        sql.append(" FROM");
        sql.append(" res_comment rc");
        sql.append(" LEFT JOIN share_user_common csuc ON rc.commentatorCode = csuc.userCode");
        sql.append(" LEFT JOIN share_user_common rsuc ON rc.repliedUserCode = rsuc.userCode");

        sql.append(" where");
        sql.append("  	(rc.repliedCommentID IS NULL OR rc.repliedCommentID='') ");

        if (resID != null) {
            param.put("resid", resID);
            sql.append(" AND rc.resID = :resid");
        }
        if (null != resTypeL1) {
            param.put("restypel1", resTypeL1);
            sql.append(" AND rc.resTypeL1 = :restypel1");
        }
        sql.append(" order by");
        sql.append(" commentTime ASC");
        Page<Map<String, Object>> page = super.queryDistinctPage(sql.toString(), param, pageNo, pageSize);
        return page;
    }

    /**
     * 保存资源评论
     * 
     * @param resComment 资源评论对象
     * @return 是否成功
     */
    @Override
    public boolean saveResComment(ResComment resComment) {
        boolean success = false;
        try {
            success = super.saveOne(resComment);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 根据资源id和类型,repliedCommentID来查询资源评论
     * 
     * @param resID 资源id
     * @param resTypeL1 资源类型
     * @param repliedCommentID 所回复的评论id
     * @return list集合
     */
    @Override
    public List<Map<String, Object>> selectResCommentList(Integer resID, Integer resTypeL1, Integer repliedCommentID) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" rrc.flagAnonymous AS repFlagAnonymous, ");
        sql.append(" rc.comment,");
        sql.append(" rc.commentatorCode,");
        sql.append(" rc.commentTime,");
        sql.append(" rc.flagAnonymous,");
        sql.append(" rc.id,");
        sql.append(" rc.repliedCommentID,");
        sql.append(" rc.repliedUserCode,");
        sql.append(" rc.resID,");
        sql.append(" rc.resTypeL1,");
        sql.append(" rc.resTypeL2,");
        sql.append(" csuc.loginAccount AS commentatorName,");
        sql.append(" rsuc.loginAccount AS repliedName,");
        sql.append(" csuc.userName AS commentatorUserName,");
        sql.append(" rsuc.userName AS repliedUserName,");
        sql.append(" csuc.avatarsImg AS commentatorAvatarsImg,");
        sql.append(" rsuc.avatarsImg AS repliedAvatarsImg");
        sql.append(" FROM");
        sql.append(" res_comment rc");
        sql.append(" LEFT JOIN share_user_common csuc ON rc.commentatorCode = csuc.userCode");
        sql.append(" LEFT JOIN share_user_common rsuc ON rc.repliedUserCode = rsuc.userCode");
        sql.append(" LEFT JOIN res_comment rrc ON (");
        sql.append(" rrc.commentatorCode = rc.repliedUserCode");
        sql.append(" AND rrc.id = rc.repliedCommentID");
        sql.append(" )");
        sql.append(" where");
        if (resID != null) {
            param.put("resid", resID);
            sql.append(" rc.resID = :resid");
        }
        if (null != resTypeL1) {
            param.put("restypel1", resTypeL1);
            sql.append(" AND rc.resTypeL1 = :restypel1");
        }
        if (repliedCommentID != null) {
            param.put("repliedcommentid", repliedCommentID);
            sql.append(" AND rc.repliedCommentID = :repliedcommentid");
        }
        sql.append(" order by");
        sql.append(" commentTime ASC");
        return super.findBySql(sql.toString(), param);
    }
}
