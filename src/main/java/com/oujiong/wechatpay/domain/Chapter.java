package com.oujiong.wechatpay.domain;


import lombok.Data;

import java.io.Serializable;

/**
 * 章实体类
 */
@Data
public class Chapter implements Serializable {

  private Integer id;
  private Integer videoId;
  private String title;
  private Integer ordered;
  private java.util.Date createTime;



}
