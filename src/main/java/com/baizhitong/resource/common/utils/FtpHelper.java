package com.baizhitong.resource.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.zip.CRC32;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.baizhitong.encrypt.MessageEncrypt;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.ftp.FtpClientProxy;
import com.baizhitong.utils.ftp.FtpConPoolManager;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

/**
 * FTP 上传文件名规则生成器
 * 
 * @author Grxie
 */
public class FtpHelper {
    /** 日志 */
    private static Logger         log               = LoggerFactory.getLogger(FtpHelper.class);
    private static MessageEncrypt DES               = MessageEncrypt.getInstance("DES");
    public static String          CIPHERKEY         = "Q!W@E#R$";
    public static String          FOLDERNAME_FORMAT = "yyyyMMdd";                              // 文件夹名
    public static final String    COURSE_COVER      = "/course/cover";                         // 课程封面路径
    public static final String    USER_PHOTO        = "user/head_portrait";                    // 用户头像地址
    public static final String    COMPANY_LOGO      = "/company/logo";                         // 机构logo
    public static final String    COMPANY_BANNER    = "/company/banner";                       // 机构图片
    public static final String    NOTICE_FILE       = "/notice/file";                          // 公告附件
    public static final String    RICHTEXT_IMAGES   = "/richtext/images";                      // 富文本图片内容
    public static final String    MEDIA_COVER       = "/media/cover";                          // 视频封面
    public static final String    DEMO              = "/demo";                                 // ftp测试地址
    private static final int COMPRESS_SIZE=1280;
    private static final double COMPRESS_QUALITY=0.6d;
    public FtpHelper() {

    }

    /**
     * 生成文件上传路径地址
     * 
     * @param type 上传类型
     * @return
     */
    public static String generateUploadPath(String type) {
        return String.format("%s/%s/", type, DateUtils.getDate(FOLDERNAME_FORMAT));
    }

    /**
     * 生成上传文件名称
     * 
     * @param crc 文件唯一码
     * @param fileName 原始文件名
     * @return
     */
    public static String generateUploadName(String crc, String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return String.format("%s_%s%s", crc, DES.encode(crc, suffix), suffix);
    }
    
    /**
     * 去除逗号
     * ()<br>
     * @param fileName
     * @return
     */
    public static String removeComma (String fileName){
        int index=fileName.lastIndexOf(".");
        fileName=fileName.substring(0,index).replaceAll("\\.", "")+fileName.substring(index);
        return fileName;
    }
    /**
     * 处理文件名
     * ()<br>
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName){
        fileName=removeComma(fileName);
        fileName = UUID.randomUUID().toString() + fileName.substring(fileName.indexOf("."), fileName.length());
        return fileName;
    }
    /**
     * 压缩
     * ()<br>
     * @param file
     * @return
     * @throws IOException
     */
    public static InputStream compressPic(MultipartFile file)throws IOException{
        // TODO Auto-generated method stub
        String fileName=getFileName(file.getOriginalFilename());
        String ext=fileName.substring(fileName.lastIndexOf("."));
        File output=new File(file.getOriginalFilename()+"-thumb."+ext);
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());   
        int width = bufferedImage.getWidth();   
        int height = bufferedImage.getHeight();
        Builder builder=Thumbnails.of(bufferedImage);
        boolean needScale=false;
        
        if(width>height){
            if(width>COMPRESS_SIZE){
                builder.width(COMPRESS_SIZE).keepAspectRatio(true);
                needScale = true;
            }
        }else{
            if(height>COMPRESS_SIZE){
                builder.height(COMPRESS_SIZE).keepAspectRatio(true);
                needScale = true;
            }
        }
        try{
            if(ext.contains("png")){
                if(!needScale){
                    return file.getInputStream();
                }
            }
            if(!needScale){
                builder.scale(1);
            }
            builder.outputQuality(COMPRESS_QUALITY).toFile(output);
            InputStream inputStream=new FileInputStream(output);
            return inputStream;
        }catch(Exception ex){
            log.error("",ex);
            return file.getInputStream();
        }finally{
            bufferedImage.flush();
        }
    }
    /**
     * ftp文件上传
     * 
     * @param file MultipartFile对象
     * @param path ftp保存目录
     * @return
     */
    public static ResultCodeVo ftpUploadFile(MultipartFile file, String path) {
        // 返回对象
        ResultCodeVo result = new ResultCodeVo();

        // 获取ftp连接池
        FtpConPoolManager.getInstance();

        // ftp连接
        FtpClientProxy ftpClientProxy = null;
        try {
            // 获取连接
            ftpClientProxy = FtpConPoolManager.getConnection();
            if (ftpClientProxy == null) {
                result.setMsg("未获取到ftp连接，出现异常!");
                result.setSuccess(false);
                log.error("\n未获取到ftp连接，出现异常!\n");
            } else {
                // 获取文件名与文件本地路径
                CommonsMultipartFile cmf = ((CommonsMultipartFile) file);
                // crc32校验
                CRC32 crc = new CRC32();
                crc.update(file.getBytes());
                // ftp目录
                String uploadPath = FtpHelper.generateUploadPath(path);
                // 文件名
                String uploadName = FtpHelper.generateUploadName(Long.toString(crc.getValue()),
                                file.getOriginalFilename());
                // 开始上传
                boolean success = true;// ftpClientProxy.uploadFile(file.getOriginalFilename(),
                                       // uploadPath+uploadName ,file.getInputStream());

                result.setSuccess(success);
                if (success) {
                    result.setData(uploadPath + uploadName + "");
                } else {
                    //// TODO: 2015/12/15 wgh
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
    }
}
