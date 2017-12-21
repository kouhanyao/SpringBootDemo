package com.example.demo.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeHelper
{

    public static boolean isNull(Object param)
    {
        return null == param;
    }
    public static boolean strisNull(Object param)
    {
        if(null == param){
            return  true;
        }
        return "".equals(param.toString());
    }

    public static boolean isNotNull(Object param)
    {
        return null != param;
    }

    public static boolean isNotNullOrEmpty(List<? extends Object> list)
    {
        return CodeHelper.isNotNull(list) && !list.isEmpty();
    }
    public static boolean strisNull(String str)
    {
        return str == null || "".equals(str);
    }

    public static boolean isNotNullOrEmpty(Map<? extends Object, ? extends Object> map)
    {
        return CodeHelper.isNotNull(map) && !map.isEmpty();
    }

    public static boolean isNotNullOrEmpty(String str)
    {
        return CodeHelper.isNotNull(str) && !str.trim().isEmpty();
    }

    public static boolean isNotNullOrEmpty(Integer params)
    {
        return CodeHelper.isNotNull(params) && params.intValue() > 0;
    }

    public static boolean isNotNullOrEmpty(Object[] params)
    {
        return CodeHelper.isNotNull(params) && params.length > 0;
    }

    public static boolean isNullOrEmpty(String str)
    {
        return null == str || str.isEmpty();
    }

    public static boolean isNullOrEmpty(List<? extends Object> params)
    {
        return null == params || params.isEmpty();
    }

    public static boolean isNullOrEmpty(Object[] params)
    {
        return null == params || params.length == 0;
    }
    
    
    public static String trim(String param,String charSequence)
    {
        if(isNotNullOrEmpty(param) && isNotNullOrEmpty(charSequence))
        {
            //去除首字符
            if(param.indexOf(charSequence) == 0)
            {
                param = param.substring(1);
            }
            
            //去除尾字符
            if(param.endsWith(charSequence))
            {
                param = param.substring(0,param.length() - 1);
            } 
        }
        
        return param;
    }
    
    
    /**
     * 生成日期加随机数的序列号
     *
     * @author fangsj
     * @return
     * @throws
     */
    public static String generateSerialNumber()
    {
        String date = DateHelper.formatDate(new Date(), "yyyyMMddHHmmss");
        String random = String.valueOf(Math.random());
        random = random.substring(2, 9);
        return date + random;
    }


    /**
     * 判断字符串是不是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }


    /**
     * 返回对象
     * @param obj
     * @return
     */
    public static Object getObject(Object obj){
        if(obj instanceof String){
            return obj==null?"":((String)obj).trim();
        }
        else {
            return obj==null?"":obj;
        }
    }




}
