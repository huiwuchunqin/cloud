package com.baizhitong.resource.common.utils;

import com.baizhitong.utils.RegexUtils;

public class RegexUtil extends RegexUtils {

    public final static String REG_ACCOUNT  = "^[0-9a-zA-Z\u0000-\u00FF]{0,50}$";
    public final static String REG_PASSWORD = "^[^\u4E00-\u9FA5\uF900-\uFA2D\u0020]{6,20}$";
    public final static String REG_WORKNO   = "^[^\u4E00-\u9FA5\uF900-\uFA2D\u0020]{0,20}$";
    /**
     * 获取长度 ()<br>
     * 
     * @param value
     * @return
     */
    /*
     * public static int getStrLength(String value) { int valueLength = 0; String chinese =
     * "[\u0391-\uFFE5]"; for (int i = 0; i < value.length(); i++) { String temp =
     * value.substring(i, i + 1); if (temp.matches(chinese)) { valueLength += 1; } else {
     * valueLength += 1; } } return valueLength; }
     */

    /**
     * 校验长度是否符合 ()<br>
     * 
     * @param str
     * @param minlength
     * @param maxlegnth
     * @return
     */
    public static boolean valideLength(String str, int minlength, int maxlegnth) {
        int actLen = str.length();
        return actLen <= maxlegnth && actLen >= minlength;

    }

}
