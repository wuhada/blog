package com.gz.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @authod wu
 * @date 2020/6/11 22:44
 */
@Controller
public class TestController {

    //@Value("${web.upload-path}")
    private String path;

    /**
     * 跳转到文件上传页面
     * @return
     */
    @RequestMapping("test")
    public String toUpload(){
        return "index";
    }

    /**
     *
     * @param file 要上传的文件
     * @return
     */
    @RequestMapping("fileUpload")
    public String upload(@RequestParam("avatar") MultipartFile file, HttpSession session){

        // 要上传的目标文件存放路径
        String localPath = "E:/image";
        // 上传成功或者失败的提示
        String msg = "";

        if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
            // 上传成功，给出页面提示
            msg = "上传成功！";
        }else {
            msg = "上传失败！";

        }

        session.setAttribute("fileName",file.getOriginalFilename());
        return "forward:/test";
    }


    @RequestMapping(value = "/get")
    public String getImage(HttpSession session) {
        return "redirect:/image/" + session.getAttribute("fileName");
    }


}