package com.liangxunwang.unimanager.util;

import com.qiniu.common.QiniuException;
import com.qiniu.processing.OperationManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class Ops {
	
	private Auth auth = Auth.create(ControllerConstants.QINIU_AK, ControllerConstants.QINIU_SK);

	private OperationManager operater = new OperationManager(auth);
	 /**
     * �����־û�
     * @param bucket �ռ���
     * @param key    �ļ���
     * @param fops   fopָ��
     * @param params notifyURL��force��pipeline �Ȳ���
     * @return persistentId
     * @throws com.qiniu.common.QiniuException ����ʧ���쳣������������Ӧ����Ϣ
     */
    public String oper(String bucket, String key, String fops, StringMap params) throws QiniuException {
//		String bucket, String key, String fops, StringMap params
    	String persistentId = operater.pfop(bucket, key, fops, params);
		return persistentId;
        
    }
	
}
