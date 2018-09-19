package com.xuyu.annotation;

import java.lang.reflect.Method;
public class User {

	@AddAnnotation(userName = "������", userId = 18, arrays = { "1" })
	public void add() {

	}

	public void del() {

	}

	public static void main(String[] args) throws ClassNotFoundException {
		// ��ô����ȡ��������ע����Ϣ �������
		Class<?> forName = Class.forName("com.xuyu.annotation.User");
		// ��ȡ����ǰ�ࣨ�������̳У����еķ���
		Method[] declaredMethods = forName.getDeclaredMethods();
		for (Method method : declaredMethods) {
			// ��ȡ�÷������Ƿ����ע��
			System.out.println("####��������" + method.getName());
			AddAnnotation addAnnotation = method.getDeclaredAnnotation(AddAnnotation.class);
			if (addAnnotation == null) {
				// �÷�����û��ע��
				System.out.println("�÷�����û�м�ע��..");
				continue;
			}
			// �ڸ÷����ϲ��ҵ���ע��
			System.out.println("userId:" + addAnnotation.userId());
			System.out.println("userName:" + addAnnotation.userName());
			System.out.println("arrays:" + addAnnotation.arrays());
			System.out.println();
		}
	}

}
