package com.h_h.study.designpatten.create_object.fly.weight;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 享元模式：关键点：缓存相似对象，节省空间资源
 * 
 * @author 元胡
 * @date 2021/04/17 4:33 下午
 */
public class FlyWeightTest {
    public static void main(String[] args) {
        System.out.println(Book.getBook(1));
        System.out.println(Book.getBook(1));
        System.out.println(Book.getBook(2));
        System.out.println(Book.getBook(2));

    }
}

class Book {

    private static Map<Integer, Book> typesBookMap = new ConcurrentHashMap<>();

    public Book(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Book getBook(Integer type) {
        Book book;
        if ((book = typesBookMap.get(type)) != null) {
            return book;
        }
        if (type == 1) {
            System.out.println("new 语文book");
            typesBookMap.put(type, new Book(type, "语文"));
        } else if (type == 2) {
            System.out.println("new 数学book");
            typesBookMap.put(type, new Book(type, "数学"));
        }
        return getBook(type);
    }

    private int type;

    private String name;
}