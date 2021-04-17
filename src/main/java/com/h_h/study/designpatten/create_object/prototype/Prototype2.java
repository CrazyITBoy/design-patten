package com.h_h.study.designpatten.create_object.prototype;

import java.io.*;

/**
 * 序列化实现
 * 
 * @author 元胡
 * @date 2021/04/17 3:26 下午
 */
public class Prototype2 {

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Product2 product = new Product2();
        product.setAge(1);
        product.setName("测试一下");
        product.setBaseInfo2(new BaseInfo2("test",11));
        Object clone = product.copy();

        System.out.println("original:" + product);
        System.out.println("clone:" + clone);
    }
}

class Product2 implements Serializable {
    private static final long serialVersionUID = -5689085309484175535L;
    private String name;

    private Integer age;

    private BaseInfo2 baseInfo2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BaseInfo2 getBaseInfo2() {
        return baseInfo2;
    }

    public void setBaseInfo2(BaseInfo2 baseInfo2) {
        this.baseInfo2 = baseInfo2;
    }

    public Product2 copy() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        oos.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (Product2) ois.readObject();
    }

    @Override
    public String toString() {
        return "Product{" + "name='" + name + '\'' + ", age=" + age + ", baseInfo=" + baseInfo2 + '}';
    }
}

class BaseInfo2 implements Serializable {

    public BaseInfo2(String address, Integer count) {
        this.address = address;
        this.count = count;
    }

    private String address;
    private Integer count;

    public BaseInfo2(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}