import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

/**
 * @author lotanyang
 * @date 2022/8/3 22:08
 **/
public class PrintX {
    public static void main(String[] args) {
        Object lock = new Object();
        Thread threadA = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (lock) {
                    System.out.println("A");
                    lock.notify();
                    try {
                        if (i < 9) { //最后一次不再wait，让程序可以正常退出
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread threadB = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (lock) {
                    System.out.println("B");
                    lock.notify();
                    try {
                        if (i < 9) {
                            lock.wait(); //最后一次不再wait，让程序可以正常退出
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadA.start();
        try {
            Thread.sleep(1000); //保证先打印A
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadB.start();
    }

    static public class Solution1 {

        private static Thread threadA = null;
        private static Thread threadB = null;

        public static void main(String[] args) {
            threadA = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    System.out.println("A");
                    LockSupport.unpark(threadB);
                    if (i < 9) { //最后一次不再park，让程序可以正常退出
                        LockSupport.park();
                    }
                }
            });
            threadB = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    System.out.println("B");
                    LockSupport.unpark(threadA);
                    if (i < 9) {
                        LockSupport.park(); //最后一次不再park，让程序可以正常退出
                    }
                }
            });
            threadA.start();
            try {
                Thread.sleep(1000); //保证先打印A
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadB.start();
        }
    }



    static public class Solution2 {
        public static void main(String[] args) {
            SynchronousQueue<Integer> queue = new SynchronousQueue<>(true); //要用公平模式
            Thread threadA = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("A");
                    try {
                        queue.put(1);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread threadB = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("B");
                    try {
                        if (i < 9) {
                            queue.put(1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadA.start();
            try {
                Thread.sleep(1000); //保证先A后B的阻塞顺序
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadB.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                queue.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static public class Solution3 {
        private static volatile boolean c = true; //使用volatile保证c的可见性
        public static void main(String[] args) {
            Thread threadA = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    while ( c) {

                    }
                    System.out.println("A");
                    c = true;
                }
            });
            Thread threadB = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    while (!c) {

                    }
                    System.out.println("B");
                    c = false;
                }
            });
            threadA.start();
            threadB.start();
        }
    }

    static public class Solution4 {

        public static void main(String[] args) {
            PrintRun printRun = new PrintRun();
            Thread thread1 = new Thread(printRun::run);
            Thread thread2 = new Thread(printRun::run);
            thread1.setName("线程1");
            thread2.setName("线程2");
            thread1.start();
            thread2.start();
        }

        public static class PrintRun{
            private final AtomicInteger count = new AtomicInteger(1); //也可替换成volatile
            private final Lock lock = new ReentrantLock();
            private final Condition single = lock.newCondition();
            private final Condition twin = lock.newCondition();
            public void run(){
                lock.lock();
                try {
                    while (count.get() <= 100){
                        if (count.get() % 2 == 1){
                            System.out.println(Thread.currentThread().getName()+"打印："+count.get());
                            twin.signal(); //不会真正唤醒twin，需要等待single释放锁
                            if (count.incrementAndGet() <= 100){
                                single.await(); //single等待并释放锁
                            }
                        }else {
                            System.out.println(Thread.currentThread().getName()+"打印："+count.get());
                            single.signal();
                            if (count.incrementAndGet() <= 100){
                                twin.await();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
    static public class Soluition5 {

        public static void main(String[] args) {
            PrintRun printRun = new PrintRun();
            Thread thread1 = new Thread(printRun);
            thread1.setName("线程1");
            Thread thread2 = new Thread(printRun);
            thread2.setName("线程2");
            thread1.start();
            thread2.start();
        }

        public static class PrintRun implements Runnable{
            volatile int i = 1;  //需要定义为volatile，保证线程可见性
            @Override
            public void run() {
                while (true){
                    synchronized (this){
                        notify(); //调用notify()不会直接唤醒下一个线程，需要等synchronized代码块执行完
                        System.out.println(Thread.currentThread().getName()+"打印："+i);
                        i++;
                        if (i < 100){
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else {
                            break;
                        }
                    }
                }
            }
        }
    }

    public static class Demo1 {

        private int count = 0;

        private final static int FULL = 10;

        private Lock lock;

        private Condition notEmptyCondition;

        private Condition notFullCondition;

        {
            lock = new ReentrantLock();
            notEmptyCondition = lock.newCondition();
            notFullCondition = lock.newCondition();

        }

        class Producer implements Runnable {

            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    lock.lock();
                    try {
                        while(count == FULL) {
                            try {
                                notFullCondition.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("生产者 " + Thread.currentThread().getName() + " 总共有 " + ++count + " 个资源");
                        notEmptyCondition.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        class Consumer implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    lock.lock();
                    try {
                        while(count == 0) {
                            try {
                                notEmptyCondition.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("消费者 " + Thread.currentThread().getName() + " 总共有 " + --count + " 个资源");
                        notFullCondition.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        public static void main(String[] args) {
            Demo1 demo1 = new Demo1();
            for (int i = 1; i <= 5; i++) {
                new Thread(demo1.new Producer(), "生产者-" + i).start();
                new Thread(demo1.new Consumer(), "消费者-" + i).start();
            }
        }
    }

    static public class Test2 {

        static int i = 1;
        //true:公平锁.否则结果不对
        static Semaphore semaphore = new Semaphore(2,true);

        public static void main(String[] args) throws InterruptedException {

            new Thread(new Task()).start();
            // 让第二个线程启动
            Thread.sleep(100);
            new Thread(new Task()).start();
            new Thread(new Task()).start();

        }
    }


    static class Task implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    // 获取不到就会阻塞在这里
                    Test2.semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(
                        Thread.currentThread().getName() + " : " + (Test2.i++));

                Test2.semaphore.release();

            }

        }

    }


    static public class Test3 {

        static int i = 1;


        public static void main(String[] args) throws InterruptedException {
            final Sync sync = new Sync(0);
            //new CountDownLatch()

            new Thread(new Task1(sync,0)).start();
            // 让第二个线程启动
            Thread.sleep(100);
            new Thread(new Task1(sync,2)).start();




        }
    }

    static class Sync extends AbstractQueuedSynchronizer {
        Sync(int state) {
            setState(state);
        }

        /**
         * CAS失败则阻塞
         * @param arg
         * @return
         */
        @Override
        public boolean tryAcquire(int arg) {
            if(compareAndSetState(arg,arg+1)) {
                return true;
            }
            return false;
        }

        public boolean tryRelease(int arg) {
            int state = getState();
            if(state == 1) {
                setState(2);
            }else if(state == 3){
                setState(0);
            }
            return true;
        }
    }

    static class Task1 implements Runnable {

        Sync sync;
        int initState;

        public Task1(Sync sync, int initState) {
            this.sync = sync;
            this.initState = initState;
        }


        @Override
        public void run() {
            while (true) {
                // 获取不到就会阻塞在这里
                sync.acquire(initState);
                System.out.println(Thread.currentThread().getName() + " : " + (Test2.i++));
                // 这个参数不写1也没事，因为我们tryRelease没用到
                sync.release(1);
            }

        }

    }

}
