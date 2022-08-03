import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lotanyang
 * @date 2022/8/3 19:13
 **/
public class ReentreenLockTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Lock lock=new ReentrantLock();
        //执行顺序不一致,有序性
        //thread2 启动
        //thread1 = 执行
        Thread thread=new FirstThread();
        Thread thread1=new Thread(new RunnableThread());

        FutureTask futureTask = new FutureTask<>(new ThirdThread());
        Thread thread2=new Thread(futureTask);
        thread.start();
        thread1.start();
        thread2.start();
        while (true){
            if(futureTask.isDone()){
                System.out.println("futureTask.get() = " + futureTask.get());
                break;
            }
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,2,10, TimeUnit.DAYS,new ArrayBlockingQueue<>(2));
        RunnableThread runnableThread = new RunnableThread();
        Future<?> submit = threadPoolExecutor.submit(runnableThread);
        System.out.println("submit.get() = " + submit.get());
        Thread caller=new Caller();
        caller.start();
    }


    static class FirstThread extends Thread{

        /**
         * If this thread was constructed using a separate
         * <code>Runnable</code> run object, then that
         * <code>Runnable</code> object's <code>run</code> method is called;
         * otherwise, this method does nothing and returns.
         * <p>
         * Subclasses of <code>Thread</code> should override this method.
         *
         * @see #start()
         * @see #stop()
         * @see #Thread(ThreadGroup, Runnable, String)
         */
        @Override
        public void run() {
            System.out.println("thread1 = 执行");
        }
    }
    static class RunnableThread implements Runnable {
        /**
         * If this thread was constructed using a separate
         * <code>Runnable</code> run object, then that
         * <code>Runnable</code> object's <code>run</code> method is called;
         * otherwise, this method does nothing and returns.
         * <p>
         * Subclasses of <code>Thread</code> should override this method.
         *
         * @see #start()
         * @see #stop()
         * @see #Thread(ThreadGroup, Runnable, String)
         */
        @Override
        public void run() {
            System.out.println("thread2 启动");
        }
    }

    static class ThirdThread implements Callable{

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public Object call() throws Exception {
            return "ok";
        }
    }

    static class Caller extends Thread{

        /**
         * If this thread was constructed using a separate
         * <code>Runnable</code> run object, then that
         * <code>Runnable</code> object's <code>run</code> method is called;
         * otherwise, this method does nothing and returns.
         * <p>
         * Subclasses of <code>Thread</code> should override this method.
         *
         * @see #start()
         * @see #stop()
         * @see #Thread(ThreadGroup, Runnable, String)
         */
        @Override
        public void run() {
           Thread.currentThread().notify();
        }
    }
}
