package com.example.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * 文件的Controller
 * Created by admin on 2017/5/16.
 *
 * @author Aboo
 */
@Controller
@RequestMapping("/file")
public class FileController {
    private Logger log = LoggerFactory.getLogger(FileController.class);

    /**
     * 下载文件  (IO)
     *
     * @param request
     * @param response
     * @throws IOException 下载文件过程中，IO操作出现异常时，抛出异常
     */
    @RequestMapping(value = "/downloadIo", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long a = System.currentTimeMillis();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String url = "E:\\tmp\\zip\\ideaIU-2018.1.3.win.zip";
        String filename = "ideaIU-寇io.zip";
        File file = new File(url);
        if (file.exists()) {
            response.setContentType("application/x-zip-compressed");// 设置Content-Type为文件的MimeType
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1"));// 设置文件名
            response.setContentLength((int) file.length());

            //JAVA IO
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file), 2097152);
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            /*OutputStream os = new BufferedOutputStream(response.getOutputStream(), 2097152);
            byte[] bytes = new byte[2097152];
            int i = 0;
            while ((i = inputStream.read(bytes)) > 0) {
                os.write(bytes, 0, i);
            }
            os.flush();
            if (os != null)
                os.close();
            if (inputStream != null)
                inputStream.close();*/
            log.debug("download succeed! ---" + filename);
        }
        long b = System.currentTimeMillis();
        System.out.println("io download takes :" + (b - a));
    }

    /**
     * 下载文件 (NIO)
     *
     * @param request
     * @param response
     * @throws IOException 下载文件过程中，IO操作出现异常时，抛出异常
     */
    @RequestMapping(value = "/downloadNio", method = RequestMethod.GET)
    public void nioDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long a = System.currentTimeMillis();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String url = "E:\\tmp\\zip\\ideaIU-2018.1.3.win.zip";
        String filename = "ideaIU-寇nio.zip";
        File file = new File(url);

        if (file.exists()) {
            response.setContentType("application/x-zip-compressed");// 设置Content-Type为文件的MimeType
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1"));// 设置文件名
            response.setContentLength((int) file.length());
            OutputStream os = new BufferedOutputStream(response.getOutputStream(), 2097152);
            //NIO 实现
            int bufferSize = 2097152;
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel fileChannel = fileInputStream.getChannel();
            // 6x128 KB = 768KB byte buffer
            ByteBuffer buff = ByteBuffer.allocateDirect(2097152);
            byte[] byteArr = new byte[bufferSize];
            int nRead, nGet;

            try {
                while ((nRead = fileChannel.read(buff)) != -1) {
                    if (nRead == 0) {
                        continue;
                    }
                    buff.position(0);
                    buff.limit(nRead);
                    while (buff.hasRemaining()) {
                        nGet = Math.min(buff.remaining(), bufferSize);
                        // read bytes from disk
                        buff.get(byteArr, 0, nGet);
                        // write bytes to output
                        os.write(byteArr);
                        //response.getOutputStream().write(byteArr);
                    }
                    buff.clear();
                }
                os.flush();
                log.debug("download succeed! ---" + filename);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                buff.clear();
                os.close();
                fileChannel.close();
                fileInputStream.close();
            }

        }
        long b = System.currentTimeMillis();
        System.out.println("nio download takes :" + (b - a));
    }
}

