package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import org.apache.poi.poifs.storage.ListManagedBlock;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareCodeTextbookVer;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.share.ShareCodeTextbookVerVo;

/**
 * 教材版本数据接口
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public interface ShareCodeTextbookVerDao {

    /**
     * 获取教材版本集合
     * 
     * @param subjectCode 学科编码
     * @return 集合
     * @throws Exception 异常
     */
    public List<ShareCodeTextbookVer> selectTextbookVerList(String subjectCode) throws Exception;

    /************************************************** |以上已确认| **************************************************/
    /**
     * 查询某些学科的教材版本
     * 
     * @param subjectCodes
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月17日 下午6:47:16
     */
    public List<ShareCodeTextbookVer> selectTextbookInSubjects(List<String> subjectCodes) throws Exception;

    /**
     * 获取教材版本分页信息
     * 
     * @param subjectCode 学科编码
     * @param name 教材版本名称
     * @param pageSize 页面大小
     * @param pageNo 页码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月14日 下午5:40:28
     */
    public Page<ShareCodeTextbookVerVo> queryTextbookVerPageInfo(String sectionCode, String subjectCode, String name,
                    Integer pageSize, Integer pageNo) throws Exception;

    /**
     * 获取资源相关章节
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:00:04
     */
    public List<Map<String, Object>> getResRelateVersion(Integer resId);

    /**
     * 查询教材版本 ()<br>
     * 
     * @param name
     * @return
     */
    public List<Map<String, Object>> getTextbookVerByName(String name);

    /**
     * 查询教材版本 ()<br>
     * 
     * @param textbookVerCode
     * @return
     */
    public ShareCodeTextbookVer getTextbookVer(String textbookVerCode);

    /**
     * 查询最大编码 ()<br>
     * 
     * @return
     */
    public String getMaxCode();

    /**
     * 保存教材版本列表 ()<br>
     * 
     * @param list
     */
    public void saveTextbookVer(List<ShareCodeTextbookVer> list);

    /**
     * 保存教材版本 ()<br>
     * 
     * @param textbookVer
     */
    public void save(ShareCodeTextbookVer textbookVer);

    /**
     * 查询教材版本 ()<br>
     * 
     * @param name
     * @param sectionCode
     * @param subjectCode
     * @return
     */
    public List<Map<String, Object>> getTextbookVerName(String name, String sectionCode, String subjectCode);

    /**
     * 更新版本名称 ()<br>
     * 
     * @param name
     * @param gid
     */
    public void update(String name, String gid);

    /**
     * 删除 ()<br>
     * 
     * @param gid 1,2,3,4
     */
    public int delete(String gid);

    /**
     * 删除 ()<br>
     * 
     * @param name
     * @return
     */
    public int deletebyName(String name);

    /**
     * 查询某学科教材版本 ()<br>
     * 
     * @param subjectCode
     * @param name
     * @return
     */
    public Map<String, Object> selectSubjectVersion(String subjectCode, String name);

    /**
     * 查询 ()<br>
     * 
     * @param gid
     * @return
     */
    public ShareCodeTextbookVer get(String gid);
}
