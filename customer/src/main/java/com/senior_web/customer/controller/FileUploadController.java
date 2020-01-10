package com.senior_web.customer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.senior_web.common.service.AttachmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
@Controller
@RequestMapping("/file")
public class FileUploadController {
    @Resource
    @Reference(version = "1.0.0")
    AttachmentService attachmentService;

    @RequestMapping(value="/uploadImage",method= RequestMethod.POST)
    @ResponseBody
    public void receiveImage(@RequestPart("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {



        try {
            //对象流的传输需要序列化，不能直接getBytes,而应该使用ObjectOutputStrem.write()写入
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
            oos.writeObject(file.getBytes());
            oos.writeObject(null);
            oos.close();
            Map<String, String> map = attachmentService.ckEditorUploadImage(byteArrayOutputStream.toByteArray());
            PrintWriter out = response.getWriter();
            String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
            String imgUrl = map.get("url");
            System.out.println(imgUrl + "上传图片路径");
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction("
                    + CKEditorFuncNum + ",'" + imgUrl + "','')");
            out.println("</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}