package com.h_h.study.designpatten.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 元胡
 * @date 2021/04/03 9:20 下午
 */
public class StaticInnerClassSingletonInstanceApp {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println(StaticInnerClassSingletonInstance.getINSTANCE());
        System.out.println(StaticInnerClassSingletonInstance.getINSTANCE());
        System.out.println(StaticInnerClassSingletonInstance.getINSTANCE());
        Class<StaticInnerClassSingletonInstance> staticInnerClassInstanceClass = StaticInnerClassSingletonInstance.class;

        Constructor<StaticInnerClassSingletonInstance> declaredConstructor = staticInnerClassInstanceClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);

        StaticInnerClassSingletonInstance staticInnerClassSingletonInstance = declaredConstructor.newInstance();
        System.out.println(staticInnerClassSingletonInstance == StaticInnerClassSingletonInstance.getINSTANCE());
    }
}

class StaticInnerClassSingletonInstance {

    private static StaticInnerClassSingletonInstance INSTANCE ;

    static {
        INSTANCE = new StaticInnerClassSingletonInstance();
    }

    private StaticInnerClassSingletonInstance(){
        if(HungrySingletonInstance.getINSTANCE() != null){
            throw new RuntimeException("单列bean 不允许创建多例！");
        }
    }

    public static StaticInnerClassSingletonInstance getINSTANCE() {
        return INSTANCE;
    }
}
