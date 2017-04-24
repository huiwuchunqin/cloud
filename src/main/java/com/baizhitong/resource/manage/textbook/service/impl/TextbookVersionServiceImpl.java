package com.baizhitong.resource.manage.textbook.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.dao.share.ShareCodeTextbookVerDao;
import com.baizhitong.resource.dao.share.ShareTextbookDao;
import com.baizhitong.resource.manage.textbook.service.TextbookVersionService;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.resource.model.share.ShareCodeTextbookVer;
import com.baizhitong.resource.model.vo.share.ShareCodeTextbookVerVo;
import com.baizhitong.resource.model.vo.share.ShareTextbookVerTreeVo;
import com.baizhitong.utils.StringUtils;

/**
 * 教材版本接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月14日 下午5:11:54
 */
@Service("textbookVersionService")
public class TextbookVersionServiceImpl implements TextbookVersionService {
    /** 教材版本 DAO */
    private @Autowired ShareCodeTextbookVerDao textbookVerDao;

    private @Autowired ShareCodeSubjectDao     subjectDao;
    private @Autowired ShareCodeSectionDao     sectionDao;
    private @Autowired ShareTextbookDao        textbookDao;

    /**
     * 获取教材版本分页信息
     */
    @Override
    public Page<ShareCodeTextbookVerVo> queryTextbookVerPageInfo(String sectionCode, String subjectCode, String name,
                    Integer pageSize, Integer pageNo) throws Exception {
        return textbookVerDao.queryTextbookVerPageInfo(sectionCode, subjectCode, name, pageSize, pageNo);
    }

    /**
     * 根据学科获取教材版本信息
     */
    @Override
    public List<ShareCodeTextbookVer> getTextbookVersionListBySubjectCode(String subjectCode) throws Exception {
        return textbookVerDao.selectTextbookVerList(subjectCode);
    }

    /**
     * 查询某些学科的教材版本
     * 
     * @param subjectCodes 学科编码列表
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月17日 下午6:54:45
     */
    public List<ShareCodeTextbookVer> getTextbookVersionInSubject(List<String> subjectCodes) throws Exception {
        return textbookVerDao.selectTextbookInSubjects(subjectCodes);
    }

    /**
     * 获取资源相关教材版本
     * 
     * @param resId 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:35:06
     */
    public ShareCodeTextbookVerVo getResRelateVersion(Integer resId) throws Exception {
        List<Map<String, Object>> list = textbookVerDao.getResRelateVersion(resId);
        List<ShareCodeTextbookVerVo> volist = ShareCodeTextbookVerVo.mapListToVoList(list);
        if (list != null && list.size() > 0)
            return volist.get(0);
        return null;
    }

    /**
     * 获得流水号 ()<br>
     * 
     * @param number
     * @return
     */
    public String getNumberString(String maxCode) {
        String nextCode = "";
        if (StringUtils.isEmpty(maxCode)) {
            nextCode = "A001";
        } else {
            String charCode = maxCode.substring(0, 1);
            int number = Integer.parseInt(maxCode.substring(1, 4));
            if (999 == number) {
                char codeChar = (char) (charCode.charAt(0) + 1);
                charCode = String.valueOf(codeChar);
                nextCode = charCode + "001";
            } else {
                number++;
                String numberStr = "00" + number;
                String finalNumber = numberStr.substring(numberStr.length() - 3, numberStr.length());
                nextCode = charCode + finalNumber;
            }
        }
        return nextCode;
    }

    /**
     * 保存教材版本
     * 
     * @param addCodes 新增学科编码
     * @param editGid 修改版本id
     * @param delGid 删除版本id
     * @param originalName 原来的名称
     * @param name 教材版本名称
     * @return
     */
    public ResultCodeVo addTextbooxVer(String originalName, String[] addCodes, String[] editGid, String[] delGid,
                    String name) {

        List<Map<String, Object>> textbookVerExitList = textbookVerDao.getTextbookVerByName(name);
        if (textbookVerExitList != null && textbookVerExitList.size() > 0 && !name.equals(originalName)) {
            return ResultCodeVo.errorCode("教材版本已经存在");

        }
        /* 新增部分 */
        if (addCodes != null && addCodes.length > 0) {
            String maxCode = textbookVerDao.getMaxCode();
            List<ShareCodeTextbookVer> list = new ArrayList<ShareCodeTextbookVer>();
            String nextCode = getNumberString(maxCode);
            for (String subjectCode : addCodes) {
                // 验证是否有已经存在的
                ShareCodeTextbookVer textbookVer = new ShareCodeTextbookVer();
                textbookVer.setCode(subjectCode + nextCode);
                textbookVer.setDescription("");
                textbookVer.setGid(UUID.randomUUID().toString());
                textbookVer.setModifyIP("127.0.0.1");
                textbookVer.setModifyPgm("TextbookVersion");
                textbookVer.setModifyTime(new Timestamp(new Date().getTime()));
                textbookVer.setName(name);
                textbookVer.setSubjectCode(subjectCode);
                textbookVer.setSysVer(0);
                list.add(textbookVer);
            }
            textbookVerDao.saveTextbookVer(list);
        }
        /* 修改部分 */
        if (editGid != null && editGid.length > 0) {
            /*
             * String msg=""; for(String gid:editGid){ ShareCodeTextbookVer
             * textbookVer=textbookVerDao.get(gid); Map<String,Object>
             * map=textbookVerDao.selectSubjectVersion(textbookVer.getSubjectCode(), name); String
             * exitName=MapUtils.getString(map,"subjectName"); msg=msg+"exitName"+","; }
             * if(StringUtils.isNotEmpty(msg)){ return
             * ResultCodeVo.errorCode(msg.substring(0,msg.length()-1)+"已存在该教材版本"); }
             */
            for (String gid : editGid) {
                textbookVerDao.update(name, gid);
            }
        }
        /* 删除部分 */
        if (delGid != null && delGid.length > 0) {
            for (String gid : delGid) {
                textbookVerDao.delete(gid);
            }

        }

        return ResultCodeVo.rightCode("保存成功");
    }

