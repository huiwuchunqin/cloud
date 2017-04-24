package com.baizhitong.resource.manage.dataSynchronization.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.dataSynchronization.service.ISynchronizeService;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.StringUtils;

/**
 * 数据同步控制类 DataSynchronizationAction TODO
 * 
 * @author creator gaow 2016年9月2日 下午2:14:21
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/sychonize")
public class DataSynchronizationAction extends BaseAction {
    @Autowired
    ISynchronizeService sychronize;
    @Autowired
    ICompanyService     companyService;

    /**
     * 当前机构数据同步 ()<br>
     * 
     * @return
     */
    @RequestMapping("/sychonizeCurrent.html")
    @ResponseBody
    public ResultCodeVo currentCompanyDataSychonize(HttpServletRequest request, Integer batch) {
        if (batch == null || batch < 1)
            return ResultCodeVo.errorCode("更新批次必须为大于0的整数");
        CompanyInfoVo company =  getCompanyInfo();
        if (company == null) {
            return ResultCodeVo.errorCode("没有机构信息");
        }
        if (StringUtils.isNotEmpty(company.getCd_guid())) {
            try {
                return sychronize.dataSynchronize(company.getOrgCode(), company.getOrgName(), company.getCd_guid(),
                                batch);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("更新程序异常" + e.getMessage(), e);
                return ResultCodeVo.errorCode("更新出错");
            }
        } else {
            return ResultCodeVo.errorCode("当前机构没有唯一识别号cd_guid");
        }
    }

    /**
     * 当前机构数据同步 ()<br>
     * 
     * @return
     */
    @RequestMapping("/sychonizeSelected.html")
    @ResponseBody
    public ResultCodeVo selectedCompanyDataSychonize(HttpServletRequest request, Integer batch,
                    @RequestParam(value = "orgCode[]") String[] orgCodes) {
        if (batch == null || batch < 1)
            return ResultCodeVo.errorCode("更新批次必须为大于0的整数");
        String msg = "";
        if (orgCodes != null && orgCodes.length > 0) {
            for (String orgCode : orgCodes) {
                CompanyInfoVo company = companyService.getCompanyInfo(orgCode);
                if (company == null) {
                    continue;
                }
                if (StringUtils.isNotEmpty(company.getCd_guid())) {
                    try {
                        ResultCodeVo resultCodeVo = sychronize.dataSynchronize(company.getOrgCode(),
                                        company.getOrgName(), company.getCd_guid(), batch);
                        if (!resultCodeVo.getSuccess()) {
                            msg = msg + "@" + resultCodeVo.getMsg();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("更新程序异常" + e.getMessage(), e);
                        return ResultCodeVo.errorCode("更新出错");
                    }
                }
            }
            if (StringUtils.isNotEmpty(msg)) {
                return ResultCodeVo.errorCode(msg);
            } else {
                return ResultCodeVo.rightCode("更新成功");
            }
        } else {
            return ResultCodeVo.errorCode("请选择学校");
        }
    }

    /**
     * 把园区数据插入临时表 ()<br>
     * 
     * @return
     */
    @RequestMapping("/insertTemp.html")
    @ResponseBody
    public ResultCodeVo insertTemp() {
        Map<String, String> toDoorgList = new HashMap<String, String>();
        /*
         * toDoorgList.put("A323F0B7-3F0A_", "9F9C2451B81C47A3A078623CAE6FA0BC");//苏州工业园区新城花园小学
         * toDoorgList.put("BE9F8814-962C_", "3601C4E6FEC84675ABDB14FE5E62A071");//苏州工业园区第二实验小学
         * toDoorgList.put("A512A27A-72DC_", "E5826CB6E73348EAA6DB341E7555087E");//苏州工业园区翰林小学
         * toDoorgList.put("F2BA9EC4-D46D_", "01C7A03D530B48A49D647DD459EE6A07");//苏州工业园区跨塘实验小学
         * toDoorgList.put("F9BDA5E6-EF8F_", "48B477A5B7254029B1726F0467EE10B5");//苏州工业园区娄葑实验小学
         * toDoorgList.put("F718F94A-C126_", "469BC5F540294929817A108708043E5A");//苏州工业园区方洲小学
         * toDoorgList.put("A404C9F2-A2BC_", "355ADEE40AA043AE8055D3777D42D4E3");//苏州工业园区星海实验中学
         * toDoorgList.put("6D6C21C0-C67E_", "603AE1EA825149C2B168CB2643544A47");//苏州工业园区第一中学
         * toDoorgList.put("9E820589-4635_", "bcd5f199-2165-4da8-aa3c-4a26d53df022");//苏州工业园区星洲学校
         * toDoorgList.put("C16D500F-C6D0_", "420C4E99EFA34ECD9A2D17B37ACFAC90");//苏州工业园区景城学校
         * toDoorgList.put("3321F201-68A2_", "6191BAD3A77A49AE9EE03520D6978DDF");//苏州工业园区斜塘学校
         * toDoorgList.put("ADA95EEF-93AB_", "880982751D874F4D9881B45454AE0D04");//苏州工业园区娄葑学校
         * toDoorgList.put("1000JG00000057", "e614fdc1-66b2-4dc7-b811-d559f460fad2");// 苏州工业园区星洋学校
         * toDoorgList.put("1A0B3010-72A7_", "D4EF0648FBE14712823CACF823F9B0F9");// 苏州工业园区独墅湖学校
         * toDoorgList.put("2E5B9BD6-BE4A_", "09D1EB973966404AAE1DABA142BAD389");// 苏州工业园区唯亭实验小学
         * toDoorgList.put("73230570-0799_", "0560491087AA49D89269E4AA56082417");// 苏州工业园区星湾学校
         * toDoorgList.put("E07B8AB8-A229_", "A4627C2ACCAE4FE39EC0E9DBD3B0BF15");// 苏州工业园区胜浦实验小学
         */ toDoorgList.put("C16D500F-C6D0_", "420C4E99EFA34ECD9A2D17B37ACFAC90");// 苏州工业园区星洋
        Set<String> keySet = toDoorgList.keySet();
        for (String s : keySet) {
            sychronize.tempInsert(s, toDoorgList.get(s));
        }
        return ResultCodeVo.rightCode("保存成功");
    }

    @RequestMapping("/test.html")
    public String test() {
        return "/manage/test.html";
    }

    /**
     * 所有的数据数据同步 ()<br>
     * 
     * @return
     */
    @RequestMapping("/sychonizeAll.html")
    @ResponseBody
    public ResultCodeVo currentAllCompany(Integer batch) {
        if (batch == null || batch < 1)
            return ResultCodeVo.errorCode("更新批次必须为大于0的整数");
        List<CompanyInfoVo> orgList = companyService.getAllCompany();
        if (orgList == null || orgList.size() <= 0) {
            return ResultCodeVo.errorCode("没有机构信息");
        }
        List<String> toDoorgList = new ArrayList<String>();
        toDoorgList.add("F718F94A-C126_");
        toDoorgList.add("0AB5A424-625D_");
        toDoorgList.add("A404C9F2-A2BC_");
        toDoorgList.add("AA2005A7-09BC_");
        toDoorgList.add("6A88E04A-1E59_");
        toDoorgList.add("9E820589-4635_");
        toDoorgList.add("C16D500F-C6D0_");
        toDoorgList.add("EA07D05F-9511_");
        toDoorgList.add("1000JG00000057");
        toDoorgList.add("1C24CBE0-52F1_");
        toDoorgList.add("1A0B3010-72A7_");
        toDoorgList.add("321283A0-6B72_");
        String msg = "";
        try {
            for (CompanyInfoVo org : orgList) {
                if (!org.getOrgName().contains("幼儿园") && toDoorgList.contains(org.getOrgCode().trim())) {
                    ResultCodeVo result = sychronize.dataSynchronize(org.getOrgCode(), org.getOrgName(),
                                    org.getCd_guid(), batch);
                    if (!result.getSuccess()) {
                        msg = msg + "@" + result.getMsg();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新程序异常" + e.getMessage(), e);
            return ResultCodeVo.errorCode("更新出错" + e.getMessage());
        }
        if (StringUtils.isNotEmpty(msg)) {
            return ResultCodeVo.errorCode(msg);
        }
        return ResultCodeVo.rightCode("保存成功");
    }

    /**
     * 跳转到更新页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toSychonize.html")
    public String toUpdate(ModelMap model) {
        model.put("orgList", JSON.toJSONString(companyService.getAllCompany()));
        return "/manage/dataSynchronize/dataSynchronize.html";
    }

    @Resource(name = "dataSource_mooc")
    DataSource dataSource;

    @RequestMapping("/insertTest.html")
    public ModelAndView insertTest() {
        sychronize.updateTest(dataSource);
        return null;
    }
}
