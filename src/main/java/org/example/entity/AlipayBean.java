package org.example.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by cld on 2024/3/29 13:34
 */
@Data
public class AlipayBean implements Serializable {

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 订单名称
     */
    private String subject;

    /**
     * 付款金额
     */
    private String total_amount;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 产品编号，支付方式不同，传的数据不同
     */
    //如果是PC网页支付，这个是必传参数
    private String product_code = "FAST_INSTANT_TRADE_PAY";
    //如果是扫码支付，这个是选传参数
    //private String product_code = "FACE_TO_FACE_PAYMENT";

    private String timeout_express;
}
