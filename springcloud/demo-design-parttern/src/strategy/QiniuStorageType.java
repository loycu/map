package strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QiniuStorageType implements IStorageType {
    @Autowired
    private QiniuStorageType qiniuStorageType;

    private final static String QINIU = "qiniu";

    
    @Override
    public void uploadFile(String file) {
        System.out.println("文件" + file + "已上传到七牛云");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StorageMapSingleton.getInstance().putStorageType(QINIU, qiniuStorageType);
    }
}