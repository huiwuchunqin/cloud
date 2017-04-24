package com.baizhitong.resource.common.utils.Excel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 验证数据重复 RepeatCheck TODO
 * 
 * @author creator gaow 2017年3月10日 下午1:26:01
 * @author updater
 *
 * @version 1.0.0
 */
public class RepeatCheck {
    /**
     * 数据列表
     */
    private Set<String> dataSet    = new HashSet<String>();
    /**
     * 重复数据列表
     */
    private Set<String> repeatList = new HashSet<String>();

    /**
     * 添加数据 ()<br>
     * 
     * @param data 数据
     */
    public void addData(String data) {
        if (dataSet.contains(data)) {
            repeatList.add(data);
        } else {
            dataSet.add(data);
        }
    };

    /**
     * 错误字符串的处理 CheckCallBack TODO
     * 
     * @author creator gaow 2017年3月10日 下午1:27:13
     * @author updater
     *
     * @version 1.0.0
     */
    public interface DataProcess {
        public String makeString(Set<String> repeatList);
    }

    /**
     * 按照逗号分割 ()<br>
     * 
     * @return
     */
    public String getCheckMsg() {
        StringBuffer str = new StringBuffer();
        if (repeatList == null || repeatList.size() <= 0) {
            return "";
        }
        for (String msg : repeatList) {
            str.append(msg).append(",");
        }
        return str.toString().substring(0, str.toString().length() - 1);
    }

    /**
     * 验证 ()<br>
     * 
     * @param callback
     * @return
     */
    public String getCheckMsg(DataProcess callback) {
        if (repeatList != null && repeatList.size() > 0) {
            return callback.makeString(repeatList);
        } else {
            return "";
        }
    }
}
