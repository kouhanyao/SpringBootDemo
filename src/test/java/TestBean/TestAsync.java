package TestBean;

import com.example.demo.ConfigSettings.ConfigXmlFile;
import com.example.demo.controller.TestAsyncController;
import com.example.demo.controller.TestValueController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * Created by 寇含尧 on 2017/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DemoApplication.class) // 指定我们SpringBoot工程的Application启动类
//@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
//@ContextConfiguration(locations={"classpath:springmvc-servlet-test.xml", "classpath:application-context-datasource-test.xml"})//加载配置文件
//@ContextConfiguration({"classpath:/applicationContext.xml"})
@ContextConfiguration(classes = {ConfigXmlFile.class})
//@ActiveProfiles("prod")
public class TestAsync {

    @Autowired
    public TestAsyncController testAsyncBean;

    @Autowired
    public TestValueController testValue;


    @org.junit.Test
    public void test_sayHello1() throws InterruptedException, ExecutionException {
        Future<String> future = null;
        System.out.println("你不爱我了么?");
        future = testAsyncBean.sayHello1();
//        System.out.println(future.get());
        System.out.println("你竟无话可说, 我们分手吧。。。");
        //Thread.sleep(3 * 1000);// 不让主进程过早结束
        System.out.println(future.get());
    }

    @org.junit.Test
    public void test_sayHello3() throws InterruptedException, ExecutionException {
        System.out.println("你不爱我了么?");
        testAsyncBean.sayHello3();
        System.out.println("你竟无话可说, 我们分手吧。。。");
        Thread.sleep(3 * 1000);// 不让主进程过早结束
    }

    @org.junit.Test
    public void test_sayHello2() throws InterruptedException, ExecutionException {
        System.out.println("你不爱我了么?");
        System.out.println(testAsyncBean.sayHello2());
        System.out.println("你说的啥? 我们还是分手吧。。。");
        Thread.sleep(3 * 1000);// 不让主进程过早结束
    }

    @org.junit.Test
    public void testValue(){
        //TestValue testValue = new TestValue();//通过new出的对象得到的值为null
        System.out.println("name="+testValue.getGgname());
    }

    @org.junit.Test
    public void test() throws InterruptedException, ExecutionException {
        System.out.println("你竟无话可说, 我们分手吧。。。");
    }

    @org.junit.Test
    public void testValueTwo(){
        //TestValueTwo testValueTwo = new TestValueTwo();//通过new出的对象得到的值为null
        System.out.println("name="+testValue.getValue());
    }

    @org.junit.Test
    public void testAssert(){

    }

