package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import com.baizhitong.common.Page;

public interface QuestionService {
    /**
     * 查询题目信息 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page getQuestionPage(Map<String, Object> param, Integer page, Integer rows);
}
