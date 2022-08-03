/**
 * @author lotanyang
 * @date 2022/8/3 19:49
 **/
public class JohnWait {

    public static void main(String[] args) throws InterruptedException {
        RunnableThread thread1 = new WhileThead("thread1");
        RunnableThread thread2 = new WhileThead("thread2");
        RunnableThread thread3 = new WhileThead("thread3");
        Thread thread11 = new Thread(thread1);
        Thread thread22 = new Thread(thread2);
        Thread thread33 = new Thread(thread3);
        thread11.start();
        thread22.start();
        thread22.join();//优先执行
        thread33.start();

    }
}
