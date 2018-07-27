package com.firstProject.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * created by 吴家俊 on 2018/7/25.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
