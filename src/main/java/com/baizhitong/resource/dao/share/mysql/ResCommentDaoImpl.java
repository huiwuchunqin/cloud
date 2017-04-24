package com.baizhitong.resource.dao.share.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.share.ResCommentDao;
import com.baizhitong.resource.model.res.LogResOperate;
import com.baizhitong.resource.model.res.ResComment;
import com.baizhitong.utils.StringUtils;

/**
 * 资源评论接口
 * 
 * @author creator lushunming 2015/12/07
 * @author updater
 */
@Service("resCommentDao")
public class ResCommentDaoImpl extends BaseMySqlDao<ResComment, Integer> implements ResCommentDao {

    @Override
    public Page<Map<String, Object>> selectResCommentPage(Integer resID, Integer resTypeL1, Integer pageNo,
                    Integer pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean saveResComment(ResComment resComment) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Map<String, Object>> selectResCommentList(Integer resID, Integer resTypeL1, Integer repliedCommentID) {
        // TODO Auto-generated method stub
        return null;
    }

}
