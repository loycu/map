/**
 * @author lotanyang
 * @date 2022/8/3 19:56
 **/
public class PrintNumber {
    public static void main(String[] args) {
//        VolitailPrintNumberThread volitailPrintNumberThread = new VolitailPrintNumberThread();
//        VolitailPrintNumberThread volitailPrintNumberThread2 = new VolitailPrintNumberThread();
//        volitailPrintNumberThread.start();
//        volitailPrintNumberThread2.start();
        //order print
        PrintNumberNormal printNumberNormal = new PrintNumberNormal(false);
        PrintNumberNormal printNumberNorma2 = new PrintNumberNormal(true);
        printNumberNormal.start();
        printNumberNorma2.start();
    }
}
