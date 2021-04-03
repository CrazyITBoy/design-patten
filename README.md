# 设计模式

## 单列模式
<ol>
<li>懒汉模式</li>

```java
class LazyInstance {

    private static volatile LazyInstance lazyInstance;

    private LazyInstance() {
        System.out.println("test");
    }

    public static LazyInstance getInstance() throws InterruptedException {
        if (lazyInstance == null) {
            synchronized (LazyInstance.class){
                if(lazyInstance == null){
                    lazyInstance = new LazyInstance();
                }
            }
        }
        return lazyInstance;
    }
}
```
使用DCL（Double Check Lock）+volatile解决并发问题与指令重排问题
DCL:两次检查是否是已经被实例化了
在进行加锁的时候，可能有多个线程阻塞在该位置，为防止第一个加锁的释放锁后，后面的线程重新获取锁，又执行new操作，从而无法保证单列。
故在锁方法体里面再次判断是否已经实例化了

对于反射攻击来说，懒汉模式无法解决

优点
在使用时候，才需要创建实例，适合于实例资源较大的

缺点
无法解决反射攻击
```java
public class LazySingletonInstance {
    public static void main(String[] args) throws InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<LazyInstance> lazyInstanceClass = LazyInstance.class;
        Constructor<LazyInstance> declaredConstructor = lazyInstanceClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        LazyInstance lazyInstance = declaredConstructor.newInstance();


        System.out.println(LazyInstance.getInstance());
        System.out.println(lazyInstance);
    }
}
```
输出：<br/>
com.h_h.study.designpatten.singleton.LazyInstance@24d46ca6
com.h_h.study.designpatten.singleton.LazyInstance@4517d9a3


<li>饿汉模式</li>
<li>静态内部类模式</li>
<li>枚举模式</li>

</ol>

