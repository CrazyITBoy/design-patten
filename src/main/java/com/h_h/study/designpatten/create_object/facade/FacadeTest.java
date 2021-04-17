package com.h_h.study.designpatten.create_object.facade;

/**
 * @author 元胡
 * @date 2021/04/17 6:50 下午
 */
public class FacadeTest {

    public static void main(String[] args) {
        Client client = new Client();
        client.doSomeThings();
    }
}

//客户端
class Client{
    public void doSomeThings(){
        //客户端无需具体指导需要调用那些类完成一些事情，只需要调用facade的简单接口，即可完成要做的事情
        Facade facade = new FacadeImpl();
        facade.doSomeThings();
    }
}

//facade 层隐藏接口的实现 对吗只需调用接口即可
interface Facade {
     void doSomeThings();
}

class FacadeImpl implements Facade{

    A a = new A();
    B b = new B();
    C c = new C();

    public void doSomeThings() {
        a.doSomething();
        b.doSomething();
        c.doSomething();
    }
}

//----- 三个具体的类要做的事情

class A {

    public void doSomething() {
        System.out.println("A do things");
    }
}

class B {

    public void doSomething() {
        System.out.println("B do things");
    }
}

class C {

    public void doSomething() {
        System.out.println("C do things");
    }
}