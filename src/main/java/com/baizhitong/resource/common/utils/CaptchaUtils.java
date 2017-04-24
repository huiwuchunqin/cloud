package com.baizhitong.resource.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baizhitong.utils.StringUtils;

/**
 * 验证码工具包
 * 
 * @author creator Cuidc 2015/12/11
 * @author updater
 */
public class CaptchaUtils {
    /** 默认随机生成位数 */
    public static final Integer DEFAULT_RANDOM_LENGTH = 4;

    /**
     * 生成验证码图片
     * 
     * @param request http请求对象
     * @param response http响应
     * @param sessionKey key值
     * @return 验证码图片
     * @throws IOException 异常信息
     */
    public static String generateCaptchaImg(HttpServletRequest request, HttpServletResponse response, String sessionKey)
                    throws IOException {
        return generateCaptchaImg(request, response, sessionKey, DEFAULT_RANDOM_LENGTH, 90, 20);
    }

    /**
     * 生成验证码图片
     * 
     * @param request http请求对象
     * @param response http响应
     * @param sessionKey key值
     * @param randomLength 随机生成位数
     * @param width 长度
     * @param height 高度
     * @return 验证码图片
     * @throws IOException 异常信息
     */
    public static String generateCaptchaImg(HttpServletRequest request, HttpServletResponse response, String sessionKey,
                    Integer randomLength, int width, int height) throws IOException {
        // 设置头信息
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 在内存中创建图象
        if (width < 18) {
            width = 90;
        }
        if (height < 18) {
            height = 20;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Times New Roman", Font.BOLD, 18));
        // 画边框
        g.setColor(new Color(0, 0, 0));
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 取随机产生的认证码(4位数字、字母)
        if (randomLength == null || randomLength <= 0) {
            randomLength = DEFAULT_RANDOM_LENGTH;
        }
        String sRand = "";
        for (int i = 0; i < randomLength; i++) {
            String rand = null;
            // 随机生成数字或者字母
            if (random.nextInt(10) > 5) {
                rand = String.valueOf((char) (random.nextInt(10) + 48));
            } else {
                rand = String.valueOf((char) (random.nextInt(26) + 65));
            }
            sRand += rand;
            // 将认证码显示到图象中
            g.setColor(new Color(random.nextInt(80), random.nextInt(80), random.nextInt(80)));
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, ((width - 10) / 5) * i + 10, (int) (height * 0.6 + 4));
        }
        // 图象生效
        g.dispose();

        // 将认证码存入SESSION
        if (!StringUtils.isEmpty(sessionKey)) {
            request.getSession().setAttribute(sessionKey, sRand);
        }

        // 输出图象到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
        return sRand;
    }

    /**
     * 根据key值获取对应验证码的字符串
     * 
     * @param request http请求对象
     * @param sessionKey key值
     * @return 验证码
     */
    public static String getVCFromSession(HttpServletRequest request, String sessionKey) {
        Object obj = request.getSession().getAttribute(sessionKey);
        return obj == null ? null : obj.toString();
    }

    /**
     * 根据key值删除对应验证码的字符串
     * 
     * @param request http请求对象
     * @param sessionKey key值
     */
    public static void removeVCFromSession(HttpServletRequest request, String sessionKey) {
        request.getSession().removeAttribute(sessionKey);
    }

    /**
     * 指定范围内生成颜色
     * 
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
