package com.baizhitong.resource.common.utils;

import org.junit.Test;

import com.baizhitong.utils.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinHelper {
    private final static String suffix = "admin";

    // 将中文转换为英文
    public static String getEname(String name) {
        HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
        pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        try {
            return PinyinHelper.toHanYuPinyinString(name, pyFormat, "", true);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 得到老师名字 ()<br>
     * 
     * @param name
     * @return
     */
    public static String getAccount(String name) {
        if (StringUtils.isEmpty(name))
            return "";

        String pin = getEname(name.substring(0, 1));
        if (name.length() > 1) {
            for (int i = 1; i < name.length(); i++)
                pin = pin + getEname(name.substring(i, i + 1)).charAt(0);
        }
        return pin;
    }

    public static String getCompanyAccount(String name) {
        if (StringUtils.isEmpty(name))
            return "";

        String pin = "";
        for (int i = 0; i < name.length(); i++)
            pin = pin + getEname(name.substring(i, i + 1)).charAt(0);
        return pin + suffix;
    }

    @Test
    public void test() {
        String s = getCompanyAccount("橫涇中學");
    }

}