package com.h_h.study.designpatten.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 元胡
 * @date 2021/04/03 6:13 下午
 */
public class HungrySingletonInstanceApp {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println(HungrySingletonInstance.getINSTANCE());
        System.out.println(HungrySingletonInstance.getINSTANCE());

        Class<?> hungryInstance = Class.forName("com.h_h.study.designpatten.singleton.HungrySingletonInstance");
        Constructor<?> declaredConstructor = hungryInstance.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Object o = declaredConstructor.newInstance();
        System.out.println(o);
    }

}

class HungrySingletonInstance {

    private static HungrySingletonInstance INSTANCE = new HungrySingletonInstance();

    private HungrySingletonInstance(){
        if(HungrySingletonInstance.getINSTANCE() != null){
            throw new RuntimeException("单列bean 不允许创建多例！");
        }
    }

    public static HungrySingletonInstance getINSTANCE() {
        return INSTANCE;
    }
}
