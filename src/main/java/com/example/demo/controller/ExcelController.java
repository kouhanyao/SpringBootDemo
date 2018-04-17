package com.example.demo.controller;

import com.example.demo.utils.ReadExcelUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 寇含尧 on 2017/12/26.
 */
@Controller
@RequestMapping("excel")
public class ExcelController {

    /**
     * 读取excel
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("readExcel")
    @ResponseBody
    public Map<String, Object> sendMsgToDoctor(HttpServletRequest request) throws Exception {
        Map<String, MultipartFile> apps = ((MultipartHttpServletRequest) request).getFileMap();
        MultipartFile app = apps.get("file");
        InputStream inputStream = app.getInputStream();
        //the name of the parameter (never null or empty)
        String paramName = app.getName();
        //获取文件名称
        String fileName = app.getOriginalFilename();
        ReadExcelUtils.read(inputStream, fileName);
        Map<String, Object> map = new HashMap<>();
        map.put("errorNum", 0);
        return map;
    }


}
