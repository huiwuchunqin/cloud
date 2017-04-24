package com.baizhitong.resource.common.constants;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * 自定义的view，绑定自定义事件
 * 
 * @author Administrator
 *
 */
public class BztFreeMarkerView extends FreeMarkerView {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.view.freemarker.FreeMarkerView#exposeHelpers(java.util.Map,
     * javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        try {
            IViewPropSetter viewPropSetter = BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(),
                            IViewPropSetter.class, true, false);
            // 设置额外的属性，有可能覆盖现有的属性
            Map<String, Object> props = viewPropSetter.props();
            model.putAll(props);
        } catch (NoSuchBeanDefinitionException ex) {
        }
    }

}
