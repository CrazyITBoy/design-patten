package com.h_h.study.designpatten.singleton;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 元胡
 * @date 2021/04/03 6:13 下午
 */
public class HungrySingletonInstanceApp {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        System.out.println(HungrySingletonInstance.getINSTANCE());
        System.out.println(HungrySingletonInstance.getINSTANCE());

        //反射攻击
//        Class<?> hungryInstance = Class.forName("com.h_h.study.designpatten.singleton.HungrySingletonInstance");
//        Constructor<?> declaredConstructor = hungryInstance.getDeclaredConstructor();
//        declaredConstructor.setAccessible(true);
//        Object o = declaredConstructor.newInstance();
//        System.out.println(o);


        //序列化与反序列化

        //写入
        ObjectOutputStream obs = new ObjectOutputStream(new FileOutputStream("HungrySingletonInstance"));
        obs.writeObject(HungrySingletonInstance.getINSTANCE());
        //读取
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("HungrySingletonInstance"));
        Object object = ois.readObject();


        System.out.println(object == HungrySingletonInstance.getINSTANCE());
    }

}

class HungrySingletonInstance implements Serializable {

    //序列化id 会导致序列化前后的对象，jvm认为不是同一个
    private static final long serialVersionUID = -1302892535948571348L;

    private static HungrySingletonInstance INSTANCE = new HungrySingletonInstance();

    private HungrySingletonInstance(){
        if(HungrySingletonInstance.getINSTANCE() != null){
            throw new RuntimeException("单列bean 不允许创建多例！");
        }
    }

    public static HungrySingletonInstance getINSTANCE() {
        return INSTANCE;
    }

    //该方法返回值必须为Object类型，同时方法名是readResolve，入参必须为空才可以保证
    //反序列化的时候，才可以从该方法读取实例
    private Object readResolve(){
        return getINSTANCE();
    }
}
