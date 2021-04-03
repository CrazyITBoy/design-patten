package com.h_h.study.designpatten.singleton;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 元胡
 * @date 2021/04/03 5:39 下午
 */
public class LazySingletonInstance {
    public static void main(String[] args) throws InterruptedException {
        //单线程情况
        LazyInstance instance = LazyInstance.getInstance();
        LazyInstance instance2 = LazyInstance.getInstance();
        System.out.println(instance == instance2);

        CountDownLatch latch = new CountDownLatch(2);
        //多线程情况
        AtomicReference<LazyInstance> threadInstance = new AtomicReference<>();
        new Thread(() -> {
            try {
                threadInstance.set(LazyInstance.getInstance());
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        AtomicReference<LazyInstance> threadInstance2 = new AtomicReference<>();
        new Thread(() -> {
            try {
                threadInstance2.set(LazyInstance.getInstance());
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //使用countDownLatch 保证所有的线程执行完成
        latch.await();
        System.out.println((threadInstance.get()) == (threadInstance2.get()));
    }
}

class LazyInstance {

    private static volatile LazyInstance lazyInstance;

    private LazyInstance() {
        System.out.println("test");
    }

    public static LazyInstance getInstance() throws InterruptedException {
        if (lazyInstance == null) {
            synchronized (LazyInstance.class){
                if(lazyInstance == null){
                    lazyInstance = new LazyInstance();
                }
            }
        }
        return lazyInstance;
    }

}
