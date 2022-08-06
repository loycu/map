package simplefactory;

/**
	 * 抽出各个方式共同的行为接口：上传文件
	 */
	public interface IStorageType {
	    void uploadFile(String file);
	}