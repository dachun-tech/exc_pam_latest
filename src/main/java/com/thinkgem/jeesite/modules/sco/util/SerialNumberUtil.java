package com.thinkgem.jeesite.modules.sco.util;

import java.util.Date;

/**
 * Created by jeesite on 2015/11/12.
 */
public class SerialNumberUtil {
    /**
     * 创建一个单号，<br />
     * 生成原则为：随机(1位)+系统时间（精确到毫秒,17位）+ 两位随机数(2位)
     * @return mscoprseq 单号
     */
    public static String createSerialNumber() {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyMMddHHmmss");
        java.util.Date currentTime = new java.util.Date();// 得到当前系统时间
        String mscoprseq = formatter.format(currentTime); // 将日期时间格式化
//        String type = String.valueOf((int) (Math.random() * 9 + 1)); //正前位
        String type = "02"; //甲方要求
        String random = "";
        // 获取100-999之间的随机数，为了保证订单号长度一致
        for (int i = 0; i < 2; i++) {
            random += String.valueOf((int) (Math.random() * 89 + 10));
        }
        // 补位
        random += String.valueOf((int) (Math.random() * 89 + 10));
        return type + mscoprseq + random;
    }
    
    /**
     * 生成单号
     * 验收单号按照18位设计：ZK001（采购机构代号）+20160101（验收单生成日期）+1（种类代号：1物资2车辆3印刷4图文5软件）+****（4位随机数字）
     * @param type
     * @return
     */
    public static String createSerialNumber(String purchaseCode,String type) {
//    	TODO:暂时写死为ZK001
//    	purchaseCode = "ZK001";
    	int random = (int)(Math.random()*9000+1000);
    	return purchaseCode + DateUtil.dateToStr(new Date(), "yyyyMMdd")  +type + random;
    }

    public static void main(String[] args) {
        System.out.println(createSerialNumber("F1212",2+""));
        System.out.println((int)(Math.random()*9000+1000));
    }
}
