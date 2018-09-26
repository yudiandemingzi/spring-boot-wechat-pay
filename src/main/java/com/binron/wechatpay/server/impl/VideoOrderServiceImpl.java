package com.binron.wechatpay.server.impl;

import com.binron.wechatpay.config.WeChatConfig;
import com.binron.wechatpay.domain.User;
import com.binron.wechatpay.domain.Video;
import com.binron.wechatpay.domain.VideoOrder;
import com.binron.wechatpay.dto.VideoOrderDto;
import com.binron.wechatpay.mapper.UserMapper;
import com.binron.wechatpay.mapper.VideoMapper;
import com.binron.wechatpay.mapper.VideoOrderMapper;
import com.binron.wechatpay.server.VideoOrderService;
import com.binron.wechatpay.utils.CommonUtils;
import com.binron.wechatpay.utils.HttpUtils;
import com.binron.wechatpay.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {


    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String save(VideoOrderDto videoOrderDto) throws Exception {

        //1、查找商品信息（这里商品指的是视频课程）
        Video video =  videoMapper.findById(videoOrderDto.getVideoId());

        //2、查找用户信息
        User user = userMapper.findByid(videoOrderDto.getUserId());

        //3、生成订单，插入数据库
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setVideoId(video.getId());
        videoOrder.setState(0);
        videoOrder.setUserId(user.getId());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setNickname(user.getName());
        videoOrder.setDel(0);
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setOutTradeNo(CommonUtils.generateUUID());

        videoOrderMapper.insert(videoOrder);

        //4、获取codeurl
        String codeUrl = unifiedOrder(videoOrder);

        return codeUrl;
    }


    /**
     * 统一下单方法
     * @return
     */
    private String unifiedOrder(VideoOrder videoOrder) throws Exception {


        //4.1、生成签名 按照开发文档需要按字典排序，所以用SortedMap
        SortedMap<String,String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id", weChatConfig.getMchId());
        params.put("nonce_str", CommonUtils.generateUUID());
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no",videoOrder.getOutTradeNo());
        params.put("total_fee",videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip",videoOrder.getIp());
        params.put("notify_url",weChatConfig.getPayCallbackUrl());
        params.put("trade_type","NATIVE");

        //4.2、sign签名 具体规则:https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_3
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign",sign);

        //4.3、map转xml （ WXPayUtil工具类）
        String payXml = WXPayUtil.mapToXml(params);

        //4.4、回调微信的统一下单接口(HttpUtil工具类）
        String orderStr = HttpUtils.doPost(WeChatConfig.getUnifiedOrderUrl(),payXml,4000);
        if(null == orderStr) {
            return null;
        }
        //4.5、xml转map （WXPayUtil工具类）
        Map<String, String> unifiedOrderMap =  WXPayUtil.xmlToMap(orderStr);
        System.out.println(unifiedOrderMap.toString());

        //4.6、获取最终code_url
        if(unifiedOrderMap != null) {
            return unifiedOrderMap.get("code_url");
        }

        return null;
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {

        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public int updateVideoOderByOutTradeNo(VideoOrder videoOrder) {

        return videoOrderMapper.updateVideoOderByOutTradeNo(videoOrder);
    }





}
