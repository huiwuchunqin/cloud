package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.share.ShareKnowledgePoint;

/**
 * 教材知识点数据接口
 * 
 * @author creator Cuidc 2015/12/10
 * @author updater
 */
public interface ShareKnowledgePointDao {
    /**
     * 获取教材知识点集合
     * 
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param bizTypeNo 教材内分类
     * @param pcode 知识点父节点编码
     * @return 集合
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectKnowledgePointTextbookList(String subjectCode, String textbookVerCode,
                    Integer bizTypeNo, String pcode) throws Exception;

    /************************************************** |以上已确认| **************************************************/

    /**
     * 获取教材知识点集合
     * 
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param gradeCode 年级编码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月16日 上午9:36:57
     */
    public List<Map<String, Object>> selectKnowledgePointTextbookList(String gradeCode, String subjectCode,
                    String textbookVerCode) throws Exception;

    /**
     * 获取根知识点
     * 
     * @param subjectCode 学科编码 111,222,333,444
     * @param versionCode 版本编码 111,222,33,44
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月17日 下午8:13:05
     */
    public List<Map<String, Object>> getKnowledgeRootInSubjectAndVersion(String subjectCodes, String versionCodes)
                    throws Exception;

    /**
     * 获取知识点内容节点
     * 
     * @param pcCode
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月18日 上午10:01:37
     */
    public List<ShareKnowledgePoint> getContentKnowLedge(String pcCode) throws Exception;

    /**
     * 获取资源相关知识点
     * 
     * @param resId
     * @param sectionCode
     * @param subejctCode
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:00:04
     */
    public List<Map<String, Object>> getResRelateKnowLedge(Integer resId, String sectionCode, String subejctCode,
                    Integer resTypeL1);

    /**
     * 查询子节点数
     * 
     * @param code 父节点code
     * @return
     * @author gaow
     * @date:2015年12月24日 下午3:36:33
     */
    public Map<String, Object> getChildCount(String code);

    /**
     * 
     * @param code
     * @return
     * @author gaow
     * @date:2015年12月24日 下午4:35:03
     */
    public ShareKnowledgePoint getByCode(String code);

    /**
     * 保存知识点信息
     * 
     * @param knowledge
     * 
     * @exception
     */
    public void saveShareKnowledgePoint(ShareKnowledgePoint knowledge) throws Exception;

    /**
     * 删除知识点信息
     * 
     * @param gid
     * @throws Exception
     */
    public void delKnowLedge(String gid) throws Exception;

    /**
     * 根据gid查询知识点信息
     * 
     * @param gid
     * @return
     * @throws Exception
     */
    public ShareKnowledgePoint getByGid(String gid) throws Exception;

    /**
     * 查询code最大的同级节点 @param code @return
     * 
     * @exception
     */
    public ShareKnowledgePoint getMaxCodeShareKnowLedgePointTextBook(String code);

    /**
     * 
     * 查询知识点信息
     * 
     * @param kpSerialCode
     * @return
     */
    public List<ShareKnowledgePoint> getKnowledgeList(List<String> kpSerialCode);

    /**
     * 
     * 查询顶级知识点信息
     * 
     * @param kpSerialCode
     * @return
     */
    public List<ShareKnowledgePoint> getTopKnowledgeList(List<String> kpSerialCode);

    /**
     * 
     * 查询顶级知识点信息
     * 
     * @param kpSerialCode
     * @return
     */
    public List<ShareKnowledgePoint> getNotTopKnowledgeList(List<String> kpSerialCode);

    /**
     * 查询知识点信息
     * 
     * @param kpSerialCode
     * @return
     */
    public List<ShareKnowledgePoint> getKnowledgeList(String kpSerialCode);

    /**
     * 查询知识点信息
     * 
     * @param pcode 父节点
     * @return
     */
    public List<ShareKnowledgePoint> getchildKnowledgeList(String pcode);

    /**
     * 查询相同名称的知识点 ()<br>
     * 
     * @param name 名称
     * @param kpSerialCode 体系
     * @param pcode 父节点编码
     * @param gid 修改的主键 如果不是修改则null
     * @return
     */
    public List<ShareKnowledgePoint> getSameNameKnowledgeList(String name, String kpSerialCode, String pcode,
                    String gid);

    /**
     * 查询相邻的节点 ()<br>
     * 
     * @param gid 主键
     * @return
     */
    List<Map<String, Object>> getSiblingKnowledgeList(String kpSerialCode, String gid);

    /**
     * 更新排序 ()<br>
     * 
     * @param gid 主键
     * @param newOrder 新的顺序
     */
    public void updateDispOrder(String gid, Integer newOrder);

}