    /**
     * 查询教材版本 ()<br>
     * 
     * @param name 教材版本名称
     * @return
     */
    public List<Map<String, Object>> getTextbookVer(String name) {
        return textbookVerDao.getTextbookVerByName(name);
    }

    /**
     * 查询教材版本 ()<br>
     * 
     * @param subjectCode 学科编码
     * @param sectionCode 学段编码
     * @param name 教材版本名称
     * @return
     */
    public List<ShareTextbookVerTreeVo> getTextbookVerTreeVo(String subjectCode, String sectionCode, String name) {
        List<Map<String, Object>> verList = textbookVerDao.getTextbookVerName(name, sectionCode, subjectCode);
        Map<String, Integer> verNameList = new HashMap<String, Integer>();
        List<ShareTextbookVerTreeVo> treeVoList = new ArrayList<ShareTextbookVerTreeVo>();
        int _pId = 1;
        if (verList != null && verList.size() > 0) {
            for (Map map : verList) {
                String verName = MapUtils.getString(map, "name");
                // 教材版本id
                Integer pId = MapUtils.getInteger(verNameList, verName);
                Integer textbookCount = MapUtils.getInteger(map, "textbookCount");
                Integer kpsCount = MapUtils.getInteger(map, "kpsCount");
                if (pId == null) {
                    pId = _pId;
                    // 最顶层教材版本
                    ShareTextbookVerTreeVo pTreeVo = new ShareTextbookVerTreeVo();
                    pTreeVo.setId(pId.toString());
                    pTreeVo.setTextbookverName(verName);
                    pTreeVo.setModifyTime(MapUtils.getString(map, "modifyTime"));
                    treeVoList.add(pTreeVo);
                    // 教材版本对应的学科
                    ShareTextbookVerTreeVo cTreeVo = new ShareTextbookVerTreeVo();
                    cTreeVo.setSectionName(MapUtils.getString(map, "sectionName"));
                    cTreeVo.setSubjectName(MapUtils.getString(map, "subjectName"));
                    cTreeVo.setDescription(MapUtils.getString(map, "description"));
                    cTreeVo.setTextbookverName("");
                    cTreeVo.set_parentId(pId.toString());
                    cTreeVo.setId(MapUtils.getString(map, "gid"));
                    cTreeVo.setGid(MapUtils.getString(map, "gid"));
                    cTreeVo.setHasTextbook(textbookCount > 0);
                    cTreeVo.setHasKps(kpsCount > 0);
                    treeVoList.add(cTreeVo);
                    // 把教材版本和id存在map中
                    verNameList.put(verName, _pId++);
                } else {
                    // 教材版本对应的学科
                    ShareTextbookVerTreeVo cTreeVo = new ShareTextbookVerTreeVo();
                    cTreeVo.setSectionName(MapUtils.getString(map, "sectionName"));
                    cTreeVo.setSubjectName(MapUtils.getString(map, "subjectName"));
                    cTreeVo.setDescription(MapUtils.getString(map, "description"));
                    cTreeVo.setGid(MapUtils.getString(map, "gid"));
                    cTreeVo.setTextbookverName("");
                    cTreeVo.set_parentId(pId.toString());
                    cTreeVo.setGid(MapUtils.getString(map, "gid"));
                    cTreeVo.setId(MapUtils.getString(map, "gid"));
                    cTreeVo.setHasTextbook(textbookCount > 0);
                    cTreeVo.setHasKps(kpsCount > 0);
                    treeVoList.add(cTreeVo);
                }
            }
            return treeVoList;
        } else {
            return null;
        }
    }

    /**
     * 删除教材版本 ()<br>
     * 
     * @param name
     * @return
     */
    public ResultCodeVo delTextbook(String name) {
        return textbookVerDao.deletebyName(name) > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }

    /**
     * 删除教材版本 ()<br>
     * 
     * @param gid
     * @return
     */
    public ResultCodeVo delTextbookByGid(String gid) {
        return textbookVerDao.delete(gid) > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }
}
