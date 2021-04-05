package com.h_h.study.designpatten.create_object.builder;

/**
 * @author 元胡
 * @date 2021/04/05 6:40 下午
 */
public class BuilderPatten {

    public static void main(String[] args) {
        Builder builder = new DefaultProduceBuilder();
        Direct direct = new Direct(builder);

        Product product = direct.makeProduct(300L, "测试", "红色");

        System.out.println(product.toString());
    }

}

class Direct {

    private Builder builder;

    Direct(Builder builder) {
        this.builder = builder;
    }

    Product makeProduct(Long invalidDate, String name, String color) {
        builder.buildColor(color);
        builder.buildName(name);
        builder.buildInvalidDate(invalidDate);
        return builder.build();
    }
}

interface Builder {

    void buildInvalidDate(Long invalidDate);

    void buildName(String name);

    void buildColor(String color);

    Product build();

}

class DefaultProduceBuilder implements Builder {

    private Long invalidDate;

    private String name;

    private String color;

    @Override
    public void buildInvalidDate(Long invalidDate) {
        this.invalidDate = invalidDate;
    }

    @Override
    public void buildName(String name) {
        this.name = name;
    }

    @Override
    public void buildColor(String color) {
        this.color = color;
    }

    @Override
    public Product build() {
        return new Product(this.invalidDate, this.name, this.color);
    }
}

class Product {
    private Long invalidDate;

    private String name;

    private String color;

    public Product(Long invalidDate, String name, String color) {
        this.invalidDate = invalidDate;
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Product{" +
                "invalidDate=" + invalidDate +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
