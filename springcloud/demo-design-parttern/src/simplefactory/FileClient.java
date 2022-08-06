package simplefactory;

/**
 * 简单工厂模式 版本的上传文件代码
 */
public class FileClient {

    public static void main(String[] args) {
        IStorageType iStorageType = StorageTypeFactory.storageTypeCreate("hdfs");
        iStorageType.uploadFile("simpleFactory.txt");
    }
}
