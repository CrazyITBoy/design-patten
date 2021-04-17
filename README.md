# 设计模式
## 创建型模式
### 单例模式
<ol>
<li>懒汉模式</li>

```java
class LazySingletonInstance {

    private static volatile LazySingletonInstance lazyInstance;

    private LazySingletonInstance() {
        System.out.println("test");
    }

    public static LazySingletonInstance getInstance() throws InterruptedException {
        if (lazyInstance == null) {
            synchronized (LazySingletonInstance.class){
                if(lazyInstance == null){
                    lazyInstance = new LazySingletonInstance();
                }
            }
        }
        return lazyInstance;
    }

}
```
使用DCL（Double Check Lock）+volatile解决并发问题与指令重排问题<br/>
DCL:两次检查是否是已经被实例化了<br/>
在进行加锁的时候，可能有多个线程阻塞在该位置，为防止第一个加锁的释放锁后，后面的线程重新获取锁，又执行new操作，从而无法保证单例。<br/>
故在锁方法体里面再次判断是否已经实例化了<br/>
volatile:禁止指令重排<br/>
在JIT编译器中或者CPU中都可能会指令重排<br/>
比如创建一个对象<br/>
1、首先是分配一个内存空间<br/>
2、然后执行初始化<br/>
3、然后把引用赋值给变量<br/>

第一步是必须的，对于指令重排来说，第二步和第三步是可以指令重排的。<br/>
如果直接赋值变量后，但是并没有初始化就去使用就会出现意向不到的问题。<br/>

优点<br/>
在使用时候，才需要创建实例，适合于实例资源较大的<br/>

缺点<br/>
<br/>
```java
public class LazySingletonInstanceApp {
    public static void main(String[] args) throws InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<LazySingletonInstance> lazyInstanceClass = LazySingletonInstance.class;
        Constructor<LazySingletonInstance> declaredConstructor = lazyInstanceClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        LazySingletonInstance lazyInstance = declaredConstructor.newInstance();


        System.out.println(LazySingletonInstance.getInstance());
        System.out.println(lazyInstance);

    }

}
```
输出：<br/>
com.h_h.study.designpatten.create_object.singleton.LazySingletonInstance@24d46ca6<br/>
com.h_h.study.designpatten.create_object.singleton.LazySingletonInstance@4517d9a3
<li>饿汉模式</li>

```java
class HungrySingletonInstance {

    private static HungrySingletonInstance INSTANCE = new HungrySingletonInstance();

    private HungrySingletonInstance(){
        if(HungrySingletonInstance.getINSTANCE() != null){
            throw new RuntimeException("单例bean 不允许创建多例！");
        }
    }

    public static HungrySingletonInstance getINSTANCE() {
        return INSTANCE;
    }
}
```

使用类的加载机制保证只会加载一次，在执行类的加载过程，加载、连接（验证、准备、解析）、初始化<br/>
其中静态字段或者静态代码块的初始化就是在初始化的时候执行的<br/>
JVM的类的加载机制是采用同步的方式进行类的加载的，所以加载类是线程安全的，不会有安全性问题。<br/>

什么时候才会执行初始化呢？<br/>
Main函数所在类<br/>
执行new <br/>
访问一个类静态属性<br/>
访问静态方法<br/>
反射访问一个类<br/>
初始化一个类的子类<br/>

通过在构造方法中判断实例是否已经创建好了,从而避免反射来创建实例<br/>
<br/>
<li>静态内部类模式</li>

```java
public class StaticInnerSingletonInstance {
    private StaticInnerSingletonInstance(){

    }

    public static class InnerHolder{
        private static StaticInnerSingletonInstance INSTANCE = new StaticInnerSingletonInstance();
    }

    public static StaticInnerSingletonInstance getINSTANCE(){
        return InnerHolder.INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println(StaticInnerSingletonInstance.getINSTANCE());
        System.out.println(StaticInnerSingletonInstance.getINSTANCE());
    }
}
```
静态内部类创建创建单例bean的原理和饿汉模式创建的单例bean的原理是一样的

<li>枚举模式</li>

```java
enum EnumInstance{
    INSTANCE;
}

```
进入编译的枚举class所在文件夹，target目录下<br/>
进入目录，敲上javap 可以知道后面跟什么参数代表什么意义<br/>
其中javap -v class名称 代表的是查看该类的编译后的字节码指令<br/>
javap -v EnumInstance.class

得到很多字节码描述，其中下面的是关键点<br/>
public static final com.h_h.study.designpatten.create_object.singleton.EnumInstance INSTANCE;<br/>
我们可以看到编译后的INSTANCE 被static修饰了，说明是通过执行类的加载，在初始化的时候保证单例的<br/>


枚举天然不支持反射创建单例，所以不存在反射攻击，且有自己的反序列机制<br/>
利用类的加载机制保证线程安全<br/>

```java
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
```
在反序列化读取枚举的时候，最终会调用<br/>                
Enum<?> en = Enum.valueOf((Class)cl, name);<br/>
保证反序列化的是同一个<br/>

<li>序列化与反序列化</li>
对于<br/>
HungrySingletonInstance.class<br/>
通过给类加上序列化标识，并且重新生成序列化id<br/>
然后加上readResolve方法就可以保证，序列化和反序列化的是同一个对象<br/>

```java
class HungrySingletonInstance implements Serializable {

    //序列化id 会导致序列化前后的对象，jvm认为不是同一个
    private static final long serialVersionUID = -1302892535948571348L;

    private static HungrySingletonInstance INSTANCE = new HungrySingletonInstance();

    private HungrySingletonInstance(){
        if(HungrySingletonInstance.getINSTANCE() != null){
            throw new RuntimeException("单例bean 不允许创建多例！");
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
```
</ol>

