package com.taobao.user.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task implements Runnable {
    private static int index = 1;
    private final Lock lock = new ReentrantLock();


    @Override
    public  void run() {



            while (index<=100) {
                synchronized (lock){

                    System.out.println(Thread.currentThread().getName()+":"+index);
                    index++;
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }






        }





    }

