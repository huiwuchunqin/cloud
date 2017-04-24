package com.baizhitong.resource.common.utils;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class TimestampConverter implements Converter<String, Timestamp> {

    @Override
    public Timestamp convert(String timeStr) {
        Timestamp t = null;
        if (StringUtils.isNotEmpty(timeStr)) {
            long time = Long.valueOf(timeStr);
            t = new Timestamp(time);
        }
        return t;
    }

}
