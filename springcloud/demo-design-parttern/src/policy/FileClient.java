package policy;

/**
 * 策略模式 版本的上传文件代码
 */
public class FileClient {

    private final static String LOCAL = "local";
    private final static String FTP = "ftp";
    private final static String FASTDFS = "fastdfs";
    private final static String HDFS = "hdfs";

    public static void main(String[] args) {
        StorageContext storageContext = null;
        //模拟入参
        String storageType = "ftp";
        switch (storageType) {
            case LOCAL:
                //客户端需要知道具体有哪些策略，能做什么。但是不需要知道策略具体怎么做
                storageContext = new StorageContext(new LocalStorageStrategy());
                break;
            case FTP:
                storageContext = new StorageContext(new FtpStorageStrategy());
                break;
            case FASTDFS:
                storageContext = new StorageContext(new FastDfsStorageStrategy());
                break;
            case HDFS:
                storageContext = new StorageContext(new HdfsStorageStrategy());
                break;
        }
        storageContext.uploadFileAction("strategy.txt");
    }
}