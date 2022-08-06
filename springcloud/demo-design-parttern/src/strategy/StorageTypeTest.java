package strategy;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = StorageApplication.class)
public class StorageTypeTest {


    @Before
    public void testBefore(){
        System.out.println("测试前");
    }

    @After
    public void testAfter(){
        System.out.println("测试后");
    }

    @Test
    public void storageTest(){
        IStorageType iStorageType = StorageMapSingleton.getInstance().getStorageType("hdfs");
        iStorageType.uploadFile("策略模式.txt");
    }
}