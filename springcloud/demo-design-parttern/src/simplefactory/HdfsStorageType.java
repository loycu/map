package simplefactory;

public class HdfsStorageType implements IStorageType {
    @Override
    public void uploadFile(String file) {
        System.out.println("文件" + file + "已上传到 hdfs服务器");
    }
}