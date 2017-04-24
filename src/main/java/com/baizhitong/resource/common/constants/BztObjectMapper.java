package com.baizhitong.resource.common.constants;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class BztObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    public BztObjectMapper() {
        this.setSerializationInclusion(Include.NON_NULL);
        this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // this.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
        // this.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        // this.configure(SerializerFeature, false);
        // this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
