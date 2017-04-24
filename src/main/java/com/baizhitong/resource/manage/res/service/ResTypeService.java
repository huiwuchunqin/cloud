package com.baizhitong.resource.manage.res.service;

import java.util.List;

import com.baizhitong.resource.model.share.ShareResTypeL1;
import com.baizhitong.resource.model.share.ShareResTypeL2;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;

/**
 * 资源类型接口
 * 
 * @author zhangqiang
 * @date 2015年12月17日 上午11:28:46
 */
public interface ResTypeService {

    /**
     * 获取资源类型树状数据
     * 
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 上午11:38:48
     */
    public List<NodeTreeVo> getResTypeTreeData() throws Exception;

    /**
     * 根据类型1查询类型2
     * 
     * @param code 类型1编码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月24日 下午1:14:39
     */
    public List<ShareResTypeL2> getResTypeTwoByType1Code(Integer code) throws Exception;

    /**
     * 查询资源一级分类 ()<br>
     * 
     * @return
     */

    public List<ShareResTypeL1> getResTypeL1();

}
