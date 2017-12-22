package com.example.demo.utils;

import com.example.demo.domain.Msg;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by 寇含尧 on 2017/12/22.
 *
 * 本工具使用ServletOutputStream分段的往浏览器flush数据。
 * 调用方式：先new CSV（），传入指定参数，不断的调用wirte()方法往浏览器写入数据，
 * 最后调用close方法关闭流。
 *
 * 本工具导出的文件格式为.csv文件，windows office工具默认编码为ASCI，wps会匹配各种编码，libreOffice calc可以指定编码，
 * 故此设置编码为GBK,兼容三种Excel软件，也可根据自身需求设置编码。
 */
public class CSVUtils {
    /**
     * 目标输出流
     */
    private OutputStream stream;

    /**
     * 表头(第一个String必须和类的成员变量名相同,第二个string代表表头名)
     */
    private LinkedHashMap<String, String> fields;

    /**
     * 类demo所有字段map
     */
    private  Map<String, Field> fieldMap = new HashMap<>();

    public CSVUtils(HttpServletResponse response, LinkedHashMap<String, String> fields, String fileName, Class<?> clz) throws IOException {
        if (response == null || fields == null || fileName == null || clz == null)
            throw new IllegalArgumentException();
        getFieldMap(clz, this.fieldMap);
        this.stream = response.getOutputStream();
        this.fields = fields;
        response.setContentType("application/octet-stream;charset=GBK");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName.concat(".csv"));
        //写表头，生成指定名字的文件，返回客户端
        StringBuilder hb = new StringBuilder();
        for (Entry<String, String> e : fields.entrySet())
            hb.append(e.getValue() + ",");
        this.stream.write(hb.substring(0, hb.length() - 1).getBytes("GBK"));
        this.stream.flush();
    }

    /**
     * 往表格中插入记录
     */
    public void write(List<Object> data) throws IllegalArgumentException, IllegalAccessException, IOException {
        for (Object o : data) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for (String field : this.fields.keySet()) {
                Field f = this.fieldMap.get(field);
                //让我们在用反射时访问私有变量
                f.setAccessible(true);
                Object value = f.get(o);
                if (value == null || StringUtils.isBlank(value.toString())) {
                    sb.append(" ,");
                } else if (f.getType() == Date.class) {
                    sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value) + ",");
                } else if (f.getType() == DateTime.class) {
                    sb.append(((DateTime) value).toString("yyyy-MM-dd HH:mm:ss") + ",");
                } else {
                    String tmp = value.toString();
                    //如果有逗号
                    if(tmp.contains(",")){
                        //如果还有双引号，先将双引号转义，避免两边加了双引号后转义错误
                        if(tmp.contains("\"")){
                            tmp = tmp.replace("\"", "\"\"");
                        }
                        //在将逗号转义
                        tmp = "\"".concat(tmp).concat("\"");
                    }
                    sb.append(tmp + ",");
                }
            }
            this.stream.write(sb.substring(0, sb.length() - 1).getBytes("GBK"));
            this.stream.flush();
        }
    }

    /**
     * 关闭流
     * @throws IOException
     */
    public void close() throws IOException {
        this.stream.close();
    }

    /**
     * 把类中的成员变量添加到Map中
     * @param clz
     * @param result
     * @param <T>
     */
    private static <T extends Object> void getFieldMap(Class<T> clz, Map<String, Field> result) {
        for (Field field : clz.getDeclaredFields()) {
            result.put(field.getName(), field);
        }
        if (clz.getSuperclass() != null) {
            getFieldMap(clz.getSuperclass(), result);
        }
    }

    public static void main(String[] args) throws IOException, IllegalAccessException {
        /*Msg msg = new Msg("123456789","111,555,55\"jhh\"","aaa");
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
        csvUtils.write(list);*/
    }
}
