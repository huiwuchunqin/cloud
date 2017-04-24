package com.baizhitong.resource.manage.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.dao.share.ShareResTypeL1Dao;
import com.baizhitong.resource.dao.share.ShareResTypeL2Dao;
import com.baizhitong.resource.manage.res.service.ResTypeService;
import com.baizhitong.resource.model.share.ShareResTypeL1;
import com.baizhitong.resource.model.share.ShareResTypeL2;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;

/**
 * 资源类型接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月17日 上午11:31:09
 */
@Service(value = "resTypeService")
public class ResTypeServiceImpl implements ResTypeService {

    /** 资源类别一级分类数据Dao */
    private @Autowired ShareResTypeL1Dao resTypeL1Dao;
    /** 资源类别二级分类数据Dao */
    private @Autowired ShareResTypeL2Dao resTypeL2Dao;

    /**
     * 获取资源类型树状数据
     */
    @Override
    public List<NodeTreeVo> getResTypeTreeData() throws Exception {
        List<NodeTreeVo> nodeTreeList = new ArrayList<NodeTreeVo>();
        // 获取资源类别所有的一级数据
        List<ShareResTypeL1> resTypeL1List = resTypeL1Dao.selectResTypeL1List();
        if (resTypeL1List != null && resTypeL1List.size() > 0) {
            for (ShareResTypeL1 resTypeL1 : resTypeL1List) {
                NodeTreeVo nodeTreeVo = new NodeTreeVo();
                nodeTreeVo.setGid(resTypeL1.getCode().toString());
                nodeTreeVo.setText(resTypeL1.getName());
                nodeTreeVo.setDescription(resTypeL1.getDescription());
                // 获取该一级类别下的所有二级数据
                List<ShareResTypeL2> resTypeL2List = resTypeL2Dao.selectResTypeL2List(resTypeL1.getCode());
                if (resTypeL2List != null && resTypeL2List.size() > 0) {
                    List<NodeTreeVo> childrenList = new ArrayList<NodeTreeVo>();
                    for (ShareResTypeL2 resTypeL2 : resTypeL2List) {
                        NodeTreeVo tree = new NodeTreeVo();
                        tree.setGid(resTypeL2.getCode().toString());
                        tree.setText(resTypeL2.getName());
                        tree.setDescription(resTypeL2.getDescription());
                        childrenList.add(tree);
                    }
                    nodeTreeVo.setChildren(childrenList);
                }
                nodeTreeList.add(nodeTreeVo);
            }
        }
        return nodeTreeList;
    }

    /**
     * 根据类型1查询类型2
     * 
     * @param code 类型1编码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月24日 下午1:14:39
     */
    public List<ShareResTypeL2> getResTypeTwoByType1Code(Integer code) throws Exception {
        return resTypeL2Dao.selectResTypeL2List(code);
    }

    /**
     * 查询资源一级分类 ()<br>
     * 
     * @return
     */

    public List<ShareResTypeL1> getResTypeL1() {
        try {
            return resTypeL1Dao.selectResTypeL1List();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
    }
}
