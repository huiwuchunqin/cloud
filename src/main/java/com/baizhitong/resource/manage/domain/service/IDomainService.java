package com.baizhitong.resource.manage.domain.service;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareDomain;

/**
 * 域名接口 IDomainService TODO
 * 
 * @author creator gaow 2017年3月7日 下午2:44:13
 * @author updater
 *
 * @version 1.0.0
 */
public interface IDomainService {

    ResultCodeVo saveDomain(ShareDomain domain);

}
