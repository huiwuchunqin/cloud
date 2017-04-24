package com.baizhitong.resource.manage.company.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baizhitong.resource.manage.company.service.ICompanyService;

/**
 * 
 * 自动更新机构学年学期信息服务
 * 
 * @author creator ZhangQiang 2017年3月7日 下午1:40:53
 * @author updater
 *
 * @version 1.0.0
 */
@Component("orgJob")
public class OrgJobAction {

    /** 机构信息接口 */
    @Autowired
    private ICompanyService companyService;

    /**
     * 
     * (自动更新机构学年学期信息定时器)<br>
     */
    @Scheduled(cron = "0 0 3 * * ?") // 每天3点
    public void updateOrgYearTermInfoJob() { 
        companyService.autoUpdateOrgYearTerm();
    }

}
