package com.baizhitong.syscode.frontend.service;

import java.util.Map;

public interface ICodeParse {
    /**
     * 
     * (替换当前的Code值)<br>
     * 
     * @param code
     * @return
     */
    public String parseCode(String code, Map<String, String> map, String... str);
}
