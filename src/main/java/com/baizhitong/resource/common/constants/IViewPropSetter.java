package com.baizhitong.resource.common.constants;

import java.util.Map;

/**
 * 返回view中设置额外的属性
 * 
 * @author Administrator
 *
 */
public interface IViewPropSetter {

    /**
     * 返回当前的键值对
     * 
     * @return
     */
    public Map<String, Object> props();

}