    public static void main(String[] args) {
        String html = "<HTML><HEAD><TITLE></TITLE><meta http-equiv=\"cache-control\" content=\"no-cache\">\n" +
                "<meta http-equiv=\"pragma\" content=\"no-cache\">\n" +
                "<NOSCRIPT><HTML><BODY>Your browser does not seem to support JavaScript. Please make sure it is supported and activated</BODY></HTML></NOSCRIPT>\n" +
                "<SCRIPT>\n" +
                "var ie4 = (document.all)? true:false;\n" +
                "var ns6 = (document.getElementById)? true && !ie4:false;\n" +
                "function Initialize() {\n" +
                "var lWidth;\n" +
                "var lHeight;\n" +
                "if (ns6) {\n" +
                "  lWidth = window.innerWidth - 30;\n" +
                "  lHeight = window.innerHeight - 30;\n" +
                "} else {\n" +
                "  lWidth = document.body.clientWidth;\n" +
                "  lHeight = document.body.clientHeight;\n" +
                "}\n" +
                "document.forms[0].elements[\"IW_width\"].value = lWidth;\n" +
                "document.forms[0].elements[\"IW_height\"].value = lHeight;\n" +
                "document.forms[0].submit();\n" +
                "}</SCRIPT></HEAD><BODY onload=\"Initialize()\">\n" +
                "<form method=post action=\"/EXEC/0/0veh4kq0legvog194sz8x0q382x2\">\n" +
                "<input type=hidden name=\"IW_width\">\n" +
                "<input type=hidden name=\"IW_height\">\n" +
                "<input type=hidden name=\"IW_SessionID_\" value=\"0veh4kq0legvog194sz8x0q382x2\">\n" +
                "<input type=hidden name=\"IW_TrackID_\" value=\"0\">\n" +
                "</form></BODY></HTML>";
        /*String html ="<html>\n" +
                "<head>\n" +
                "    <META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; CHARSET=GB2312\">\n" +
                "    <meta http-equiv=\"cache-control\" content=\"no-cache\">\n" +
                "    <meta http-equiv=\"pragma\" content=\"no-cache\">\n" +
                "    <style type=\"text/css\">\n" +
                "        .IWLABEL2CSS {\n" +
                "            position: absolute;\n" +
                "            left: 111px;\n" +
                "            top: 131px;\n" +
                "            z-index: 100;\n" +
                "            font-style: normal;\n" +
                "            font-weight: normal;\n" +
                "            font-size: 13px;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        .IWLABEL1CSS {\n" +
                "            position: absolute;\n" +
                "            left: 129px;\n" +
                "            top: 67px;\n" +
                "            z-index: 100;\n" +
                "            font-style: normal;\n" +
                "            font-weight: normal;\n" +
                "            font-size: 24px;\n" +
                "            font-family: '黑体';\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        .IWEDIT1CSS {\n" +
                "            position: absolute;\n" +
                "            left: 207px;\n" +
                "            top: 126px;\n" +
                "            z-index: 100;\n" +
                "            width: 275px;\n" +
                "            height: 21px;\n" +
                "            font-style: normal;\n" +
                "            font-weight: normal;\n" +
                "            font-size: 13px;\n" +
                "            text-decoration: none;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        .IWBUTTON1CSS {\n" +
                "            position: absolute;\n" +
                "            left: 495px;\n" +
                "            top: 122px;\n" +
                "            z-index: 100;\n" +
                "            width: 75px;\n" +
                "            height: 25px;\n" +
                "            font-style: normal;\n" +
                "            font-weight: normal;\n" +
                "            font-size: 13px;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        .IWIMAGE1CSS {\n" +
                "            position: absolute;\n" +
                "            left: -1px;\n" +
                "            top: 254px;\n" +
                "            z-index: 100;\n" +
                "            font-style: normal;\n" +
                "            font-weight: normal;\n" +
                "            font-size: 13px;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <script type=\"text/javascript\" src=\"/js/IWPreScript.js_9.0.17\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "\n" +
                "        var TABCONTROL_IWTABCONTROL1 = null;\n" +
                "        function init_IWTABCONTROL1(AIndex) {\n" +
                "            TABCONTROL_IWTABCONTROL1 = new JSTabControl(document.getElementById(\"IWTABCONTROL1\"));\n" +
                "            TABCONTROL_IWTABCONTROL1.selectedIndex = AIndex;\n" +
                "            TABCONTROL_IWTABCONTROL1.addTabPage(document.getElementById(\"IWTABCONTROL1PAGE0\"), \"PAGETITLE_IWTABCONTROL1PAGE0\");\n" +
                "        }\n" +
                "        var IWTABCONTROL1IWCL = null;\n" +
                "        var IWTABCONTROL1PAGE0IWCL = null;\n" +
                "        var IWLABEL2IWCL = null;\n" +
                "        var IWLABEL1IWCL = null;\n" +
                "        var IWEDIT1IWCL = null;\n" +
                "        function IWEDIT1_onchange(event) {\n" +
                "            OnControlContentChange(event);\n" +
                "            return true;\n" +
                "        }\n" +
                "\n" +
                "        function IWEDIT1_onkeypress(event) {\n" +
                "            OnControlContentChange(event);\n" +
                "            return true;\n" +
                "        }\n" +
                "\n" +
                "        function IWEDIT1_onclick(event) {\n" +
                "            OnControlContentChange(event);\n" +
                "            return true;\n" +
                "        }\n" +
                "\n" +
                "        function IWEDIT1_onselect(event) {\n" +
                "            OnControlContentChange(event);\n" +
                "            return true;\n" +
                "        }\n" +
                "\n" +
                "        var IWBUTTON1IWCL = null;\n" +
                "        function IWBUTTON1_onclick(event) {\n" +
                "            return SubmitClickConfirm('IWBUTTON1', '', true, '');\n" +
                "        }\n" +
                "\n" +
                "        var IWIMAGE1IWCL = null;\n" +
                "        function FormDefaultSubmit() {\n" +
                "            return false;\n" +
                "        }\n" +
                "        var GIsPartialUpdate = false;\n" +
                "        var GOnResizetimeout = 1000;\n" +
                "        var GURLBase = \"\";\n" +
                "        var GAppID = \"1ye5a8g1w95qpd16pktdo10g39a2\";\n" +
                "        var GTrackID = 1\n" +
                "        function IWTop() {\n" +
                "            return window;\n" +
                "        }\n" +
                "        var GActiveControl = null;\n" +
                "        var GActiveControl = null;\n" +
                "        history.go(1);\n" +
                "\n" +
                "        function Validate() {\n" +
                "            return true;\n" +
                "        }\n" +
                "\n" +
                "        function InitIWCLObjects() {\n" +
                "            IWTABCONTROL1IWCL = NewIWCL(IWCLForm, \"IWTABCONTROL1\", false);\n" +
                "            if (IWTABCONTROL1IWCL != null) {\n" +
                "                IWTABCONTROL1IWCL.SetAlign(alTop);\n" +
                "                IWTABCONTROL1IWCL.SetAnchors(new CreateAnchors(true, true, true, false));\n" +
                "            }\n" +
                "            if (IWTABCONTROL1IWCL) {\n" +
                "                IWTABCONTROL1IWCL.BorderWidthPixels = 0;\n" +
                "            }\n" +
                "            IWTABCONTROL1PAGE0IWCL = NewIWCL(IWTABCONTROL1IWCL, \"IWTABCONTROL1PAGE0\", false);\n" +
                "            if (IWTABCONTROL1PAGE0IWCL != null) {\n" +
                "                IWTABCONTROL1PAGE0IWCL.SetAlign(alNone);\n" +
                "                IWTABCONTROL1PAGE0IWCL.SetAnchors(new CreateAnchors(true, true, true, true));\n" +
                "            }\n" +
                "            if (IWTABCONTROL1PAGE0IWCL) {\n" +
                "                IWTABCONTROL1PAGE0IWCL.BorderWidthPixels = 0;\n" +
                "            }\n" +
                "            IWLABEL2IWCL = NewIWCL(IWTABCONTROL1PAGE0IWCL, \"IWLABEL2\", false);\n" +
                "            IWLABEL1IWCL = NewIWCL(IWTABCONTROL1PAGE0IWCL, \"IWLABEL1\", false);\n" +
                "            IWEDIT1IWCL = NewIWCL(IWTABCONTROL1PAGE0IWCL, \"IWEDIT1\", false);\n" +
                "            if (IWEDIT1IWCL) IWEDIT1IWCL.HookEvent(\"change\", IWEDIT1_onchange);\n" +
                "            if (IWEDIT1IWCL) IWEDIT1IWCL.HookEvent(\"keypress\", IWEDIT1_onkeypress);\n" +
                "            if (IWEDIT1IWCL) IWEDIT1IWCL.HookEvent(\"click\", IWEDIT1_onclick);\n" +
                "            if (IWEDIT1IWCL) IWEDIT1IWCL.HookEvent(\"select\", IWEDIT1_onselect);\n" +
                "            IWBUTTON1IWCL = NewIWCL(IWTABCONTROL1PAGE0IWCL, \"IWBUTTON1\", false);\n" +
                "            if (IWBUTTON1IWCL) IWBUTTON1IWCL.HookEvent(\"click\", IWBUTTON1_onclick);\n" +
                "            IWIMAGE1IWCL = NewIWCL(IWCLForm, \"IWIMAGE1\", false);\n" +
                "        }\n" +
                "        function Initialize() {\n" +
                "            StaticInit();\n" +
                "            GActivateLock = true;\n" +
                "            InitRects(1336, 607);\n" +
                "            InitIWCLObjects();\n" +
                "            init_IWTABCONTROL1(0);\n" +
                "            Body_OnResize();\n" +
                "            if (document.body.leftMargin < 0 && document.body.topMargin < 0) {\n" +
                "                document.body.leftMargin = 0;\n" +
                "                document.body.topMargin = 0;\n" +
                "            }\n" +
                "            ReleaseLock();\n" +
                "        }</script>\n" +
                "    <META HTTP-EQUIV=\"MSThemeCompatible\" Content=\"yes\">\n" +
                "    <META Name=\"GENERATOR\" content=\"IntraWeb v9.0.17 Serial -1\">\n" +
                "    <script type=\"text/javascript\" src=\"/js/IWCommon.js_9.0.17\"></script>\n" +
                "\n" +
                "    <script type=\"text/javascript\" src=\"/js/IWCL.js_9.0.17\"></script>\n" +
                "\n" +
                "    <script type=\"text/javascript\" src=\"/js/IWAjax.js_9.0.17\"></script>\n" +
                "\n" +
                "    <script type=\"text/javascript\" src=\"/js/tabcontrol.js_9.0.17\"></script>\n" +
                "\n" +
                "    <script type=\"text/javascript\" src=\"/js/IWGecko.js_9.0.17\"></script>\n" +
                "</head>\n" +
                "<body onresize=\"return Body_OnResize();\" topmargin=\"0\" leftmargin=\"0\" onload=\"Initialize()\" onunload=\"ReleaseIWCL()\"\n" +
                "      onblur=\"GSubmitting = false;SetBusy(false);\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "       style=\"border-collapse: collapse; position:absolute; z-index:9000;\n" +
                "left:0px; top:0px\" bordercolor=\"#111111\" width=\"100%\" id=\"locker\"\n" +
                "       height=\"100%\">\n" +
                "    <tr>\n" +
                "        <td></td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "<div id=\"bussyCursor\"\n" +
                "     style=\"position:absolute;left:0px;top:0px;width:10000px;height:10000px;visibility:hidden;display:none;cursor:wait;z-index:10000\"></div>\n" +
                "<div style=\"position:absolute;left:-1px;top:-1px;z-index:1100;width:1336px;height:251px;font-style:normal;font-weight:normal;font-size:13px;text-decoration: none;border-style:none;border-width:0;background-color:#C0C0C0;padding:0;\"\n" +
                "     class=\"tab-pane\" id=\"IWTABCONTROL1\" name=\"IWTABCONTROL1\">\n" +
                "    <div style=\"z-index:1;white-space:nowrap;overflow:hidden;height:20;margin-top: 0;\"><input type=\"hidden\"\n" +
                "                                                                                              id=\"STYLEHOLDER_IWTABCONTROL1_ACTIVE\"\n" +
                "                                                                                              style=\"font-style:normal;font-weight:bold;font-size:13px;text-decoration: none;font-family:sans-serif;color:#FFFFFF;background-color:#A9A9A9;cursor: pointer; height: 20px; padding: 3px;\"><input\n" +
                "            type=\"hidden\" id=\"STYLEHOLDER_IWTABCONTROL1_INACTIVE\"\n" +
                "            style=\"font-style:normal;font-weight:normal;font-size:13px;text-decoration: none;font-family:sans-serif;color:#000000;background-color:#D3D3D3;cursor: pointer; height: 20px; padding: 3px;\"><span\n" +
                "            id=\"PAGETITLE_IWTABCONTROL1PAGE0\" style=\"margin-top: 0;\">查询</span></div>\n" +
                "    <form onsubmit=\"return FormDefaultSubmit();\">\n" +
                "        <input type=\"HIDDEN\" name=\"IWTABCONTROL1_input\" id=\"IWTABCONTROL1_input\" value=\"0\">\n" +
                "    </form>\n" +
                "\n" +
                "    <div style=\"z-index:2; width:1336;height:231;position: absolute;left:0;top:18;background-color:#FFFFFF;border-style:none;border-width:0;background-color:#FFFFFF;padding:0;\"\n" +
                "         class=\"tab-page\" id=\"IWTABCONTROL1PAGE0\"><span style=\"text-align:left;\" class=\"IWLABEL2CSS\" id=\"IWLABEL2\">输入检查号：</span><span\n" +
                "            style=\"text-align:left;\" class=\"IWLABEL1CSS\" id=\"IWLABEL1\">四川大学华西第四医院超声检查报告查询</span>\n" +
                "        <form onsubmit=\"return FormDefaultSubmit();\">\n" +
                "            <input type=\"TEXT\" name=\"IWEDIT1\" value=\"6229390\"\n" +
                "                class=\"IWEDIT1CSS\" id=\"IWEDIT1\" tabindex=\"1\"><input\n" +
                "                value=\"查询\" name=\"IWBUTTON1\" type=\"button\" class=\"IWBUTTON1CSS\" id=\"IWBUTTON1\" tabindex=\"2\">\n" +
                "        </form>\n" +
                "    </div>\n" +
                "</div>\n" +
                "<form onsubmit=\"return FormDefaultSubmit();\"><img src=\"/Cache/user/1ye5a8g1w95qpd16pktdo10g39a2/JPG3732.tmp\"\n" +
                "                                                  name=\"IWIMAGE1\" border=\"0\" class=\"IWIMAGE1CSS\" id=\"IWIMAGE1\"></form>\n" +
                "<form method=\"POST\" name=\"SubmitForm\" action=\"/EXEC/1/1ye5a8g1w95qpd16pktdo10g39a2\">\n" +
                "    <input type=\"HIDDEN\" name=\"IWEDIT1\">\n" +
                "    <input type=\"HIDDEN\" name=\"IWBUTTON1\">\n" +
                "    <input type=\"HIDDEN\" name=\"IWTABCONTROL1_input\">\n" +
                "    <input type=\"HIDDEN\" name=\"IW_Action\">\n" +
                "    <input type=\"HIDDEN\" name=\"IW_ActionParam\">\n" +
                "    <input type=\"HIDDEN\" name=\"IW_FormName\" value=\"IWForm1\">\n" +
                "    <input type=\"HIDDEN\" name=\"IW_FormClass\" value=\"TIWForm1\">\n" +
                "    <input type=\"HIDDEN\" name=\"IW_width\">\n" +
                "    <input type=\"HIDDEN\" name=\"IW_height\">\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";*/
        Document doc = Jsoup.parseBodyFragment(html);
        /*Element body = doc.body();
        System.out.println();*/
        //获取 带有src属性的img元素
        /*Elements imgTags = doc.select("img[src]");
        System.out.println("=====imgsTag===="+imgTags);
        for(Element element:imgTags){
            String src=element.attr("abs:src");//获取src的绝对路径
            String src2=element.attr("src");//获取src的绝对路径
            System.out.println("===src==="+src);
            System.out.println("===src2==="+src2);
        }*/


        Elements imgTags = doc.select("form[action]");
        System.out.println("=====formTag===="+imgTags);
        for(Element element:imgTags){
            String src=element.attr("abs:action");//获取src的绝对路径
            String src2=element.attr("action");//获取src的绝对路径
            System.out.println("===src==="+src);
            System.out.println("===src2==="+src2);
        }
    }
}
