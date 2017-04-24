package com.baizhitong.resource.dao.share.sqlserver;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ISysMessageDao;
import com.baizhitong.resource.model.share.SysMessage;
/**
 * 查询消息是否被处理过
	* SysMessageDaoImpl TODO
	* 
	* @author creator gaow 2017年3月28日 上午10:58:46
	* @author updater 
	*
	* @version 1.0.0
 */
@Service
public class SysMessageDaoImpl extends BaseSqlServerDao<SysMessage> implements  ISysMessageDao{
    /**
     * ()<br>
     * @param messageType
     * @param messageId
     * @return
     */
    @Override
    public SysMessage getMessage(String messageType,String messageId){
        QueryRule qr=QueryRule.getInstance();
        qr.andEqual("messageType",messageType);
        qr.andEqual("messageId",messageId);
        return super.findUnique(qr);
    }
    
    /**
     * ()<br>
     * @param sysMessage
     */
    @Override
    public void insert(SysMessage sysMessage){
       try {
        super.saveOne(sysMessage) ;
    } catch (IllegalArgumentException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
}
