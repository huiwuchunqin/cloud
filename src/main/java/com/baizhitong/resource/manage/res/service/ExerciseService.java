package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import com.baizhitong.common.Page;

public interface ExerciseService {
    /**
     * 查询测验信息 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page getExercisePage(Map<String, Object> param, Integer page, Integer rows);
}
