package com.baizhitong.resource.dao.report;

import java.util.Map;

import com.baizhitong.common.Page;

/*
 * 
	* UserOperateResDao 用户使用资源情况
	* 
	* @author creator gaow 2016年12月8日 上午10:55:45
	* @author updater 
	*
	* @version 1.0.0
 */
public interface UserOperateResDao {
    /**
     * 查询用户使用资源情况 ()<br>
     * 
     * @param param 参数
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    Page selectTeacherOp(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * ()<br>
     * 
     * @param param 参数
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    Page selectStudentOp(Map<String, Object> param, Integer page, Integer rows);

}
