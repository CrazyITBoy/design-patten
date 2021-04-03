#设计模式

##单列模式
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

<li>饿汉模式</li>
<li>静态内部类模式</li>
<li>枚举模式</li>

</ol>

