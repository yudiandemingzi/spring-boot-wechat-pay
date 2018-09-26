package com.binron.wechatpay.server;


import com.binron.wechatpay.domain.VideoOrder;
import com.binron.wechatpay.dto.VideoOrderDto;

/**
 * 订单接口
 */
public interface VideoOrderService {

    /**
     * 下单接口
     * @param videoOrderDto
     * @return
     */
    String save(VideoOrderDto videoOrderDto) throws Exception;


    /**
     * 根据流水号查找订单
     * @param outTradeNo
     * @return
     */
    VideoOrder findByOutTradeNo(String outTradeNo);


    /**
     * 根据流水号更新订单
     * @param videoOrder
     * @return
     */
    int updateVideoOderByOutTradeNo(VideoOrder videoOrder);

}
