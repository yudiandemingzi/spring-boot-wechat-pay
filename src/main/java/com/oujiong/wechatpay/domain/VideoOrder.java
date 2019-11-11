package com.oujiong.wechatpay.domain;


import lombok.Data;

import java.io.Serializable;

/**
 * 订单表
 */
@Data
public class VideoOrder implements Serializable {

  private Integer id;
  private String openid;

  private String outTradeNo;
  /**
   * 0表示未支付，1表示已经支付
   */
  private Integer state;
  private java.util.Date createTime;
  private java.util.Date notifyTime;
  /**
   *分为单位
   */
  private Integer totalFee;
  private String nickname;
  private String headImg;
  private Integer videoId;
  private String videoTitle;
  private String videoImg;
  private Integer userId;
  private String ip;
  private Integer del;


}
