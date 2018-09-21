package com.example.demo.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 寇含尧 on 2017/9/8.
 */
public class ImageUtils {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    /**
     * 微信端下载渠道二维码的主图片路径
     */
    public static final String baseUrl = "http://leleyun.oss-cn-beijing.aliyuncs.com/images/weixin/baseChannelImage.png";

    /**
     * 将图片转成圆形
     *
     * @param bufferedImage 图像数据缓冲区
     * @param pixel         像素大小
     * @return
     * @throws Exception
     */
    public static BufferedImage changeImageShape(BufferedImage bufferedImage, int pixel) throws Exception {
        //处理图片将其压缩成正方形的小图
        BufferedImage convertImage = scaleByPercentage(bufferedImage, pixel, pixel);
        //裁剪成圆形 （传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的）
        convertImage = convertCircular(convertImage);
        return convertImage;
    }

    /**
     * 导入网络图片到缓冲区
     *
     * @param imgUrl 图片路径
     * @return
     */
    public BufferedImage loadImageOfBufferedImage(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入网络图片并以流的形式返回
     *
     * @param imgName
     * @return
     */
    public static InputStream loadImageOfInputStream(String imgName) {
        try {
            URL url = new URL(imgName);
            return url.openStream();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 创建二维码图像数据缓冲区（同时去除白边）
     *
     * @param path  路径
     * @param pixel 像素大小
     * @return
     * @throws Exception
     */
    public static BufferedImage createQrcode(String path, int width ,int height) throws Exception {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(path, BarcodeFormat.QR_CODE, width, height, hints);
        bitMatrix = deleteWhite(bitMatrix);
        BufferedImage image = toBufferedImage(bitMatrix);
        return image;
    }

    /**
     * 缩小Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像
     *
     * @param inputImage 输入图像数据缓冲区
     * @param newWidth   压缩后宽度
     * @param newHeight  压缩后高度
     * @throws IOException return
     */
    public static BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight) throws Exception {
        //获取原始图像透明度类型
        int type = inputImage.getColorModel().getTransparency();
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        //开启抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //使用高质量压缩
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        BufferedImage img = new BufferedImage(newWidth, newHeight, type);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHints(renderingHints);
        graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 传入的图像必须是正方形的 才会 圆形  如果是长方形的比例则会变成椭圆的
     *
     * @param bi1 图像缓冲数据
     * @return
     * @throws IOException 异常
     */
    public static BufferedImage convertCircular(BufferedImage bi1) throws IOException {
        BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());
        Graphics2D g2 = bi2.createGraphics();
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        //设置颜色
        g2.setBackground(Color.green);
        g2.dispose();
        return bi2;
    }

    /**
     * 去除二维码白边
     *
     * @param matrix
     * @return
     */
    public static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    /**
     * 将BitMatrix转成BufferedImage
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }


    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    /**
     * 把BufferedImage转成流
     *
     * @return
     */
    public static InputStream bufferedImageToInputStream(BufferedImage buffImg) throws Exception {
        if (buffImg == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buffImg, "jpg", baos);
        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
        baos.flush();
        baos.close();
        return inputStream;
        /*inputStream.close();*/
    }

    //图片到byte数组
    public byte[] image2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    //byte数组到图片
    public void byte2image(byte[] data,String path){
        if(data.length<3||path.equals("")) return;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    //byte数组到16进制字符串
    public String byte2string(byte[] data){
        if(data==null||data.length<=1) return "0x";
        if(data.length>200000) return "0x";
        StringBuffer sb = new StringBuffer();
        int buf[] = new int[data.length];
        //byte数组转化成十进制
        for(int k=0;k<data.length;k++){
            buf[k] = data[k]<0?(data[k]+256):(data[k]);
        }
        //十进制转化成十六进制
        for(int k=0;k<buf.length;k++){
            if(buf[k]<16) sb.append("0"+Integer.toHexString(buf[k]));
            else sb.append(Integer.toHexString(buf[k]));
        }
        return "0x"+sb.toString().toUpperCase();
    }
}
