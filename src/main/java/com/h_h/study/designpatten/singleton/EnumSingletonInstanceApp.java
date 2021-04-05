package com.h_h.study.designpatten.singleton;

import java.io.*;

/**
 * 枚举模式单例bean
 * @author 元胡
 * @date 2021/04/03 9:29 下午
 */
public class EnumSingletonInstanceApp {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(EnumSingletonInstance.INSTANCE.hashCode());
        System.out.println(EnumSingletonInstance.INSTANCE.hashCode());

        //写入
        ObjectOutputStream obs = new ObjectOutputStream(new FileOutputStream("EnumSingletonInstanceApp"));
        obs.writeObject(EnumSingletonInstance.INSTANCE);
        //读取
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("EnumSingletonInstanceApp"));
        Object o = ois.readObject();

        System.out.println(o == EnumSingletonInstance.INSTANCE);
    }
}

enum EnumSingletonInstance {
    INSTANCE;
}
