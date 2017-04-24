package com.baizhitong.resource.manage.textbook.service;

import java.util.List;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareRelTextbookKps;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;
import com.baizhitong.resource.model.vo.share.ShareKnowledgePointVo;
import com.baizhitong.resource.model.vo.share.ZtreeHelpVo;

/**
 * 教材知识点接口
 * 
 * @author zhangqiang
 * @date 2015年12月15日 下午8:26:05
 */
public interface TextbookKnowledgePointService {

    /**
     * 
     * 查询知识点体系信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编吗
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param textbookCode 教材编码
     * @param termCode 学期编码
     * @return
     * @throws Exception
     */
    public List<NodeTreeVo> getNodeTreeList(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode, String termCode) throws Exception;

    /**
     * 删除知识点
     * 
     * @param gid 知识点id @throws Exception
     * 
     * @exception
     */
    public ResultCodeVo delKnowLedge(String gid) throws Exception;

    /**
     * 根据id查询知识点 @param gid 知识点id @return @throws Exception
     * 
     * @exception
     */
    public ShareKnowledgePointVo getKnowLedgePointTextBook(String gid) throws Exception;

    public List<List<ZtreeHelpVo>> getKnowledgeZtree(Integer resId, Integer resTypeL1) throws Exception;

    /**
     * 
     * 更新知识点 @param name 名称 @param description 描述 @param gid 知识点id @throws Exception
     * 
     * @exception
     */
    public ResultCodeVo updateTextBookKnowLedge(String name, String description, String gid) throws Exception;

    /**
     * 保存知识点
     * 
     * @param knowLedgeVo @throws Exception
     * 
     * @exception
     */
    public ResultCodeVo saveKnowLedge(ShareKnowledgePointVo knowLedgeVo) throws Exception;

    /**
     * 
     * 查询初次加载数据
     * 
     * @param subjectCode 学科编码
     * @param sectionCode 学段编码
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getKnowledgeTreeRoot(String subjectCode, String sectionCode, Integer resId)
                    throws Exception;

    /**
     * 查询所有的 ()<br>
     * 
     * @param subjectCode
     * @param sectionCode
     * @param resId
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getAll(String subjectCode, String sectionCode, Integer resId, String gradeCode,
                    String textbookVerCode, String termCode) throws Exception;

    /**
     * 
     * 查询初次加载数据
     * 
     * @param pcode 父节点编码
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getContentKnowLedge(String pcode) throws Exception;

    /**
     * 得到所有父节点编码
     * 
     * @param code
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月24日 下午4:33:14
     */
    public ZtreeHelpVo getTopParent(ZtreeHelpVo helpVo, String code) throws Exception;

    /**
     * 获取资源相关知识点
     * 
     * @param resId 资源id
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:31:26
     */
    public List<ZtreeHelpVo> getResRelateKnowLedgeZtree(Integer resId, String sectionCode, String subjectCode,
                    Integer resTypeL1) throws Exception;

    /**
     * 查询资源相关知识点的父节点 ()<br>
     * 
     * @param resId
     * @param sectionCode
     * @param subjectCode
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getResRelateKnowLedgeParent(Integer resId, String sectionCode, String subjectCode,
                    Integer resTypeL1) throws Exception;

    /**
     * 根据关系查询 教材知识点关系列表 @param kpSerialName @return @throws Exception
     * 
     * @exception
     */
    public List<ShareRelTextbookKps> getRelTextBookKps(String kpSerialCode) throws Exception;

    /**
     * 查询教材下章节 ()<br>
     * 
     * @param textbookCode
     * @return
     * @throws Exception
     */
    public List<NodeTreeVo> getKnowledgeTopNodeTreeList(String serialCode) throws Exception;

    /**
     * 查询章节子节点 ()<br>
     * 
     * @param textbookCode
     * @return
     * @throws Exception
     */
    public List<NodeTreeVo> getKnowledgeChildNodeTreeList(String code) throws Exception;

    /**
     * 更新顺序 ()<br>
     * 
     * @param gid
     * @param type
     * @param kpSerialCode
     * @return
     */
    public ResultCodeVo updateDispOrder(String kpSerialCode, String gid, Integer type);
}
