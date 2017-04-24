package com.baizhitong.resource.manage.textbook.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareCodeTextbookVer;
import com.baizhitong.resource.model.vo.share.ShareCodeTextbookVerVo;
import com.baizhitong.resource.model.vo.share.ShareTextbookVerTreeVo;

/**
 * 教材版本接口
 * 
 * @author zhangqiang
 * @date 2015年12月14日 下午5:09:51
 */
public interface TextbookVersionService {

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
     * @date 2015年12月14日 下午6:54:38
     */
    public Page<ShareCodeTextbookVerVo> queryTextbookVerPageInfo(String sectionCode, String subjectCode, String name,
                    Integer pageSize, Integer pageNo) throws Exception;

    /**
     * 根据学科获取教材版本信息
     * 
     * @param subjectCode
     * @return List集合
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月15日 下午4:34:39
     */
    public List<ShareCodeTextbookVer> getTextbookVersionListBySubjectCode(String subjectCode) throws Exception;

    /**
     * 获取资源相关教材版本
     * 
     * @param resId 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:35:06
     */
    public ShareCodeTextbookVerVo getResRelateVersion(Integer resId) throws Exception;

    /**
     * 查询某些学科的教材版本
     * 
     * @param subjectCodes 学科编码列表
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月17日 下午6:54:45
     */
    public List<ShareCodeTextbookVer> getTextbookVersionInSubject(List<String> subjectCodes) throws Exception;

    /**
     * 保存教材版本 ()<br>
     * 
     * @param addCodes 新增学科编码
     * @param editGid 修改版本id
     * @param delGid 删除版本id
     * @param originalName 原来名称
     * @param name 教材版本名称
     * @return
     */
    public ResultCodeVo addTextbooxVer(String originalName, String[] addCodes, String[] editGid, String[] delGid,
                    String name);

    /**
     * 查询教材版本 ()<br>
     * 
     * @param subjectCode 学科编码
     * @param sectionCode 学段编码
     * @param name 教材版本名称
     * @return
     */
    public List<ShareTextbookVerTreeVo> getTextbookVerTreeVo(String subjectCode, String sectionCode, String name);

    /**
     * 查询教材版本 ()<br>
     * 
     * @param name 教材版本名称
     * @return
     */
    public List<Map<String, Object>> getTextbookVer(String name);

    /**
     * 删除教材版本 ()<br>
     * 
     * @param name
     * @return
     */
    public ResultCodeVo delTextbook(String name);

    /**
     * 删除教材版本 ()<br>
     * 
     * @param gid
     * @return
     */
    public ResultCodeVo delTextbookByGid(String gid);

}
