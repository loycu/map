package strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FastDfsStorageType implements IStorageType {

   @Autowired
   private FastDfsStorageType fastDfsStorageType;

   private final static String LOCAL = "local";

   @Override
   public void uploadFile(String file) {
       System.out.println("文件" + file + "已上传到 fastdfs服务器");
   }

   /**
    * 当前 bean 被实例化后，会执行下面方法把字符串和策略的对应关系传进去
    */
   @Override
   public void afterPropertiesSet() {
       StorageMapSingleton.getInstance().putStorageType(LOCAL, fastDfsStorageType);
   }
}