package com.baizhitong.resource.common.utils;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.ObjectUtils;
import com.baizhitong.utils.TimeUtils;

@RunWith(SpringJUnit4ClassRunner.class)
// 行政班级编码
@Component
public class PrimaryKeyHelper {

    private @Autowired ISysCodeService  sysCodeService;
    private @Autowired SharePlatformDao platformDao;
    private static SharePlatformDao     staticPlatformDao;
    private static ISysCodeService      staticSysCodeService;

    @PostConstruct
    public void init() {
        staticSysCodeService = sysCodeService;
        staticPlatformDao = platformDao;
    }

    /**
     * 获取行政班级编码 ()<br>
     * 
     * @param orgCode 机构编码
     * @param sectionCode 学段编码
     * @param entryYear 入学年份
     * @param maxCode 最大值
     * @return 编码
     */
    public static String getAdminClassCode(String orgCode, String sectionCode, Integer entryYear, Integer maxCode) {
        String adminClassCode = "BJ" + orgCode + sectionCode + entryYear;

        if (StringUtils.isEmpty(sectionCode)) {
            sectionCode = "#";
        }

        if (maxCode != null) {
            Integer next = maxCode + 1;
            adminClassCode += next < 10 ? "0" + next : next;
        } else {
            adminClassCode += "01";
        }
        return adminClassCode;
    }

    /**
     * 获取用户编码 ()<br>
     * 
     * @return 编码
     */
    public static String getUserCode() {
        Integer month = TimeUtils.getMonth();
        Integer year = TimeUtils.getYear();
        String monthStr = month > 9 ? month.toString() : "0" + month;
        SharePlatform platform = staticPlatformDao.getByCodeGlobal();
        if (platform == null) {
            throw new RuntimeException("平台信息为空");
        }
        return staticSysCodeService.getCode(SysCodeConstants.USER_CODE, "platformCode", platform.getCodeGlobal(),
                        "yearMonth", year.toString() + monthStr);
    }

    @Test
    public void test() {
        String code = getUserCode();
        System.out.println("编码" + code);

    }

}
