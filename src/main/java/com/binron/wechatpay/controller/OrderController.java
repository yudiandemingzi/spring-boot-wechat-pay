package com.binron.wechatpay.controller;

import com.binron.wechatpay.dto.VideoOrderDto;
import com.binron.wechatpay.server.VideoOrderService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单接口
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {


    @Autowired
    private VideoOrderService videoOrderService;

    /**
     * 用户点击购买下单接口
     */
    @GetMapping("buy")
    public void saveOrder(@RequestParam(value = "video_id",required = true)int videoId,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        /**
         * 实际开发需要获取用户id和用户当前ip，这里临时写死的配置
         * String ip = IpUtils.getIpAddr(request);
         * int userId = request.getAttribute("user_id");
         */
        int userId = 1;
        String ip = "120.25.1.43";
        //1、根据用户id和商品id生成订单
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(userId);
        videoOrderDto.setVideoId(videoId);
        videoOrderDto.setIp(ip);

        //2、保存订单同时返回codeUrl
        String codeUrl = videoOrderService.save(videoOrderDto);
        if(codeUrl == null) {
            throw new  NullPointerException();
        }

        //3、通过google工具生成二维码供用户扫码支付
         try{
            //3、1生成二维码配置
            Map<EncodeHintType,Object> hints =  new HashMap<>();

            //3、2设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);

            //3、3编码类型
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl,BarcodeFormat.QR_CODE,400,400,hints);
            OutputStream out =  response.getOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,"png",out);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
