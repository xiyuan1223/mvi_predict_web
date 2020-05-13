package com.senior_web.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.senior_web.common.service.AttachmentService;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.senior_web.provider.service.impl.UnZipFile;

public class DataFlowControl {
    @Resource
    @Reference(version = "1.0.0")
    AttachmentService attachmentService;

    public Map<String, String> dataFlow(byte[] bytes, String originName) {
        String ctFilePath = "ctpath";
        File resourcefile = new File("tmp_file");
        String projectRealPath = resourcefile.getAbsolutePath();
//        String projectRealPath = env.getProperty("upload.path");
        // get the real path to store received images

        //存放文件的本地文件夹
        String ctRealPath = projectRealPath+File.separator+ctFilePath;
        File imageDir = new File(ctRealPath);
        if(!imageDir.exists()) {//文件夹
            imageDir.mkdirs();
        }


        //接收文件以zip格式存储
        Map<String, String> acceptDataStatus = attachmentService.ckEditorUploadImage(bytes, originName);
        //获取存储路径
        String zipCtFilePath = acceptDataStatus.get("localFilePath");
        //解压缩
        try{

            UnZipFile.unZipFiles(zipCtFilePath,ctRealPath);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("解压缩文件失败");
        }

        




    }

    private Map<String, String> generateResult(boolean uploaded, String relativeUrl) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("uploaded", uploaded + "");
        result.put("url", relativeUrl);

        return result;
    }
}
