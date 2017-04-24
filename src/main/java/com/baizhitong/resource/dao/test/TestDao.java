package com.baizhitong.resource.dao.test;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.test.Test;

public interface TestDao {
    /**
     * 根据试卷编码查询试卷 ()<br>
     * 
     * @param testCode 试卷编码
     * @return
     */
    public Test findByTestCode(String testCode);

    /**
     * 更新试卷审核状态 <br>
     * 
     * @param testCode 试卷编码
     * @param shareCheckStatus 审核状态
     * @param modifierIP 更新者IP
     * @param modifier 更新者姓名
     * @return 更新记录数
     */
    public int updateCheckStatus(String testCode, String shareCheckStatus, String modifierIP, String modifier); 

    /**
     * 查询试卷信息 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getTestPage(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 删除试卷 ()<br>
     * 
     * @param ids
     * @return
     */
    public int deleteTest(String ids, String modifier, String modifierIP);

    /**
     * 
     * (批量操作测验)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip);
}
