package strategy;

import org.springframework.beans.factory.InitializingBean;

public interface IStorageType extends InitializingBean {
    void uploadFile(String file);
}