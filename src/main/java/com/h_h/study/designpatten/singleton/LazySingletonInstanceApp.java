package com.h_h.study.designpatten.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 懒汉模式单例bean
 * @author 元胡
 * @date 2021/04/03 5:39 下午
 */
public class LazySingletonInstanceApp {
    public static void main(String[] args) throws InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //单线程情况
//        LazyInstance instance = LazyInstance.getInstance();
//        LazyInstance instance2 = LazyInstance.getInstance();
//        System.out.println(instance == instance2);
//
//        CountDownLatch latch = new CountDownLatch(2);
//        //多线程情况
//        AtomicReference<LazyInstance> threadInstance = new AtomicReference<>();
//        new Thread(() -> {
//            try {
//                threadInstance.set(LazyInstance.getInstance());
//                latch.countDown();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        AtomicReference<LazyInstance> threadInstance2 = new AtomicReference<>();
//        new Thread(() -> {
//            try {
//                threadInstance2.set(LazyInstance.getInstance());
//                latch.countDown();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        //使用countDownLatch 保证所有的线程执行完成
//        latch.await();
//        System.out.println((threadInstance.get()) == (threadInstance2.get()));
        Class<LazySingletonInstance> lazyInstanceClass = LazySingletonInstance.class;
        Constructor<LazySingletonInstance> declaredConstructor = lazyInstanceClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        LazySingletonInstance lazyInstance = declaredConstructor.newInstance();


        System.out.println(LazySingletonInstance.getInstance());
        System.out.println(lazyInstance);

    }

}

class LazySingletonInstance {

    private static volatile LazySingletonInstance lazyInstance;

    private LazySingletonInstance() {
        System.out.println("test");
    }

    public static LazySingletonInstance getInstance() throws InterruptedException {
        if (lazyInstance == null) {
            synchronized (LazySingletonInstance.class){
                if(lazyInstance == null){
                    lazyInstance = new LazySingletonInstance();
                }
            }
        }
        return lazyInstance;
    }

}
