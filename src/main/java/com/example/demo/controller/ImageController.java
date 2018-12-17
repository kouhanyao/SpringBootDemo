package com.example.demo.controller;

import com.example.demo.utils.ImageUtils;
import com.example.demo.utils.StreamUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by 寇含尧 on 2017/10/27.
 */
@RestController
@RequestMapping(value = "image")
public class ImageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "getImage")
    public void getImage(HttpServletResponse response) throws Exception {
        BufferedImage bufferedImage = createHeChengImage();
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = ImageUtils.bufferedImageToInputStream(bufferedImage);
        outputStream.write(StreamUtils.inputStreamToByte(inputStream));
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    public BufferedImage createHeChengImage() throws Exception {
        //合成图片
        //主图片的路径
        URL baseUrl = new URL(ImageUtils.baseUrl);
        BufferedImage buffImg = ImageIO.read(baseUrl);
        //得到画笔对象
        Graphics g = buffImg.getGraphics();
        //设置颜色。
        g.setColor(Color.white);

        //创建附加的图象
        //头像的路径
        URL headUrl = new URL("http://leleyun.oss-cn-beijing.aliyuncs.com/images/weixin/1.jpg");
        BufferedImage headBufferedImage = ImageIO.read(headUrl);
        try {
            headBufferedImage = ImageUtils.changeImageShape(headBufferedImage, 150);
        } catch (Exception e) {
            logger.error("将图片转成圆形异常", e);
        }
        //将头像图片绘到大图片上。
        //71,110 .表示小图片在大图片上的位置。
        g.drawImage(headBufferedImage, 71, 110, null);


        //获取二维码图像数据缓冲区
        BufferedImage qrCodeBufferedImage = null;
        try {
            qrCodeBufferedImage = ImageUtils.createQrcode("http://leleyun.oss-cn-beijing.aliyuncs.com/images/weixin/1.jpg", 325, 325);
        } catch (Exception e) {
            logger.error("创建渠道二维码异常", e);
        }
        //将二维码图片绘到大图片上
        //100,795 .表示小图片在大图片上的位置。
        g.drawImage(qrCodeBufferedImage, 100, 795, null);


        //最后一个参数用来设置字体的大小
        Font f = new Font("微软雅黑", Font.BOLD, 50);
        Color mycolor = new Color(51, 51, 51);
        //设置颜色
        g.setColor(mycolor);
        g.setFont(f);
        //表示这段文字在图片上的位置(x,y)
        g.drawString("用户名字", 260, 177);

        f = new Font("微软雅黑", Font.PLAIN, 30);
        mycolor = Color.black;
        g.setColor(mycolor);
        g.setFont(f);
        g.drawString("向您隆重推荐" + "手机", 260, 235);

        //释放此图形的上下文以及它使用的所有系统资源。
        g.dispose();
        return buffImg;
    }

    @RequestMapping("/getQrcodeImageZip")
    public void getQrcodeImageZip(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Integer pixel = 200;
        String[] ss = {"一", "二"};
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(output);
        for (String s : ss) {
            String path = "http://weixin.qq.com/q/02TRysBrFGcdi10000M03U";
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(path, BarcodeFormat.QR_CODE, pixel, pixel, hints);
            BufferedImage image = ImageUtils.toBufferedImage(bitMatrix);
            ByteArrayOutputStream output1 = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", output1);
            byte[] data = output1.toByteArray();
            String fileName = s + ".jpg";
            out.putNextEntry(new ZipEntry(fileName));
            out.write(data);
            out.closeEntry();
            output1.flush();
            output1.close();

        }
        out.flush();
        out.close();
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode("推荐人.zip", "UTF-8") + "\"");
        //response.setHeader("Content-Disposition", String.format("attachment; %s", getDownLoadFileName("推荐人.zip",request)));
        response.setContentType("application/octet-stream;charset=UTF-8");
        outputStream.write(output.toByteArray());
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 处理下载文件名字乱码问题
     * @param filename
     * @param request
     * @return
     */
    private String getDownLoadFileName(String filename,HttpServletRequest request) {
        //Edge header  User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134
        String new_filename = null;
        try {
            new_filename = URLEncoder.encode(filename, "UTF8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String userAgent = request.getHeader("User-Agent");
        // System.out.println(userAgent);
        String rtn = "filename=\"" + new_filename + "\"";
        // 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            // IE、edge浏览器，只能采用URLEncoder编码
            if (userAgent.indexOf("msie") != -1 || userAgent.indexOf("edge") !=-1) {
                rtn = "filename=\"" + new_filename + "\"";
            }
            // Opera浏览器只能采用filename*
            else if (userAgent.indexOf("opera") != -1) {
                rtn = "filename*=UTF-8''" + new_filename;
            }
            // Safari浏览器，只能采用ISO编码的中文输出
            else if (userAgent.indexOf("safari") != -1) {
                try {
                    rtn = "filename=\""
                            + new String(filename.getBytes("UTF-8"),
                            "ISO8859-1") + "\"";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
            else if (userAgent.indexOf("applewebkit") != -1) {
                try {
                    new_filename = MimeUtility
                            .encodeText(filename, "UTF8", "B");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                rtn = "filename=\"" + new_filename + "\"";
            }
            // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
            else if (userAgent.indexOf("mozilla") != -1) {
                rtn = "filename*=UTF-8''" + new_filename;
            }
        }
        return rtn.replace("+", "%20");
    }
}
