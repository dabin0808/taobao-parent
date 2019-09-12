package com.taobao.user.service;

import java.util.concurrent.*;

public class TaskTest {

    public static void main(String[] args) {

       Task task = new Task();

        Thread t1 = new Thread(task, "T1");
        Thread t2 = new Thread(task, "T2");
        Thread t3 = new Thread(task, "T3");
        t1.start();
        t2.start();
        t3.start();


    }
}
