package com.taobao.user.controller;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {


    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id ="2016093000634489";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQC1EONiLhWw8CtPnIUzJfSb/XvTshkNzP2M5wJCdsVgzdx0omDfJ5yEN2AlkSJLuFmbd3yNaKt/prtgnbHzLlkWvU7wsWQanUh/KhvS+6BxzCrr1c/yvLM2OzkVWRbkDUV8zCCytERggg7V31lC/vOWHkKBTQohRXzCfrhWkldBk/Vr39MLEVjcFq5NcIDSfwv5PXYjBSzvdm6KX4Wbu6L2G0R64WzT25Cg2d563ZacTyKUanQWIz5Bi2Xy7MHAynlXJJwu3WKIguWAs4RlLz3Mn7L+N4EAVsc0fGvaJVAjIZatCfWpMYOYsdobSOb7ETCyLjgzGCdQvaD5ounb/m4RAgMBAAECggEBAK4QpTeubAXkbQ6kvtx0AZqwbgkObs14osuBDQgOHCO649NwgsYk02+7uH+E7b5zZBl0HdXnqO8Xv4zbLMZGaTjWFvyG3GBFkftF5OfIJO8hQqXjr3yAcZySBIeEVOA57fJSXqDNceqBPXfXtn0UkeFgB7k78aAYqB5mKhp3ptCPppEXG5vcL5VjMPWEVyqzwV3W4eqekfkn5oWdZqhBVIXg+u2kan5/hMkWFsk8vbEUBzXFTQH5wyL8e25zjzUgFornO7odzpssIJqQ02dICrIwO6FBeOciqJBxidzfkS97A/dwkqgPhJuTyltGY/XBe990MEuU6oUiOtSLb39Pcv0CgYEA9IglmxmFwTCVF96OWZo5+f9jyzf6w2DCeAapR+jnx9CpkyKa345GJwJCpgv/QAXQUCNeyKrs9iF6jfjCjgfq0ZawhLcSBxryybfjnOyAUs4Gofyo+6r8rQErPm0IrlZ3aRHvjWPzG59OTMZSF+7i4gT+STWwhzrQ1PUhjHVUc0MCgYEAvY7E7CevfWomIjEEuqX2gpeJqYv4Pi/YhZGnQzjSsNaePljNa5h1aKyiTCnDCHzp/H5Um1GibyE/8I1j8Fd9zu8nZPQhDqsVFLUYYquR+aRLow8yYX4d47kwr6II2PuhsTGmde/VIJ9Mw+8pyj+MHC6hqYhjmSMfpCIX22i1QhsCgYEAxLdqGVX05i+hIWym86pAjb9IlslX8Ck9CLXDJNhW7cYNRRKHqOSZxvPojhAErYp/xJhPvfrTWkYgg2nlicEQxL8HDh0AYbrqKiDG+QzBRtv7NgE+KAer8bmJAlVQsH9a3KnTHtmTT1te8CBzFlHVL6J4toh8lK1aJpd0/7u7zmUCgYEAgKTs1gxU1/t7J8c2m2lYs+YeAhfBAIs+Gd/wsPlWW7c7q7pepv/P5psKhj/tYcxMjBQ8XTB42YyqNRnv0kO+WPsLNENOUAMhim0gwI4Jwm+S+XWtzmZl6I9dEEUaVg/n+zZj7zQ14y7ZTTv5k42vC81F36V1Wo9wwNZ2dGxKXzMCgYEA2jnfCxptPjHSj4uG5Q3Ocg72d/RAREQcV69kW4zS8jcs7tDBbqHQBh7FSHoVNejzZjE/gp6yFxPxpAan6RBG4U2XpXm3Uxr7m5RNpeLmfpj52buD9abAQBybjMX8UdHCEBZ8lqUsI+qNocKCANFTMzLSh7otymU3glSSj67x2wQ=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh4s7K0OK3KHahBE9SBksoYg/AaKyusatkLJNTNyyx98G1U+6T/y8l7kS8QoCsOM7Q5NbKXi1I5X7O13VN7NfZ0Fq6rhcxm+yQMt4dZycXwYkTQpYbhtHBrbHvZaB0uf1qd3+jH9nn5NmGCBbGo+tRtjk9kxs8rPGA86nDzbyyrMxz6tmFmlZ1JHQc/RyTtJmPq5rwn9reRwJ9+idbD+O5JXr5fcdSjyNUcJ0KaYJu00+WUl4Iu64Vg2/FE3nC2IhGW091Xwxxjx1USQsVs7e4/z6WiqxSP1VLH6ATF63mKl6goyf7GSe/+qnRsJZfvaduR+e69gnTtgcueRVKHtg4QIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8081/paySuccess.do";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url =  "http://localhost:8081/paySuccess.do";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
