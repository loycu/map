package policy;

/**
 * 策略模式的上下文。
 * 这里策略模式让我觉得有些类似像代理模式，于是去查了一下。
 * 简单代理模式与策略模式在功能上的很大的区别是：
 *
 * 简单代理模式中，代理类知道被代理类的行为，因为代理类与被代理类实现的是同一个接口，因此代理类与被代理类的结构是相同的；
 *
 * 而策略模式中，策略容器并不知道内部策略的详细信息，因为容器并没有实现与内部策略相同的接口，
 * 即容器与内部策略只是简单的组合关系，容器只是将内部策略的行为抽取出来，进行了统一的实现。
 *
 */
public class StorageContext {

    private StorageStrategy storageStrategy;

    public StorageContext(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public void uploadFileAction(String file){
        storageStrategy.uploadFile(file);
    }
}