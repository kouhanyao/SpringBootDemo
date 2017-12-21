package com.example.demo.controller;

import com.example.demo.utils.ImageUtils;
import com.example.demo.utils.StreamUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
    public void getQrcodeImageZip(HttpServletResponse response) throws Exception {
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
        response.setContentType("application/octet-stream;charset=UTF-8");
        outputStream.write(output.toByteArray());
        outputStream.flush();
        outputStream.close();
    }
}
