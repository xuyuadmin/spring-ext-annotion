package com.xuyu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuyu.annotation.ExtTransaction;
import com.xuyu.dao.UserDao;
import com.xuyu.service.LogService;
import com.xuyu.service.UserService;


//user �����
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private LogService logService;
	// @Autowired
	// private TransactionUtils transactionUtils;

	// spring �����װ�أ� aop����
	// public void add() {
	// TransactionStatus transactionStatus = null;
	// try {
	// // ��������
	// transactionStatus = transactionUtils.begin();
	// userDao.add("test001", 20);
	// System.out.println("��ʼ������!@!!");
	// // int i = 1 / 0;
	// System.out.println("################");
	// userDao.add("test002", 21);
	// // �ύ����
	// if (transactionStatus != null)
	// transactionUtils.commit(transactionStatus);
	// } catch (Exception e) {
	// e.getMessage();
	// // �ع�����
	// if (transactionStatus != null)
	// transactionUtils.rollback(transactionStatus);
	// }
	// }

	// ������@Transactional ����XML��ʽ
	// ����ִ�п�ʼִ��ǰ�������ύ����
	// @ExtTransaction
	// public void add() {
	// try {
	// userDao.add("test001", 20);
	// int i = 1 / 0;
	// System.out.println("################" + i);
	// userDao.add("test002", 21);
	// } catch (Exception e) {
	// // ��ȡ��ǰ�����ֶ����лع�
	// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	// }
	// // begin commit/rollback
	// }
	//
	 @ExtTransaction
	//@Transactional
	public void add() {
		// ���ýӿڵ�ʱ�� �ӿ�ʧ�� ��Ҫ�ع���������־��¼����Ҫ�ع���
		logService.addLog(); // ������������󣬲���Ӱ�쵽�ҵĻع�### ������addLog����ִ����ϣ���Ӧ���ύ����
		userDao.add("test001", 20);
		// int i = 1 / 0;
		System.out.println("################");
		userDao.add("test002", 21);

	}
	// ����ִ�����֮�󣬲Ż��ύ����

	public void del() {
		System.out.println("del");
	}

}
