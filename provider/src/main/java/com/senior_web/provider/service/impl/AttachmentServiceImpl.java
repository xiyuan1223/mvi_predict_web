package com.senior_web.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.senior_web.common.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Service(version = "1.0.0")
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private Environment env;

    public static String newsImage = "newsImage";

    public Map<String, String> ckEditorUploadImage(MultipartFile file, HttpServletRequest request) {
        if(file==null || "".equals(file.getOriginalFilename().trim())) {
            return generateResult(false, "#");
        }
        String originalName = file.getOriginalFilename();
        // generate file name
        String localFileName = System.currentTimeMillis() + "-" + originalName;
        // get project path
//        File resourcefile = new File("src/main/resources/static/");
        //传到项目所在目录同级目录下
        File resourcefile = new File("./emi_resource");
        String projectRealPath = resourcefile.getAbsolutePath();
//        String projectRealPath = env.getProperty("upload.path");
        // get the real path to store received images
        //存放图片的本地文件夹
        String realPath = projectRealPath+File.separator+newsImage;
        File imageDir = new File(realPath);
        if(!imageDir.exists()) {//文件夹
            imageDir.mkdirs();
        }
        //上传的本地路径
        String localFilePath = realPath + File.separator + localFileName;
        try {
            file.transferTo(new File(localFilePath));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            // log here
        } catch (IOException e) {
            e.printStackTrace();
            // log here
        }
        //上传的网络路径(配置了替换)
        String imageContextPath ="/images/" +newsImage+"/"+ localFileName;
        // log here +
        System.out.println("received file original name: " + originalName);
        System.out.println("stored local file name: " + localFileName);
        System.out.println("f本地路径: " + localFilePath);
        System.out.println("网络路径: " + imageContextPath);
        // log here -
        return generateResult(true, imageContextPath);
    }

    private Map<String, String> generateResult(boolean uploaded, String relativeUrl){
        Map<String, String> result = new HashMap<String, String>();
        result.put("uploaded", uploaded + "");
        result.put("url", relativeUrl);

        return result;
    }
}
