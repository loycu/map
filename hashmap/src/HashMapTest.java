import java.util.HashMap;

/**
 * @author lotanyang
 * @date 2022/8/3 17:22
 **/
public class HashMapTest {

    public static void main(String[] args) {

//        e.hash & oldCap
        int result=162 & 16;
        System.out.println("162&16 = " + result);

        int result1=15 & 16;
        System.out.println("15&16 = " + result1);

        int result2=48 & 16;
        System.out.println("32&16 = " + result2);
//        testResize();
    }

    private static void testResize() {
        HashMap<Object, Object> map = new HashMap<>(5);
        System.out.println("map.size() = " + map.size());
        for (int i = 0; i < 5; i++) {
            map.put(i,i);
        }
        System.out.println("map.size() = " + map.size());
        map.forEach((x,y)->{
            System.out.println("x = " + x);
            System.out.println("y = " + y);
        });
    }

}
