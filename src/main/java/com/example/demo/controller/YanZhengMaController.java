package com.example.demo.controller;

import com.example.demo.constant.ResultCode;
import com.example.demo.system.MessageResult;
import com.example.demo.utils.ToolUtil;
import com.example.demo.utils.VerifyCodeUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class YanZhengMaController {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    /**
     * 验证码接口
     *
     * @param request
     * @param response
     */
    @RequestMapping("/code")
    @ResponseBody
    public MessageResult code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String mobile = (String) request.getAttribute("mobile");
        if (ToolUtil.isNull(mobile)) {
            return MessageResult.warpper(ResultCode.NOT_LOGGED);
        }

        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //放入redis中
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        try {
            connection.hSet("VERIFY_CODE".getBytes(), mobile.getBytes(), verifyCode.getBytes()
            );
            connection.expire("VERIFY_CODE".getBytes(), 30);// 30秒有效

        } catch (Exception e) {
            logger.error("", e);
        } finally {
            connection.close();
        }

        //生成图片
        int w = 120, h = 50;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        return null;
    }
}
