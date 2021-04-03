# 设计模式

## 单例模式
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

对于反射攻击来说，懒汉模式无法解决<br/>

优点<br/>
在使用时候，才需要创建实例，适合于实例资源较大的<br/>

缺点<br/>
无法解决反射攻击<br/>
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
com.h_h.study.designpatten.singleton.LazySingletonInstance@24d46ca6<br/>
com.h_h.study.designpatten.singleton.LazySingletonInstance@4517d9a3
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

得到很多字节码描述，其中下面的是关键点
public static final com.h_h.study.designpatten.singleton.EnumInstance INSTANCE;
我们可以看到编译后的INSTANCE 被static修饰了，说明是通过执行类的加载，在初始化的时候保证单例的


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
保证反序列化的是同一个

<li>序列化与反序列化</li>
对于
HungrySingletonInstance.class
通过给类加上序列化标识，并且重新生成序列化id
然后加上readResolve方法就可以保证，序列化和反序列化的是同一个对象

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

