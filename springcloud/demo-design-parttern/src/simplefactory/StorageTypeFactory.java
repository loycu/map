package simplefactory;

import simplefactory.HdfsStorageType;

/**
 * 简单工厂
 */
public class StorageTypeFactory {

    private final static String LOCAL = "local";
    private final static String FTP = "ftp";
    private final static String FASTDFS = "fastdfs";
    private final static String HDFS = "hdfs";

   public static IStorageType storageTypeCreate(String storageType) { 
        IStorageType iStorageType = null;
        switch (storageType) {
            case LOCAL:
                iStorageType = new LocalStorageType();
                break;
            case FTP:
                iStorageType = new FtpStorageType();
                break;
            case FASTDFS:
                iStorageType = new FastDfsStorageType();
                break;
            case HDFS:
                iStorageType = new HdfsStorageType();
                break;
        }
        return iStorageType;
    }

}