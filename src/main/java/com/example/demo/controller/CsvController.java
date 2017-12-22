package com.example.demo.controller;

import com.example.demo.domain.Msg;
import com.example.demo.utils.CSVUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 寇含尧 on 2017/12/22.
 */
@RestController
@RequestMapping("/excel")
public class CsvController {
    @RequestMapping(value = "")
    public void test2(HttpServletResponse response) throws IOException, IllegalAccessException {
        Msg msg = new Msg("123456789","111,555,55\"jhh\"","aaa");
        Msg msg1 = new Msg("234567","22,555,55\"jhh\"","bbb");
        Msg msg2 = new Msg("345678","333,555,55\"jhh\"","cccc");
        Msg msg3 = new Msg("456789","444,555,55\"jhh\"","ddddd");
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        //添加表头
        fields.put("title","title");
        fields.put("content","content");
        fields.put("etraInfo","etraInfo");
        CSVUtils csvUtils = new CSVUtils(response, fields, "文件标题", Msg.class);

        //添加数据
        List<Object> list = new ArrayList<>();
        list.add(msg);
        list.add(msg1);
        list.add(msg2);
        list.add(msg3);
        csvUtils.write(list);

        //关闭流
        csvUtils.close();
    }
}
