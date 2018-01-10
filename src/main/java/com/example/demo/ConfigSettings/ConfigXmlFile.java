package com.example.demo.ConfigSettings;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by 寇含尧 on 2017/7/17.
 */
@Configuration
@ComponentScan("com.example.demo")
@ImportResource(locations={"classpath:test.xml"})
/*ImportResouce有两种常用的引入方式：classpath和file，具体查看如下的例子：

        classpath路径：locations={"classpath:application-bean1.xml","classpath:application-bean2.xml"}

        file路径：locations= {"file:d:/test/application-bean1.xml"};*/

public class ConfigXmlFile {
}
