package com.pn.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {


    //注入验证码工具类
    @Resource(name = "captchaProducer")
    private Producer producer;

    //注入redis模板类
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    //生成验证码接口
    @RequestMapping("/captcha/captchaImage")
    public void getCaptchaImage(HttpServletResponse response) {

        ServletOutputStream outputStream = null;

        try {
            //生成验证码//生成验证码
            String capText = producer.createText();
            //使用producer生成内存中的图片
            BufferedImage image = producer.createImage(capText);
            //将验证码存入redis
            stringRedisTemplate.opsForValue().set(capText, capText, 30 * 60, TimeUnit.SECONDS);
            //通过response将验证码返回前端
            response.setContentType("image/jpeg");
            outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