### 简单工厂模式

简单工厂模式，代码简单，只要一个工厂，根据一定的条件生产不同的产品

```java
public class SimpleFactory {

    //核心代码，根据不同的条件生产不同的对象（产品）
    public Stock getStock(int type){
        if(type == 1){
            return new RetailPOSStock();
        }else if(type == 2){
            return new MicroStoreStock();
        }
        return null;
    }
}


interface Stock{
    boolean subStock();
}


class RetailPOSStock implements Stock{

    @Override
    public boolean subStock() {
        return false;
    }
}

class MicroStoreStock implements Stock{

    @Override
    public boolean subStock() {
        return false;
    }
}
```

### 工厂方法模式

工厂方法模式，将对象的生产延迟到工厂的子类，由不同的子类工厂负责生产不同的对象

```java
public  class FactoryMethod {

    public static void main(String[] args) {
        OauthFactory oauthFactory = new AlipayOauthFactory();
        Oauth oauth = oauthFactory.getOauth();
        System.out.println(oauth.oauth());

        OauthFactory wxOauthFactory = new WxOauthFactory();
        Oauth wxOauth = wxOauthFactory.getOauth();
        System.out.println(wxOauth.oauth());
    }
}

//---------------以下便是关于工厂方法的代码---------------
//抽象工厂
interface OauthFactory{
    //获得授权对象
    Oauth getOauth();
}

//具体工厂 生成具体的授权对象
class WxOauthFactory implements OauthFactory{

    @Override
    public Oauth getOauth() {
        return new WxOauth();
    }
}

//具体工厂 生成具体的授权对象
class AlipayOauthFactory implements OauthFactory{

    @Override
    public Oauth getOauth() {
        return new AlipayOauth();
    }
}

//----------生成的产品，授权对象--------
interface Oauth{
    int oauth();
}

class WxOauth implements Oauth{
    @Override
    public int oauth() {
        System.out.println("微信授权！");
        return 0;
    }
}

class AlipayOauth implements Oauth{
    @Override
    public int oauth() {
        System.out.println("支付宝授权！");
        return 1;
    }
}
```
### 抽象工厂模式

核心就是提供一个能够创建一系列产品的抽象工厂，然后再由不同的具体工厂进行生产这一系列产品。<br/>
也就是说一个工厂会生产多个不同产品

```java
public class AbstractFactory {

    public static void main(String[] args) {
        //用户网页授权登录微信
        UserLoginFactory userLoginFactory  = new WxUserLoginFactory();
        Oauth oauth = userLoginFactory.getOauth();
        oauth.oauth();

        UserCache userCache = userLoginFactory.getUserCache();
        userCache.cacheUser();

    }

}

//提供一个创建一系列产品的抽象工厂
interface UserLoginFactory {
    Oauth getOauth();

    UserCache getUserCache();
}

//由具体的工厂进行不同的实现
class AlipayUserLoginFactory implements UserLoginFactory {

    @Override
    public Oauth getOauth() {
        return new AlipayOauth();
    }

    @Override
    public UserCache getUserCache() {
        return new AlipayUseCache();
    }
}

class WxUserLoginFactory implements UserLoginFactory {

    @Override
    public Oauth getOauth() {
        return new WxOauth();
    }

    @Override
    public UserCache getUserCache() {
        return new WxUseCache();
    }
}




//---------------以下便是关于工厂方法的代码---------------

//----------生成的产品，授权对象--------
interface Oauth{
    int oauth();
}

class WxOauth implements Oauth {
    @Override
    public int oauth() {
        System.out.println("微信授权！");
        return 0;
    }
}

class AlipayOauth implements Oauth {
    @Override
    public int oauth() {
        System.out.println("支付宝授权！");
        return 1;
    }
}

interface UserCache{
    int cacheUser();
}

class AlipayUseCache implements UserCache{

    @Override
    public int cacheUser() {
        System.out.println("缓存支付宝用户OK!");
        return 0;
    }
}

class WxUseCache implements UserCache{

    @Override
    public int cacheUser() {
        System.out.println("缓存微信用户OK!");
        return 0;
    }
}
```

### 建造者模式

指定一个指挥值（Direct），执行创建的步骤。<br/>
在由一个抽象的构造者，决定对象的构造过程。<br/>
最后由具体的构造，执行对象的构造。<br/>

```java
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
```

### 原型模式

原型模式，是一种对象复制的设计模式。<br/>

原型模式可以通过两种方式来实现：<br/>
（1）实现拷贝接口<br/>
（2）通过序列化与反序列化<br/>
（3）通过new对象，然后字段set值<br/>
#### （1）拷贝
所有是应用类型的变量都需要实现cloneable接口，并且重新clone方法，如果变量的变量还是引用类型需要继续修改<br>
深拷贝：只拷贝对象，对象中引用类型的变量只拷贝了引用<br>
浅拷贝：拷贝对象，并且对引用类型变量，进行了整个对象的拷贝<br>
```java
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
```
#### （2）序列化
序列化实现拷贝的好处是，引用类型变量只要实现序列化接口，重新序列化id即可，只需要写一个序列化实现接口即可
````java
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
````
应用：
```java
org.springframework.beans.factory.support.AbstractBeanDefinition
java.util.Arrays
```
### 享元模式
将一些无差异对象，只存储一份数据，节省空间。
关键点，缓存相似对象使用Map等等工具
```java
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
```

jdk源码中的应用
```java
String,Integer,Long...
com.sun.org.apache.bcel.internal.generic.InstructionConstants 
```

### 门面模式（外观模式）
