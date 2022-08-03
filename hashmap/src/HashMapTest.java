import java.util.HashMap;
import java.util.Objects;

/**
 * @author lotanyang
 * @date 2022/8/3 17:22
 **/
public class HashMapTest {

    public static void main(StringTest[] args) {

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


    public static class Person {
        Integer id;
        String name;

        public Person(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            if (obj instanceof Person) {
                Person person = (Person) obj;
                if (this.id == person.id)
                    return true;
            }
            return false;
        }

        public static void main(String[] args) {
            Person p1 = new Person(1, "aaa");
            Person p2 = new Person(1, "bbb");
            HashMap<Person, String> map = new HashMap<>();
            map.put(p1, "这是p1");
            System.out.println(map.get(p2));
        }
    }
}
