package com.taobao.seller.aopLog;

import com.taobao.common.DateUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@Aspect
public class SellerLog {
    @Pointcut("execution(*  *..SellerMannager.*(..))")
    public void pot() {

    }

    @Around("pot()")
    public Object aroundMethod(ProceedingJoinPoint pjd) {

        Object res = null;
        String method = pjd.getSignature().getName();
        try {
            long s = System.currentTimeMillis();
            res = pjd.proceed();
            long e = System.currentTimeMillis();
            printLog(method, e - s);
        } catch (Throwable throwable) {
            printError(method, throwable);
            throwable.printStackTrace();
        }

        return res;
    }

    private void printError(String method, Throwable throwable) {
        String str = DateUtil.getNowTime() + "发生了" + throwable.getMessage() + "错误.";
        logPrintFile(str);
    }

    private void printLog(String method, long l) {
        String str = DateUtil.getNowTime() + "执行了" + method + "()方法,消耗了" + l + "ms";
        logPrintFile(str);
    }

    File file = new File("e://log.txt");
    BufferedWriter bw;

    public void logPrintFile(String log) {
        //创建文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bw = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.append(log);
            bw.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
