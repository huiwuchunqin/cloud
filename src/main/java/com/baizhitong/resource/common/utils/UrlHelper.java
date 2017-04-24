package com.baizhitong.resource.common.utils;

import java.util.Date;

import com.baizhitong.encrypt.MD5;
import com.baizhitong.encrypt.MessageEncrypt;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

public class UrlHelper {

    public static String          CIPHERKEY       = "Q!W@E#R$";
    public static String          DOWNTIME_FORMAT = "yyyyMMddHHmmss";

    private static MessageEncrypt DES             = MessageEncrypt.getInstance("DES");
    private static MessageEncrypt MD5ENC          = MessageEncrypt.getInstance("MD5");

    /**
     * MD5解密
     * 
     * @param enc
     * @return
     */
    public static String encodeMD5(String enc) {
        return MD5ENC.encode(enc);
    }

    /**
     * 检查上传加密串
     * 
     * @param key
     * @param type 上传类型
     * @param enc
     * @return
     */
    public static boolean checkKey(String key, String type, Long remain, String enc) {
        if (StringUtils.isEmpty(enc))
            return false;
        String e = MD5.calcMD5(key + type + remain + CIPHERKEY);
        if (enc.equalsIgnoreCase(e))
            return true;
        return false;
    }

    /**
     * 获取下载加密串
     * 
     * @param id
     * @return
     */
    public static String getDownKey(String id) {
        String time = DateUtils.getDate(new Date(), UrlHelper.DOWNTIME_FORMAT);
        return id + DES.encode(id + time + CIPHERKEY);
    }

    /**
     * 获取阅读加密串
     * 
     * @param id
     * @return
     */
    public static String getReadKey(String id) {
        return getDownKey(id);
    }

    public static class DownKey {
        public String id;
        public String _id;
        public String time;
        public String key;

        public DownKey(String id, String id2, String time, String key) {
            this.id = id;
            this._id = id2;
            this.time = time;
            this.key = key;
        }

    }

    /**
     * 获取的key串 type = 1,单文件上传， tpye = 2 多文件上传
     * 
     * @return
     */
    public static String getUploadKey(String uid, String type, Long remain) {
        if (uid == null || StringUtils.isEmpty(type))
            return "";
        return StringUtils.format("key={0}&type={1}&remain={2}&enc={3}", uid, type, remain,
                        MD5.calcMD5(uid + type + remain + CIPHERKEY));
    }

    /**
     * 生成加密串
     * 
     * @param uid
     * @param type
     * @param remain
     * @return
     */
    public static String getMD5String(String uid, String type, Long remain) {
        return MD5.calcMD5(uid + type + remain + CIPHERKEY);
    }

    /**
     * 
     * 检查下载加密串，返回解密后的对象
     * 
     * @param enc
     * @return
     */
    public static DownKey checkDownKey(String enc) {
        if (StringUtils.isEmpty(enc) || enc.length() <= 38) {
            return null;
        }
        String id = enc.substring(0, 24);
        enc = enc.substring(24);
        enc = DES.decode(enc);
        if (StringUtils.isEmpty(enc) || enc.length() <= 38) {
            return null;
        }
        String _id = enc.substring(0, 24);
        String time = enc.substring(24, 38);
        String key = enc.substring(38);
        DownKey dk = new DownKey(id, _id, time, key);
        if (checkDownKey(id, _id, time, key))
            return dk;

        return null;
    }

    /**
     * 
     * 检查阅读加密串，返回解密后的对象
     * 
     * @param enc
     * @return
     */
    public static DownKey checkReadKey(String enc) {
        return checkDownKey(enc);
    }

    private static boolean checkDownKey(String id, String _id, String time, String key) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(_id) || StringUtils.isEmpty(time)
                        || StringUtils.isEmpty(key))
            return false;
        if (id.equalsIgnoreCase(_id) && CIPHERKEY.equalsIgnoreCase(key)) {
            Date _oldTime = DateUtils.getDateTime(time, DOWNTIME_FORMAT);
            if (_oldTime == null)
                return false;
            long _this = new Date().getTime();
            long _old = _oldTime.getTime();
            if (_this - _old <= 3600 * 1000) {
                // 超过10秒地址无效
                return true;
            }

        }
        return false;
    }

    /**
     * 大小格式化
     * 
     * @param data
     * @return
     */
    public static String sizeFormat(Long data) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        String f[] = { "B", "KB", "MB", "GB", "TB", "PB", "EB" };
        int l = 0;
        float size = 0f;
        size = data;
        while (size >= 1024) {
            size = size / 1024;
            l++;
        }
        if (l >= 0 && l <= f.length - 1) {
            return df.format(size) + f[l];
        }
        return "0" + f[0];
    }

    public static String[] convertStrToArray(String str) {
        String[] strArray = null;
        strArray = str.split(",");
        return strArray;
    }

    public static void main(String[] args) throws Exception {
    }
}
