package com.senior_web.common.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AttachmentService {

    public Map<String, String> ckEditorUploadImage(MultipartFile file, HttpServletRequest request);

}


