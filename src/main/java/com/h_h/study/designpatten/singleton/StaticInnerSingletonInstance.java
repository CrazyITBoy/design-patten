package com.h_h.study.designpatten.singleton;

/**
 * 静态内部类实现单例bean
 * @author 元胡
 * @date 2021/04/03 10:46 下午
 */
public class StaticInnerSingletonInstance {
    private StaticInnerSingletonInstance(){
        System.out.println("StaticInnerSingletonInstance");
    }

    public static class InnerHolder{
        public InnerHolder(){
            System.out.println("InnerHolder");
        }
        private static StaticInnerSingletonInstance INSTANCE = new StaticInnerSingletonInstance();
    }

    public static StaticInnerSingletonInstance getINSTANCE(){
        return InnerHolder.INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println(StaticInnerSingletonInstance.getINSTANCE());
        System.out.println(StaticInnerSingletonInstance.getINSTANCE());
    }
}
