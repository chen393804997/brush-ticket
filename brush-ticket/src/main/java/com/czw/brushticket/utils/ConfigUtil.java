package com.czw.brushticket.utils;

/**
 * @author: czw
 * @create: 2018-10-02 23:20
 **/
public class ConfigUtil {
    /** 获取验证码图片地址*/
    public static final String GET_IMG = "https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&0.7870525409538782";
    /** 验证图片*/
    public static final String CAPTCHA_CHECK = "https://kyfw.12306.cn/passport/captcha/captcha-check";
    /** 登录*/
    public static final String LOGIN = "https://kyfw.12306.cn/passport/web/login";

    public static final String PATH = "/Users/Mac/local/apache2.4/12306/";

    public static final String IMG_IP = "http://localhost:80/12306/";

    public static final String QUERY = "https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=DATE_TIME&leftTicketDTO.from_station=START&leftTicketDTO.to_station=END&purpose_codes=ADULT";

    public static final String QUERY_CITY = "https://www.12306.cn/index/script/core/common/station_name_v10003.js";
}
