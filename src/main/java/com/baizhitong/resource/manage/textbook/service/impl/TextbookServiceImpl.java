package com.baizhitong.resource.manage.textbook.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.ShareCodeGradeDao;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.dao.share.ShareCodeTextbookVerDao;
import com.baizhitong.resource.dao.share.ShareTextbookDao;
import com.baizhitong.resource.manage.textbook.service.TextbookService;
import com.baizhitong.resource.model.share.ShareCodeGrade;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.resource.model.share.ShareCodeTextbookVer;
import com.baizhitong.resource.model.share.ShareTextbook;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;
import com.baizhitong.resource.model.vo.share.ShareTextbookVo;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 教材接口实现类
 * 
 * @author creator BZT 2016年1月12日 下午5:16:42
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class TextbookServiceImpl extends BaseService implements TextbookService {
    @Autowired
    private ShareTextbookDao        textbookDao;            // 教材dao
    @Autowired
    private ISysCodeService         sysCodeService;
    /** 学段dao */
    @Autowired
    private ShareCodeSectionDao     shareCodeSectionDao;
    /** 学科dao */
    @Autowired
    private ShareCodeSubjectDao     shareCodeSubjectDao;
    /** 年级dao */
    @Autowired
    private ShareCodeGradeDao       shareCodeGradeDao;
    /** 教材版本dao */
    @Autowired
    private ShareCodeTextbookVerDao shareCodeTextbookVerDao;

    /**
     * 保存教材
     * 
     * @param textbook
     * @throws Exception
     */
    public ResultCodeVo saveTextbook(ShareTextbookVo textbookvo) throws Exception {
        List<ShareTextbook> sameNameList = textbookDao.getSameName(textbookvo.getTbName(), textbookvo.getSubjectCode(),
                        "");
        if (sameNameList != null && sameNameList.size() > 0)
            return ResultCodeVo.errorCode("同学科下存在重名的教材");
       
        UserInfoVo user =getUserInfoVo();
        ShareTextbook textbook = ShareTextbookVo.voToEntity(textbookvo);
        if (StringUtils.isNotEmpty(textbookvo.getTbCode())) {
            ShareTextbook siblingTextbook = textbookDao.getShareTextbookByTbCode(textbookvo.getTbCode());
            textbook.setSectionCode(siblingTextbook.getSectionCode());
            textbook.setSubjectCode(siblingTextbook.getSubjectCode());
            textbook.setTextbookVerCode(siblingTextbook.getTextbookVerCode());
            textbook.setGradeCode(siblingTextbook.getGradeCode());
        }
        textbook.setModifyIP(getIp());
        textbook.setModifyPgm(user.getUserName());
        textbook.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        textbook.setGid(UUID.randomUUID().toString());
        textbook.setTbCode(sysCodeService.getCode(SysCodeConstants.TEXT_BOOK_CODE, "textbookVerCode",
                        textbook.getTextbookVerCode(), "gradeCode", textbook.getGradeCode()));
        textbookDao.saveShareTextbook(textbook);
        ShareCodeSubject subject = shareCodeSubjectDao.getSubject(textbook.getSubjectCode());
        ShareCodeSection section = shareCodeSectionDao.getSection(textbook.getSectionCode());
        ShareCodeGrade grade = shareCodeGradeDao.getGrade(textbook.getGradeCode());
        ShareCodeTextbookVer textbookVer = shareCodeTextbookVerDao.getTextbookVer(textbook.getTextbookVerCode());
        NodeTreeVo nodeTreeVo = new NodeTreeVo();
        if (section != null)
            nodeTreeVo.setSectionName(section.getName());
        if (subject != null)
            nodeTreeVo.setSubjectName(subject.getName());
        if (grade != null)
            nodeTreeVo.setGradeName(grade.getName());
        if (textbookVer != null)
            nodeTreeVo.setTextbookVerName(textbookVer.getName());
        nodeTreeVo.setGid(textbook.getGid());
        nodeTreeVo.setCode(textbook.getTbCode());
        nodeTreeVo.setTbCode(textbook.getTbCode());
        nodeTreeVo.setPcode(null);
        nodeTreeVo.setDescription(textbookvo.getMemo());
        nodeTreeVo.setState(null);
        nodeTreeVo.setGradeCode(textbook.getGradeCode());
        nodeTreeVo.setSubjectCode(textbook.getSubjectCode());
        nodeTreeVo.setTextbookVerCode(textbook.getTextbookVerCode());
        nodeTreeVo.setSectionCode(textbook.getSectionCode());
        nodeTreeVo.setNodeType("textbook");
        nodeTreeVo.setText(textbook.getTbName());
        return ResultCodeVo.rightCode("保存成功", nodeTreeVo);
    }

    /**
     * 
     * 更新教材信息
     * 
     * @param name
     * @param memo
     * @param gid
     * @return
     * @throws Exception
     */
    public ResultCodeVo updateTextbook(String tbName, String memo, String gid) throws Exception {
        // 获取http请求对象
       
        // 从cookie中获取用户登录信息
        UserInfoVo userInfo =getUserInfoVo();
        if (StringUtils.isEmpty(tbName)) {
            return ResultCodeVo.errorCode("教材名称不能为空");
        }
        ShareTextbook textbook = textbookDao.getShareTextbook(gid);
        // 重名检查
        List<ShareTextbook> sameNameList = textbookDao.getSameName(tbName, textbook.getSubjectCode(), gid);
        if (sameNameList != null && sameNameList.size() > 0)
            return ResultCodeVo.errorCode("同学科下存在重名的教材");
        /*************************** 更新 *********************************/
        textbook.setTbName(tbName);
        textbook.setMemo(memo);
        textbook.setModifyPgm(userInfo.getUserName());
        textbook.setModifyIP(getIp());
        textbook.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        boolean success = textbookDao.saveShareTextbook(textbook);
        ShareCodeSubject subject = shareCodeSubjectDao.getSubject(textbook.getSubjectCode());
        ShareCodeSection section = shareCodeSectionDao.getSection(textbook.getSectionCode());
        ShareCodeGrade grade = shareCodeGradeDao.getGrade(textbook.getGradeCode());
        ShareCodeTextbookVer textbookVer = shareCodeTextbookVerDao.getTextbookVer(textbook.getTextbookVerCode());
        NodeTreeVo nodeTreeVo = new NodeTreeVo();
        if (section != null)
            nodeTreeVo.setSectionName(section.getName());
        if (subject != null)
            nodeTreeVo.setSubjectName(subject.getName());
        if (grade != null)
            nodeTreeVo.setGradeName(grade.getName());
        if (textbookVer != null)
            nodeTreeVo.setTextbookVerName(textbookVer.getName());
        nodeTreeVo.setGid(textbook.getGid());
        nodeTreeVo.setCode(textbook.getTbCode());
        nodeTreeVo.setTbCode(textbook.getTbCode());
        nodeTreeVo.setPcode(null);
        nodeTreeVo.setState(null);
        nodeTreeVo.setNodeType("textbook");
        nodeTreeVo.setDescription(memo);
        nodeTreeVo.setText(textbook.getTbName());
        if (success) {
            return ResultCodeVo.rightCode("教材更新成功", nodeTreeVo);
        } else {
            return ResultCodeVo.errorCode("教材更新失败");
        }
    }

    /**
     * 删除教材
     * 
     * @param gid
     * @return
     */
    public ResultCodeVo delTextbook(String gid) {
        try {
            ShareTextbook textbook = textbookDao.getShareTextbook(gid);
            if (textbook == null)
                return ResultCodeVo.errorCode("没有查询到教材");
            textbook.setFlagDelete(1);
            textbookDao.saveShareTextbook(textbook);
            return ResultCodeVo.rightCode("删除教材成功");
        } catch (Exception e) {
            return ResultCodeVo.rightCode("删除教材失败");
        }

    }

    /**
     * 
     * 查询教材
     * 
     * @param gid 教材id
     * @return
     * @throws Exception
     */
    public ShareTextbookVo getShareTextbookVo(String gid) throws Exception {
        ShareTextbook textbook = textbookDao.getShareTextbook(gid);
        return ShareTextbookVo.EntityToVo(textbook);
    }

    /**
     * 查询教材信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param textbookCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbookVo> getTextbookList(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode, String termCode) throws Exception {
        List<ShareTextbook> textbookList = textbookDao.getTextbookList(sectionCode, gradeCode, subjectCode,
                        textbookVerCode, textbookCode, termCode);
        return ShareTextbookVo.EntityListToVoList(textbookList);
    }

}
