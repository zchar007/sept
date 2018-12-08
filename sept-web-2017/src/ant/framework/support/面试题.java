package ant.framework.support;

public class 面试题 {
	// 对象
	// 问：对象的比较，怎么判断两个对象相等？
	// 答：equals方法
	// 问：Integer里Integer a = new Integer(123), Integer b = 123, a==b?
	// 答：不相等
	// 问：Integer里Integer a = new Integer(123), Integer b = new Integer(123),
	// a==b?
	// 答：不相等
	// 问：Integer里Integer a = new Integer(123), int b = 123, a==b?
	// 答：相等
	// 问：char 型变量中能不能存贮一个中文汉字。
	// 答：能,char型变量是用来存储Unicode编码的字符的，unicode编码字符集中包含了汉字，所以，char型变量中当然可以存储汉字啦。不过，如果某个特殊的汉字没有被包含在unicode编码字符集中，那么，这个char型变量中就不能存储这个特殊汉字。
	// 问：抽象类和接口有什么异同？
	// 答：相同点：都不能被直接实例化；不同点：接口支持多继承、接口成员都是抽象的、接口可用于回调
	// 问：String 对象为什么是不可变的？
	// 答：属性都是final的
	// 问：不可变对象的好处?
	// 答：多线程的时候没有被修改的风险、hashcode可以缓存
	// 问：object中有哪些方法?
	// 答：registerNatives、getClass、hashCode、equals、clone、toString、notify、notifyAll、wait
	// 集合类
	// 问：常用的List实现有哪些？
	// 答：ArrayList 和 LinkedList, CopyOnWriteArrayList
	// 问：ArrayList 和 LinkedList的区别
	// 答：ArrayList是array实现的，读更快；LinkedList是链表实现的，写更快
	// 问：ArrayList怎么扩容的，扩容到多大？
	// 答：申请空间，copy一份出去，1.5倍
	// 问：多个线程修改CopyOnWriteArrayList，是怎么处理并发的？
	// 答：copy一份修改完了，再将指针指过去；同时修改的时候原来对象不加锁，写时可以读
	// 问：使用场景？
	// 答：读多写少、不要求数据强一致、内存占用小
	// 问：HashMap原理, HashMap中元素的写入流程
	// 答：根据hashCode取模（位运算）选择bucket
	// 问：HashMap容量不够的时候怎么扩容的
	// 答：rehash
	// 问：new HashMap<>(10)，此时数组的个数应该是多少？
	// 答：tableSize应该是2的n次方
	// 问：hash相同,key不同的时候怎么办？
	// 答：塞入链表头部
	// 问：java8 对hashmap优化
	// 答：链表变成红黑树
	// 问：HashMap在使用时怎么初始化？HashMap的遍历有几种方式？
	// 问：HashMap遍历方式，1，通过遍历keySet, 逐个取value 2，遍历EntrySet， 取entry.key,
	// entry.value. 第二种方式效率更优，因为第一种通过key获取value，也是一种遍历。
	// 问：处理hash冲突：拉链法、再散列、公共溢出区、开放地址法
	// 问：ConcurrentHashMap原理
	// 答：线程同步基于Segment加锁，效率比对整个HashMap加锁效率高
	// 问：ConcurrentHashMap怎么实现线程安全
	// 答：分段锁
	// 问：ConcurrentHashMap默认多少个分段锁
	// 答：2的n次方
	// 问：与hashtable区别
	// 答：HashTable用synchronized保证线程安全，竞争激烈情况下效率低下
	// 问: ConcurrentHashMap是如何解决并发冲突的
	// 答: 使用Segment在每个桶上加锁
	// 问：ConcurrentModificationException异常发生的原因
	// 答：并发情况下，迭代器的modCount和expectedModCount的值不一致
	// 问：给你一个List，有20 Id，你怎么去重？
	// 答：HashSet
	// 问：HashSet的实现原理
	// 答：HashMap
	// 问：HashMap的key可以去重，那它的value设置的什么值？
	// 答：PRESENT = new Object();
	// 多线程
	// 问：reentrantlock，synchronized区别
	// 答：synchronized需要jvm实现、reentrantlock需要调用lock和unlock API
	// 问：使用场景
	// 答：资源竞争激烈用Reentrantlock，不激烈用synchronized，Sychronize关键字在jdk内部优化，竞争不激烈情况性能上会优于Lock，
	// Lock提供了可重入锁和读写锁。
	// 问：java 线程有哪些状态
	// 答：创建、就绪 、运行、阻塞、死亡
	// 问：如何转换
	//
	// 问：线程如何通信方式？
	// 答：共享内存：信号量synchronized、while轮询；消息传递：wait notify 、pipe管道
	// 问：Java线程同步方式？
	// 答：线程同步使用Sychronize & Lock方式
	// 问：CountDownLatch
	// 答：并发类、通过计数器实现、使一个线程等待其他线程完成各自工作后再执行
	// 问：Timer和ScheduleThreadPoolExecutor区别
	// 答：Timer指定绝对时间，单线程；ScheduleThreadPoolExecutor可以用相对时间，使用线程池
	// 问：new ThreadPool的参数？
	// 答：coreSize maxSize keepalivetime，timeunit，workqueue、 threadfactory
	// rejectedexecutionhandler
	// 问：线程的创建方式？
	// 答：
	// 1.继承Thread类，并复写run方法，创建该类对象，调用start方法开启线程。
	// 2.实现Runnable接口，复写run方法，创建Thread类对象，将Runnable子类对象传递给Thread类对象。调用start方法开启线程，第二种方式好，将线程对象和线程任务对象分离开。降低了耦合性，利于维护
	// 3.创建FutureTask对象，创建Callable子类对象，复写call(相当于run)方法，将其传递给FutureTask对象（相当于一个Runnable）。创建Thread类对象，将FutureTask对象传递给Thread对象。调用start方法开启线程。这种方式可以获得线程执行完之后的返回值。
	// 问：线程不烦忙的时候怎么回落的？
	// 答：回落到coreSize的线程数
	// 问：已经有5个线程, 有第6个线程的时候，线程池怎么处理的 (使用无界队列)
	// 答：
	// 首先线程池判断基本线程池是否已满？没满，创建一个工作线程来执行任务。满了，则进入下个流程。
	// 其次线程池判断工作队列是否已满？没满，则将新提交的任务存储在工作队列里。满了，则进入下个流程。（使用无界队列则队列不会满）
	// 最后线程池判断整个线程池是否已满？没满，则创建一个新的工作线程来执行任务，满了，则交给饱和策略来处理这个任务。
	// 问：newFixedThreadPool, newCachedThreadPool有什么区别
	// 答：前者对最大线程数有限制，后者没有限制
	// 问：这两个方法产生的线程池会有什么问题吗？
	// 答：都可能塞爆内存
	// 问：那怎么办？
	// 答：自己初始化线程池, 设置合理的线程数个队列大小，拒绝策略
	// 问：线程池原理：CacheExecutorService & FixedExecutorService &
	// SingleExecutorService
	// 问：Executors中FixedThreadPool，设置5个的大小，同时submit 10个任务，是怎么执行的？
	// 答：初始化的时候创建5个线程，另外5个排队 (错误)
	// 问：volatile的作用
	// 答：可见性，防止CPU指令重排
	// 问：原子类atomicinteger
	// 答：cas
	// 问：CAS原理
	// 答：campare and swap 乐观锁
	// 问：内存缓存行
	// 答：缓存中可以分配的最小存储单位、缓存行填充可以用来提升并发编程效率
	// 问：ThreadLocal作用？
	// 答：线程本地变量
	// 问：使用时候的注意点
	// 答：释放掉
	// 问：synchronized、reentrantlock区别
	// 答：锁竞争不激烈用synchronized，竞争激烈用reentrantlock，一般情况下用synchronized即可
	// 问：线程A调用线程B sleep方法，哪个线程睡眠
	// 答：线程A
	// 问：execute(),submit()区别
	// 答：execute()没返回值，submit()有返回值。submit()传入的任务实现callable 接口
	// 问：juc（java util concurrent）包原子类，自增如何实现
	// 问：sleep() 和wait() 有什么区别
	// 答：
	// 1、类：这两个方法来自不同的类分别是Thread和Object
	// 2、锁：最主要是sleep方法没有释放锁，而wait方法释放了锁，使得其他线程可以使用同步控制块或者方法
	// 3、适用范围：wait，notify和notifyAll只能在同步控制方法或者同步控制块里面使用，而sleep可以任何地方使用
	// 问：怎么唤醒wait()停止的线程
	// 答：notify、notifyAll
	// 问：NIO和BIO的区别
	// 答：非阻塞和阻塞
	// JVM
	// 问：jvm内存模型
	// 答：新生代（eden，2个survivor）、老年代、永久代（在Java 8中改成MetaSpace，大小限制为系统内存）
	// 问：所有的对象都是在eden分配吗？
	// 答：大对象可能直接在老年代分配
	// 问：启动参数
	// 答：java -Xmx12m -Xms3m -Xmn1m -XX:PermSize=20m -XX:MaxPermSize=20m
	// -XX:+UseSerialGC
	//
	// 配置
	// 描述
	// -Xms 初始化堆内存大小
	// -Xmx 堆内存最大值
	// -Xmn 新生代大小
	// -XX:PermSize 初始化永久代大小
	// -XX:MaxPermSize 永久代最大容量
	//
	// 问：垃圾回收过程
	// 答：minor gc、major gc
	// 问：有哪些引用类型
	// 答：
	//
	// 引用类型
	// 垃圾收集
	// 强引用（Strong Reference） 不符合垃圾收集
	// 软引用（Soft Reference） 垃圾收集可能会执行，但会作为最后的选择
	// 弱引用（Weak Reference） 符合垃圾收集
	// 虚引用（Phantom Reference） 符合垃圾收集
	//
	// 问：垃圾回收算法
	// 答：复制、标记-整理、标记清除（CMS）
	//
	// 问：常见的垃圾回收器？
	// 答：
	// Serial GC：适合单线程、命令行程序；缺点：暂停所有应用线程
	// Parallel GC：多线程收集；优点：吞吐量大；缺点：暂停所有应用线程
	// CMS：适合应用程序；优点回收较快，停顿时间短，缺点消耗CPU；STW的两种情况:
	// 永久代标记，并发增量标记；多线程可以减少标记时间，降低STW时间
	// G1：原理：分区域，堆压缩；Java 8优化：字符串去重，用同一个char[]存储相同的字符串，节省空间
	// 问：调优经验
	// 答：
	// 问: full gc时间过长怎么解决？
	// 答: 增加年老代空间大小
	// 问：一个大对象分配到销毁，在内存中的状态
	// 答：当对象很大的时候，会直接在年老带分配，对象销毁之后，full gc的时候回收
	// 问：那这个大对象引用了年轻代的小对象的时候，会扫描回收吗？
	// 答：会的
	// 问：线上应用full gc时间很长，你该怎么办？
	// 答：摘下机器，重启，先解决问题，dump
	// 问：服务器挂掉
	// 问：堆溢出
	// 答：大对象
	// 问：栈溢出
	// 答：递归层数过多
	// 问：持久态溢出
	// 答：创建很多不同的字符串、cglib加载类、不同的classLoader加载类
	// 问：工具
	// 答：jstat：查看垃圾回收情况，mat：进行死锁线程校验
	// 问：新生代对象什么时候移入到老年代
	// 答：新生代gc年龄加1，到达配置的年龄（例如15）移入到老年代
	// 问：java 堆如何垃圾回收
	// 答：分代：新生代、老年代、新生代8:1:1、新生代复制算法、老年代标记清除
	// 问：JVM内存模型
	// 答：堆、栈、方法区、计数器。
	// 问：什么时候触发类加载
	// 答：new、反射、加载父类、Class.forName、static
	// 问：类加载机制
	// 答：双亲委托
	// Spring
	// 问：Spring bean的作用域
	// 答：单例，prototype，request、session 、global session
	// 问：sping bean实例化过程
	// 答：
	// construct
	// property、BeanNameAware::setBeanName
	// InitializingBean::afterPropertiesSet
	// init-method
	// 问：spring mvc内部运行原理
	// 答：
	// DispatcherServlet
	// Handler（Controller）(通过url、HandlerMapping) 返回ModelAndView
	// ViewResolver将ModelAndView转换成View
	// 问：mybatis内部原理
	//
	// 问：spring ioc,aop的实现原理
	// 答：spring aop实现方式：jdk 实现invocationhandler、cglib
	// 两种方式区别：jdk需要共同接口，cglib不需要
	// jdk动态代理实现：proxy.newinstance、invocationhandler.invoke()
	// 问: redis在内存中的数据结构
	// 答: 知道hash的, 有一个配置，hash大小小于这个值的时候，是数组的形式；大于这个值的时候使用hash表存储
	// 问: 这个配置值的是hash元素的个数，还是元素的大小
	// 答: hash元素的个数
	// 问：设计模式的目的
	// 答：优雅性, 分层, 职责，面向对象, 职责,解耦, 关闭原则
	// 问：Spring怎么实现单例？
	// 答：IOC
	// 问：AOP的几种实现方式
	// 答：aspectj （interface，CGLib）
	// 问：spring事务传播属性
	// PROPAGATION_REQUIRED — 支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。
	// 　PROPAGATION_SUPPORTS — 支持当前事务，如果当前没有事务，就以非事务方式执行。
	// 　PROPAGATION_MANDATORY — 支持当前事务，如果当前没有事务，就抛出异常。
	// 　PROPAGATION_REQUIRES_NEW — 新建事务，如果当前存在事务，把当前事务挂起。
	// 　PROPAGATION_NOT_SUPPORTED — 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
	// 　PROPAGATION_NEVER — 以非事务方式执行，如果当前存在事务，则抛出异常。
	// 　PROPAGATION_NESTED —
	// 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。
	// 问：权限处理，用filter或AOP、过滤器, 拦截器
	// 问：spring 缺点思考
	// 设计模式
	// 问：spring的单例模式跟java的单例模式有什么区别？
	// 问：平时用到了哪些设计模式
	// 问：写个线程安全的单例
	// 问：策略与模板区别
	// 问：装饰与职责链

}
