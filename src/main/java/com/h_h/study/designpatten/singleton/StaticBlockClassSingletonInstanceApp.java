package com.h_h.study.designpatten.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 静态代码块 单例bean
 * @author 元胡
 * @date 2021/04/03 9:20 下午
 */
public class StaticBlockClassSingletonInstanceApp {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println(StaticBlockClassSingletonInstance.getINSTANCE());
        System.out.println(StaticBlockClassSingletonInstance.getINSTANCE());
        System.out.println(StaticBlockClassSingletonInstance.getINSTANCE());
        Class<StaticBlockClassSingletonInstance> staticInnerClassInstanceClass = StaticBlockClassSingletonInstance.class;

        Constructor<StaticBlockClassSingletonInstance> declaredConstructor = staticInnerClassInstanceClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);

        StaticBlockClassSingletonInstance staticInnerClassSingletonInstance = declaredConstructor.newInstance();
        System.out.println(staticInnerClassSingletonInstance == StaticBlockClassSingletonInstance.getINSTANCE());
    }
}

class StaticBlockClassSingletonInstance {

    private static StaticBlockClassSingletonInstance INSTANCE ;

    static {
        INSTANCE = new StaticBlockClassSingletonInstance();
    }

    private StaticBlockClassSingletonInstance(){
        if(HungrySingletonInstance.getINSTANCE() != null){
            throw new RuntimeException("单例bean 不允许创建多例！");
        }
    }

    public static StaticBlockClassSingletonInstance getINSTANCE() {
        return INSTANCE;
    }
}
