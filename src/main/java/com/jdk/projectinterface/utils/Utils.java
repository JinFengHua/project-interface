package com.jdk.projectinterface.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * 上传图像至服务器
     * @param file 上传的图片文件
     * @param request HttpServletRequest
     * @param parentPath 想要存储的文件夹名称
     * @return 存储的映射路径
     */
    public static String saveImage(MultipartFile file, HttpServletRequest request,String parentPath){
        if (file.isEmpty()){
            return null;
        }
        String path = "src/main/resources/static/"+ parentPath + "/";
        File folder = new File(path);
        if (!folder.isDirectory()){
            folder.mkdirs();
        }
        String ext = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        String photoPath = UUID.randomUUID().toString() + ext;
        try {
            File newFile = new File(folder.getAbsolutePath() + File.separator + photoPath);
            file.transferTo(newFile);
            return request.getScheme() + "://" + request.getServerName() + ":" +request.getServerPort() + "/image/" + parentPath + "/" + photoPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除图片
     * @param path 图片的路径
     */
    public static void deleteImage(String path , String parentPath){
        String p = "src/main/resources/static/"+ parentPath + path.substring(path.lastIndexOf("/"));
        File file = new File(p);
        if (!path.isEmpty()){
            file.delete();
        }
    }

    /**
     * 判断对象是否为空
     * @param o 需要判断的对象
     * @return 为空返回true, 不为空返回false
     */
    public static boolean isEmpty(Object o) {
        if (o == null || Objects.isNull(o)) {
            return true;
        }
        if (o instanceof String) {
            if ("".equals(o.toString().trim())) {
                return true;
            }
            return "undefined".equals(o.toString().trim());
        } else if (o instanceof List) {
            return ((List) o).size() == 0;
        } else if (o instanceof Map) {
            return ((Map) o).size() == 0;
        } else if (o instanceof Set) {
            return ((Set) o).size() == 0;
        } else if (o instanceof Object[]) {
            return ((Object[]) o).length == 0;
        } else if (o instanceof int[]) {
            return ((int[]) o).length == 0;
        } else if (o instanceof long[]) {
            return ((long[]) o).length == 0;
        }
        return false;
    }

    /**
     * 验证手机号码
     * @param mobiles
     * @return
     */
    public static boolean isPhone(String mobiles){
        boolean flag = false;
        try{
            String pattern = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 验证邮箱地址是否正确
     * @param email
     * @return
     */
    public static boolean IsEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
