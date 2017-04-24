package com.baizhitong.resource.manage.textbook.service;

import java.util.List;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;
import com.baizhitong.resource.model.vo.share.ShareTextbookChapterVo;
import com.baizhitong.resource.model.vo.share.ZtreeHelpVo;

/**
 * 教材章节接口
 * 
 * @author zhangqiang
 * @date 2015年12月15日 下午1:49:04
 */
public interface TextbookChapterService {

    /**
     * 获取教材章节树信息
     * 
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param textbookCode 教材编码
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月15日 下午1:51:22
     */
    public List<NodeTreeVo> getNodeTreeList(String subjectCode, String textbookVerCode, String textbookCode,
                    String sectionCode, String gradeCode, String termCode) throws Exception;

    /**
     * 获取资源相关章节
     * 
     * @param resId 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:27:20
     */
    public List<ZtreeHelpVo> getResRelateChapterZtree(Integer resId, Integer resTypeL1) throws Exception;

    public List<List<ZtreeHelpVo>> getChapterZtree(Integer resId, Integer resTypeL1) throws Exception;

    /**
     * 
     * 查询首次加载数据
     * 
     * @param resId 资源id
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCodes 教材版本编码
     * @param textbookCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getVersionTreeRoot(Integer resId, String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCodes, String textbookCode) throws Exception;

    /**
     * 查询教材章节内容节点
     * 
     * @param pcCode 父节点编码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月18日 上午10:10:00
     */
    public List<ZtreeHelpVo> getContentVersion(String pcCode) throws Exception;

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
     * 
     * 查询资源相关章节
     * 
     * @param resId 资源id
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param textbookVerCode 教材版本编码
     * @param tbCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getResRelateChapterZtree(Integer resId, String sectionCode, String subjectCode,
                    String gradeCode, String textbookVerCode, String tbCode, Integer resTypeL1) throws Exception;

    /**
     * 查询选中的节点和其父节点
     * 
     * @param resId
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param textbookVerCode
     * @param tbCode
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getResRelateChapterAndParent(Integer resId, String sectionCode, String subjectCode,
                    String gradeCode, String textbookVerCode, String tbCode, Integer resTypeL1) throws Exception;

    /**
     * 
     * 删除教材章节信息<br> (这里描述这个方法适用条件 – 可选)
     * 
     * @param request 请求 @param gid 教材id @return @exception
     */
    public ResultCodeVo deleteChapter(String gid);

    /**
     * 
     * 添加教材章节信息<br> (这里描述这个方法适用条件 – 可选)
     * 
     * @param chapter 教材章节信息 @return @throws Exception @exception
     */
    public ResultCodeVo addChapterInfo(ShareTextbookChapter chapter) throws Exception;

    /**
     * 
     * 根据相关查询条件获取教材章节信息<br> (这里描述这个方法适用条件 – 可选)
     * 
     * @param code 章节编码 @param subjectCode 学科编码 @param textbookCode 教材版本编码 @return @throws
     * Exception @exception
     */
    public ShareTextbookChapterVo getChapterInfo(String code, String subjectCode, String textbookCode) throws Exception;

    /**
     * 查询教材章节
     * 
     * @param gid 教材章节id
     * @return
     * @throws Exception
     */
    public ShareTextbookChapterVo getChapter(String gid) throws Exception;

    /**
     * 
     * 修改教材章节信息<br> (这里描述这个方法适用条件 – 可选)
     * 
     * @param gid 系统ID @param name 章节名称 @param description 章节描述信息 @return @throws
     * Exception @exception
     */
    public ResultCodeVo updateChapterInfo(String gid, String name, String description) throws Exception;

    /**
     * 
     * 根据教材查询教材章节
     * 
     * @param tbCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapterVo> getChapterList(String tbCode) throws Exception;

    public List<ShareTextbookChapter> getList(String tbCode) throws Exception;

    /**
     * 查询教材下章节 ()<br>
     * 
     * @param textbookCode
     * @return
     * @throws Exception
     */
    public List<NodeTreeVo> getChapterTopNodeTreeList(String textbookCode, String chapterName) throws Exception;

    /**
     * 查询章节子节点 ()<br>
     * 
     * @param textbookCode
     * @return
     * @throws Exception
     */
    public List<NodeTreeVo> getChapterChildrenNodeTreeList(String pcode, String chapterName) throws Exception;

    /**
     * 查询所有的节点 ()<br>
     * 
     * @param resId
     * @param sectionCode
     * @param gradeCode
     * @param subjectCode
     * @param textbookVerCode
     * @param textbookCode
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getAll(Integer resId, String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode, String termCode) throws Exception;

    /**
     * 更新顺序 ()<br>
     * 
     * @param gid
     * @param type
     * @param textbookCode
     * @return
     */
    public ResultCodeVo updateDispOrder(String textbookCode, String gid, Integer type);

}
