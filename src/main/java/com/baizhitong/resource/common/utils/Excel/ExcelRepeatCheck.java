package com.baizhitong.resource.common.utils.Excel;

import java.util.HashMap;
import java.util.Map;

import com.baizhitong.utils.StringUtils;

import freemarker.template.utility.StringUtil;

public class ExcelRepeatCheck {

    public enum CheckType {
        Email("邮箱"), Mobile("电话"), loginAccount("登录账号"), studentNumber("学号"),studentHardNumber("硬件号");
        private String name;

        private CheckType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Map<CheckType, RepeatCheck> checkListMap = new HashMap<CheckType, RepeatCheck>();

    /**
     * 添加数据 ()<br>
     * 
     * @param data 数据
     */
    public void addData(CheckType type, String data) {
        RepeatCheck check = checkListMap.get(type);
        if (check == null) {
            check = new RepeatCheck();
            checkListMap.put(type, check);
        }
        check.addData(data);
    };

    public String getCheckMsg() {
        StringBuilder msg = new StringBuilder();
        for (CheckType check : checkListMap.keySet()) {
            String mString = checkListMap.get(check).getCheckMsg();
            if (StringUtils.isNotEmpty(mString)) {
                msg.append(check.getName()).append("<br/>").append(mString).append("（导入数据重复）").append("<br/>");

            }
        }
        return msg.toString();
    }
}
