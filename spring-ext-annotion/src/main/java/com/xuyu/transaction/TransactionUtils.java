package com.xuyu.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

//���������Ҫ�ֶ�begin �ֶ��ع� �ֶ��ύ��
@Component
@Scope("prototype") // ÿ���������µ�ʵ�� Ŀ�Ľ���̰߳�ȫ���� ������
public class TransactionUtils {

	// ȫ�ֽ�������״̬
	private TransactionStatus transactionStatus;
	// ��ȡ����Դ
	@Autowired
	private DataSourceTransactionManager dataSourceTransactionManager;

	// ��������
	public TransactionStatus begin() {
		System.out.println("��������");
		transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
		return transactionStatus;
	}

	// �ύ����
	public void commit(TransactionStatus transaction) {
		System.out.println("�ύ����");
		dataSourceTransactionManager.commit(transaction);
	}

	// �ع�����
	public void rollback() {
		System.out.println("�ع�����...");
		dataSourceTransactionManager.rollback(transactionStatus);
	}

}
