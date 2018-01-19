package com.example.demo;

import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class TestJarOriginalPath {
    public static void main(String[] args) throws FileNotFoundException {
        /*String s = "name.xml";
        System.out.println(s.matches(".xml"));*/
        File path = new File(ResourceUtils.getURL("classpath:").getHost());
        File path1 = new File(ResourceUtils.getURL("classpath:").getPath());
//        if(!path.exists()) path = new File("");
        System.out.println("path:" + path.getAbsolutePath());//path:E:\SpringBootTest
        System.out.println("path:" + path1.getAbsolutePath());//path:E:\SpringBootTest\target\test-classes
        System.out.println("path:" + path1.getPath());//path:E:\SpringBootTest\target\test-classes


        //如果上传目录为/static/images/upload/，则可以如下获取：
        File upload = new File(path.getAbsolutePath(), "static/images/upload/");
//        if(!upload.exists()) upload.mkdirs();
        System.out.println("upload url:" + upload.getAbsolutePath());
        //在开发测试模式时，得到的地址为：{项目跟目录}/target/static/images/upload/
        //在打包成jar正式发布时，得到的地址为：{发布jar包目录}/static/images/upload/

        /**
         另外使用以上代码需要注意，因为以jar包发布时，我们存储的路径是与jar包同级的static目录，因此我们需要在jar包目录的application.properties配置文件中设置静态资源路径，如下所示：
         # 设置静态资源路径，多个以逗号分隔
         spring.resources.static-locations=classpath:static/,file:static/
         */

        //获得项目的绝对路径，然后拼装配置文件的路径。
        String filePath = System.getProperty("user.dir");
        System.out.println(filePath);//E:\SpringBootDemo

        File path2 = new File(ResourceUtils.getURL("").getPath());
        System.out.println("path2:" + path.getAbsolutePath());//path2:E:\SpringBootDemo

        String url = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        System.out.println(url);
    }
}
