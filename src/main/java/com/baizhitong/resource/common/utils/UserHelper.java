package com.baizhitong.resource.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class UserHelper {
    public static final String KEY = "QWErty!@#$%^";

    private static String encrypt(String str, String key, String charset) {
        try {
            if (str == null || str.length() < 1)
                return "";
            DESKeySpec keySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            Cipher c1 = Cipher.getInstance("DES");
            c1.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherByte = c1.doFinal(str.getBytes(charset));
            String result = bytes2Hex(cipherByte);
            if (!(null == charset || charset.length() < 1)) {
                // result = URLEncoder.encode(result, charset);
            }
            return result;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * DES 解密
     * 
     * @param str
     * @param key
     * @param charset
     * @return
     */
    private static String decrypt(String str, String key, String charset) {
        try {
            if (str == null || str.length() < 1)
                return "";
            DESKeySpec keySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(keySpec);
            Cipher c1 = Cipher.getInstance("DES");
            c1.init(Cipher.DECRYPT_MODE, secretKey);
            String result = new String(c1.doFinal(hex2byte(str)), charset);
            if (!(null == charset || charset.length() < 1)) {
                // result = URLDecoder.decode(result, charset);
            }
            return result;
        } catch (Exception e) {
            return "";
        }
    }

    public static String encrypt(String str) {
        return encrypt(str, KEY, "utf-8");
    }

    public static String decrypt(String str) {
        return decrypt(str, KEY, "utf-8");
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    private static byte[] hex2byte(String hexStr) {
        try {
            byte[] bts = new byte[hexStr.length() / 2];
            for (int i = 0, j = 0; j < bts.length; j++) {
                bts[j] = (byte) Integer.parseInt(hexStr.substring(i, i + 2), 16);
                i += 2;
            }
            return bts;
        } catch (Exception e) {
            return "".getBytes();
        }
    }

}
