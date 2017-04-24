package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.res.ResMediaSpecial;
import com.baizhitong.resource.model.vo.res.ResMediaSpecialVo;

/**
 * 
 * 资源_特色资源Service
 * 
 * @author creator zhangqiang 2016年8月9日 上午11:25:15
 * @author updater
 *
 * @version 1.0.0
 */
public interface ResMediaSpecialService {

    /**
     * 
     * (查询特色资源全部信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> querySpecialAllInfoPage(Map<String, Object> param) throws Exception;

    /**
     * 
     * (删除特色资源)<br>
     * 
     * @param id
     * @return
     * @throws Exception
     */
    int deleteResMediaSpecial(String id) throws Exception;

    /**
     * 
     * (查询特色资源待审核信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> querySpecialCheckInfoPage(Map<String, Object> param) throws Exception;

    /**
     * 
     * (特色资源审核)<br>
     * 
     * @param resCode 资源编码数组
     * @param shareCheckLevel 审核中共享级别数组
     * @param shareCheckStatus 分享审核中状态
     * @param checkComments 审核意见
     * @return
     * @throws Exception
     */
    ResultCodeVo saveExamine(String[] resCode, String[] shareCheckLevel, Integer shareCheckStatus, String checkComments)
                    throws Exception;

    /**
     * 
     * (查询特色资源已通过审核信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> querySpecialCheckedInfoPage(Map<String, Object> param) throws Exception;

    /**
     * 
     * (查询特色资源已退回信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> querySpecialBackedInfoPage(Map<String, Object> param) throws Exception;

    /**
     * 
     * (特色资源全部数据导出)<br>
     * 
     * @param shareLevel
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param resStatus
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    HSSFWorkbook getAllDataExcel(Integer shareLevel, String resSpecialTypeL1, String resSpecialTypeL2,
                    Integer resStatus, String makerOrgName, String startDate, String endDate, String resName,
                    String makerName, Integer shareCheckStatus) throws Exception;

    /**
     * 
     * (特色资源待审核数据导出)<br>
     * 
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    HSSFWorkbook getCheckDataExcel(String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName,
                    String startDate, String endDate, String resName, String makerName) throws Exception;

    /**
     * 
     * (特色资源已通过审核数据导出)<br>
     * 
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    HSSFWorkbook getCheckedDataExcel(String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName,
                    String startDate, String endDate, String resName, String makerName, Integer shareCheckLevel)
                                    throws Exception;

    /**
     * 
     * (特色资源审核已退回数据导出)<br>
     * 
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    HSSFWorkbook getBackedDataExcel(String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName,
                    String startDate, String endDate, String resName, String makerName, Integer shareCheckLevel)
                                    throws Exception;

    /**
     * 
     * (根据主键id获取特色资源)<br>
     * 
     * @param id 主键id
     * @return
     * @throws Exception
     */
    ResMediaSpecialVo querySpecialMediaById(Integer id) throws Exception;

    /**
     * 
     * (保存或修改特色资源信息)<br>
     * 
     * @param entity 实体
     * @return
     * @throws Exception
     */
    ResultCodeVo saveResMediaSpecial(ResMediaSpecial entity) throws Exception;

    /**
     * 
     * (查询资源审核情况明细数据)<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> queryCheckDetailInfoPage(Map<String, Object> param) throws Exception;

}
