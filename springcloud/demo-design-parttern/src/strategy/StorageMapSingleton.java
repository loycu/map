package strategy;

import java.util.HashMap;

/**
 * 使用了饿汉式的单例模式
 */
public class StorageMapSingleton {

    private HashMap<String, IStorageType> map = new HashMap<>();

    private static StorageMapSingleton storageMapSingleton = new StorageMapSingleton();

    private StorageMapSingleton(){

    }

    public static StorageMapSingleton getInstance(){
        return storageMapSingleton;
    }

    public IStorageType getStorageType(String type){
        return map.get(type);
    }

    /**
     * 这里使用默认的访问权限，允许在同一个package下面调用该方法。当前类必须和具体的实现类在同一个package下
     * @param type
     * @param storageType
     */
    void putStorageType(String type, IStorageType storageType){
        map.put(type, storageType);
    }

}