package com.baizhitong.resource.common.constants;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

public class BztFreeMarkerViewResolver extends FreeMarkerViewResolver {
    /**
     * Requires {@link FreeMarkerView}.
     */
    @Override
    protected Class<?> requiredViewClass() {
        return BztFreeMarkerView.class;
    }
}
