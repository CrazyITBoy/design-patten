package com.h_h.study.designpatten.create_object.prototype;

/**
 * cloneable接口与clone()方法实现拷贝
 * @author 元胡
 * @date 2021/04/17 3:26 下午
 */
public class Prototype {

    public static void main(String[] args) throws CloneNotSupportedException {
        Product product = new Product();
        product.setAge(1);
        product.setName("测试一下");
        product.setBaseInfo(new BaseInfo("test",11));
        Object clone = product.clone();

        System.out.println("original:" + product);
        System.out.println("clone:" + clone);
    }
}

class Product implements Cloneable {
    private String name;

    private Integer age;

    private BaseInfo baseInfo;

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

    public BaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    @Override
    protected Product clone() throws CloneNotSupportedException {
        Product product = (com.h_h.study.designpatten.create_object.prototype.Product) super.clone();
        product.setBaseInfo(this.getBaseInfo().clone());
        return product;
    }

    @Override
    public String toString() {
        return "Product{" + "name='" + name + '\'' + ", age=" + age + ", baseInfo=" + baseInfo + '}';
    }
}

class BaseInfo implements Cloneable {

    public BaseInfo(String address, Integer count) {
        this.address = address;
        this.count = count;
    }

    private String address;
    private Integer count;

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

    @Override
    protected BaseInfo clone() throws CloneNotSupportedException {
        return (BaseInfo) super.clone();
    }
}