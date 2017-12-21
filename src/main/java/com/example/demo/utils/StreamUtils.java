package com.example.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by 寇含尧 on 2017/10/27.
 */
public class StreamUtils {
    /**
     * 读取输入流的内容
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] inputStreamToByte(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
