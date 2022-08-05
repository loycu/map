/**
 * @author lotanyang
 * @date 2022/8/3 20:06
 **/
public class VolitailPrintNumberThread extends Thread{
    static volatile int number;

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
        while (true){
            if(number<100){
                System.out.println("number = " + number++);//乱序执行 100次
            }else {
                break;
            }
            }
        }
}
