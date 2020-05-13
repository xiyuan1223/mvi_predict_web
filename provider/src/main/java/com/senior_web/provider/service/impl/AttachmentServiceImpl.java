package com.senior_web.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.senior_web.common.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service(version = "1.0.0")
public class AttachmentServiceImpl implements AttachmentService, Serializable {
    @Autowired
    private Environment env;

    public static String newsImage = "protempfile";

    public Map<String, String> ckEditorUploadImage(byte[] bytes,String originName){


        String originalName = originName;
        Object obj = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
            obj = (File)ois.readObject();




        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(obj==null || "".equals(originName.trim())) {
            return generateResult(false, "#");
        }

        // generate file name
        String localFileName = System.currentTimeMillis() + "-" + originalName;
        // get project path
//        File resourcefile = new File("src/main/resources/static/");
        //传到项目所在目录同级目录下
        File resourcefile = new File("tmp_file");
        String projectRealPath = resourcefile.getAbsolutePath();
//        String projectRealPath = env.getProperty("upload.path");
        // get the real path to store received images

        //存放文件的本地文件夹
        String realPath = projectRealPath+File.separator+newsImage;
        File imageDir = new File(realPath);
        if(!imageDir.exists()) {//文件夹
            imageDir.mkdirs();
        }

        //获取本地路径
        boolean fileStoreTag = false;
        String localFilePath = realPath + File.separator + localFileName;
        try {
            ((File) obj).renameTo(new File(localFilePath));
//             fileStoreTag= WriteObject((File)obj,localFilePath);
        } catch (Exception e) {
            System.out.println("文件创建失败");
            e.printStackTrace();

            // log here
        }
        //上传的网络路径(配置了替换)
        String imageContextPath ="/images/" +newsImage+"/"+ localFileName;
        // log here +
        System.out.println("received file original name: " + originalName);
        System.out.println("stored local file name: " + localFileName);
        System.out.println("provider 本地路径: " + localFilePath);
        System.out.println("网络路径: " + imageContextPath);
        // log here -

        Map<String,String> acceptDataStatus = new HashMap<String,String>();
        acceptDataStatus.put("localFilePath",localFileName);

        return acceptDataStatus;
    }

    private Map<String, String> generateResult(boolean uploaded, String relativeUrl){
        Map<String, String> result = new HashMap<String, String>();
        result.put("uploaded", uploaded + "");
        result.put("url", relativeUrl);

        return result;
    }





    /**
     * 将文件写入指定的路径
     * @param file File 对象
     * @param path 磁盘路径
     */
    public  boolean WriteObject(File file,String path) {

        ObjectOutputStream oos =null;
        try {
            // 把对象写入到文件中，使用ObjectOutputStream

            oos = new ObjectOutputStream(
                    new FileOutputStream(path));
            oos.writeObject(file);
            // 把对象写入到文件中
            System.out.println("写入文件完毕！");
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage() + "错误！");
            return false;
        }finally{
            try {
                oos.close();//关闭输出流
            } catch (IOException e) {
                System.out.println("关闭文件出错");
            }

        }
    }





}
