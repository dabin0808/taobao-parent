package com.taobao.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间相关工具类 全部写出静态方法 类名直接调用
 */
public class DateUtil {

   public static String getNowTime(){
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
       return format.format(new Date());
   }

}
