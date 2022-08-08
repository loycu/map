package jvm;

class SingleTon{
    public static int count1;
    public static int count2=0;
    private static SingleTon instance=new SingleTon();
    public SingleTon(){
        count1++;
        count2++;
    }

    public static SingleTon getInstance() {
        return instance;
    }
}

//类加载
public class JVMTest {
    public static void main(String[] args) {
        SingleTon.getInstance();
        System.out.println(SingleTon.count1);//1
        System.out.println(SingleTon.count2);//1
    }
}