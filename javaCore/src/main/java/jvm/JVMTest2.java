package jvm;

class SingleTon1{
    //在上面先赋值
    private static SingleTon1 instance=new SingleTon1();
    public static int count1;
    public static int count2=0;//重复赋值

    public SingleTon1(){
        count1++;
        count2++;
    }

    public static SingleTon1 getInstance() {
        return instance;
    }
}

//类加载
public class JVMTest2 {
    public static void main(String[] args) {
        SingleTon1.getInstance();
        System.out.println(SingleTon1.count1);//1
        System.out.println(SingleTon1.count2);//0
    }
}