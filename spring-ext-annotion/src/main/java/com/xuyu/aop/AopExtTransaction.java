package com.xuyu.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xuyu.annotation.ExtTransaction;
import com.xuyu.transaction.TransactionUtils;


//  �Զ�������ע�����ʵ��
@Aspect
@Component
@Scope("prototype")
public class AopExtTransaction {
	// һ������ʵ���� ���һ������
	@Autowired
	private TransactionUtils transactionUtils;

	// ʹ���쳣֪ͨ���� �ع�����
	@AfterThrowing("execution(* com.xuyu.service.*.*.*(..))")
	public void afterThrowing(TransactionStatus transactionStatus) {
		// ��ȡ��ǰ������лع�
		//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		//�Дஔǰ�ղ����գ��؝L
		if(transactionStatus!=null) {
			transactionUtils.rollback();
		}
	}

	// ����֪ͨ �ڷ���֮ǰ��֮��������
	@Around("execution(* com.xuyu.service.*.*.*(..))")
	public void around(ProceedingJoinPoint pjp) throws Throwable {

		// 1.��ȡ�÷������Ƿ����ע��
		ExtTransaction extTransaction = getMethodExtTransaction(pjp);
		TransactionStatus transactionStatus = begin(extTransaction);
		// 3.����Ŀ�������󷽷�
		pjp.proceed();
		// 4.�жϸ÷������Ƿ����ע�⣬�����ύ����
		commit(transactionStatus);
	}
	// 2.�����������ע��,��������
	private TransactionStatus begin(ExtTransaction extTransaction) {
		if (extTransaction == null) {
			return null;
		}
		return transactionUtils.begin();
	}
	// 4.�������ע��,�ύ����
	private void commit(TransactionStatus transactionStatus) {
		if (transactionStatus != null) {
			transactionUtils.commit(transactionStatus);
		}

	}

	// ��ȡ�������Ƿ��������ע��
	private ExtTransaction getMethodExtTransaction(ProceedingJoinPoint pjp)
			throws NoSuchMethodException, SecurityException {
		//��ȡĿ����󷽷����� add
		String methodName = pjp.getSignature().getName();
		System.out.println(methodName);
		// ��ȡĿ����� class com.xuyu.service.impl.UserServiceImpl
		Class<?> classTarget = pjp.getTarget().getClass();
		System.out.println(classTarget);
		// ��ȡĿ��������� [Ljava.lang.Class;@1c1bbc4e
		Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
		System.out.println(par);
		// ����Ŀ����󷽷����ƺͶ������ͣ���ȡĿ����󷽷�·�� public void com.xuyu.service.impl.UserServiceImpl.add()
		Method objMethod = classTarget.getMethod(methodName, par);
		System.out.println(objMethod);
		ExtTransaction extTransaction = objMethod.getDeclaredAnnotation(ExtTransaction.class);
		return extTransaction;
	}

}
