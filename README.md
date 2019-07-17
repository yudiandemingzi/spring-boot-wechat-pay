整个扫码支付功能根据 微信官方扫描支付API 实现

[微信扫码支付官方文档](https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5)

针对`微信扫码支付功能` 下面都有博客详细的说明

1、[微信扫码支付功能（1）---通过谷歌二维码工具生成付款码](https://www.cnblogs.com/qdhxhz/p/9708534.html)

2、[[微信扫码支付功能（2）---用户扫码支付成功,微信异步回调商户接口](https://www.cnblogs.com/qdhxhz/p/9716216.html)

#### 技术架构

项目总体技术选型

```
SpringBoot2.0.5 + Maven3.5.4 + Mybatis + lombok(插件) + ngrok
```

跑项目之前，先执行代码里提供的sql文件，创相关表

然后就可以启动项目。

`注意`

为了能够实现本地调试，这里用了ngrok穿透技术，它的作用就是：

当用户下单成功后，微信异步回调商户接口的时候，通过ngrok工具就可以回调到本地，方便本地调试。

有关ngrok如何使用，自己也写了篇博客详细讲解：

[本地调试工具ngrok、微信回调ngrok域名](https://www.cnblogs.com/qdhxhz/p/9678137.html)


