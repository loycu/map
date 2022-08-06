package policy;

public class FtpStorageStrategy extends StorageStrategy {
    @Override
    public void uploadFile(String file) {
        System.out.println("文件" + file + "已上传到 ftp服务器");
    }
}