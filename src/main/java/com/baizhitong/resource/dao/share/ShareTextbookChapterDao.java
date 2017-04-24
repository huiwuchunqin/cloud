package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.share.ShareTextbookChapterVo;

/**
 * 教材章节树数据接口
 * 
 * @author creator Cuidc 2015/12/10
 * @author updater
 */
public interface ShareTextbookChapterDao {
    /**
     * 获取教材章节树集合
     * 
     * @param subjectCode 学科编码
     * @param textbookCode 教材版本编码
     * @param pcode 父章节编号
     * @return 集合
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectTextbookChapterList(String subjectCode, String textbookCode, String pcode)
                    throws Exception;
    /************************************************** |以上已确认| **************************************************/

    /**
     * 获取根教材章节
     * 
     * @param subjectCode 学科编码 111,222,333,444
     * @param versionCode 版本编码 111,222,33,44
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月17日 下午8:13:05
     */
    public List<Map<String, Object>> getVersionRootInSubjectAndVersion(String subjectCodes, String versionCodes)
                    throws Exception;

    /**
     * 获取教材章节内容节点
     * 
     * @param pcCode 父节点
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月18日 上午10:01:37
     */
    public List<Map<String, Object>> getContentVersion(String pcCode) throws Exception;

    /**
     * 获取资源相关章节 ()<br>
     * 
     * @param resId 资源id
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param textbookVerCode 教材版本编码
     * @param tbCode 教材编码
     * @return
     */
    public List<Map<String, Object>> getResRelateChapter(Integer resId, String sectionCode, String subjectCode,
                    String gradeCode, String textbookVerCode, String tbCode, Integer resTypeL1);

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
     * 通过code查询章节
     * 
     * @param code
     * @return
     * @author gaow
     * @date:2015年12月24日 下午4:40:03
     */
    public ShareTextbookChapter getByCode(String code);

    /**
     * 
     * 根据父章节编号获取教材章节列表信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param pcode 父章节编号 @return 教材章节列表信息 @throws Exception @exception
     */
    public List<ShareTextbookChapter> getListInfoByPcode(String pcode, String chapterName) throws Exception;

    /**
     * 
     * 删除教材章节<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param code 章节编号 @param subjectCode 学科编码 @param textbookCode 教材版本编码 @return 删除是否成功 @throws
     *        Exception @exception
     */
    public int deleteTextbookChapter(String code, String subjectCode, String textbookCode) throws Exception;

    /**
     * 
     * 根据相关查询条件获取教材章节列表信息<br>
     * 按显示顺序降序排序 @param pcode 父章节编码 @return 教材章节列表信息 @throws Exception @exception
     */
    public List<ShareTextbookChapter> getListInfo(String pcode);

    /**
     * 
     * 添加教材章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param chapter 教材章节信息 @return 新增是否成功 @throws Exception @exception
     */
    public boolean addChapterInfo(ShareTextbookChapter chapter) throws Exception;

    /**
     * 
     * 根据相关条件查询所有父章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param subjectCode 学科编码 @param textbookCode 教材版本编码 @return 教材章节列表信息 @throws
     *        Exception @exception
     */
    public List<Map<String, Object>> getListInfo(String subjectCode, String textbookCode) throws Exception;

    /**
     * 
     * 根据相关查询条件获取教材章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param subjectCode 学科编码 @param textbookCode 教材版本编码 @param code 章节编码 @return 教材章节信息 @throws
     *        Exception @exception
     */
    public ShareTextbookChapter getChapter(String subjectCode, String textbookCode, String code) throws Exception;

    /**
     * 
     * 根据系统ID获取教材章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param gid 系统ID @return 教材章节信息 @throws Exception @exception
     */
    public ShareTextbookChapter getChapterByGid(String gid) throws Exception;

    /**
     * 
     * 根据教材查询教材章节
     * 
     * @param tbCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapter> getChapterList(String tbCode) throws Exception;

    /**
     * 
     * 根据顶级教材查询教材章节
     * 
     * @param tbCode 教材编码
     * @param chapterName 教材章节名称
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapter> getTopChapterList(String tbCode, String chapterName) throws Exception;

    /**
     * 查询相同名称的教材 ()<br>
     * 
     * @param name 名称
     * @param tbCode 教材编码
     * @param pcode 父节点编码
     * @param gid 修改的主键非修改则为null
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapter> getSameNameChapterList(String name, String tbCode, String pcode, String gid)
                    throws Exception;

    public List<ShareTextbookChapter> getNotTopChapterList(String textbookCode) throws Exception;

    /**
     * 查询兄弟章节 ()<br>
     * 
     * @param gid 主键
     * @param textbookCode 教材
     * @return
     */
    public List<Map<String, Object>> getTextBookSiblingList(String textbookCode, String gid);

    /**
     * 更新排序 ()<br>
     * 
     * @param gid 主键
     * @param newOrder 新的顺序
     */
    public void updateDispOrder(String gid, Integer newOrder);

    /**
     * 查询一个节点的所有子节点(包括自己) ()<br>
     * 
     * @param gid 主键
     * @param newOrder 新的顺序
     */
    public List<ShareTextbookChapter> getAllChildNode(String code);
}
