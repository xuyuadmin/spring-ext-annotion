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


//  自定义事务注解具体实现
@Aspect
@Component
@Scope("prototype")
public class AopExtTransaction {
	// 一个事务实例子 针对一个事务
	@Autowired
	private TransactionUtils transactionUtils;

	// 使用异常通知进行 回滚事务
	@AfterThrowing("execution(* com.xuyu.service.*.*.*(..))")
	public void afterThrowing(TransactionStatus transactionStatus) {
		// 获取当前事务进行回滚
		//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		//判斷當前事務不爲空，回滾
		if(transactionStatus!=null) {
			transactionUtils.rollback();
		}
	}

	// 环绕通知 在方法之前和之后处理事情
	@Around("execution(* com.xuyu.service.*.*.*(..))")
	public void around(ProceedingJoinPoint pjp) throws Throwable {

		// 1.获取该方法上是否加上注解
		ExtTransaction extTransaction = getMethodExtTransaction(pjp);
		TransactionStatus transactionStatus = begin(extTransaction);
		// 3.调用目标代理对象方法
		pjp.proceed();
		// 4.判断该方法上是否加上注解，存在提交事务
		commit(transactionStatus);
	}
	// 2.如果存在事务注解,开启事务
	private TransactionStatus begin(ExtTransaction extTransaction) {
		if (extTransaction == null) {
			return null;
		}
		return transactionUtils.begin();
	}
	// 4.如果存在注解,提交事务
	private void commit(TransactionStatus transactionStatus) {
		if (transactionStatus != null) {
			transactionUtils.commit(transactionStatus);
		}

	}

	// 获取方法上是否存在事务注解
	private ExtTransaction getMethodExtTransaction(ProceedingJoinPoint pjp)
			throws NoSuchMethodException, SecurityException {
		//获取目标对象方法名称 add
		String methodName = pjp.getSignature().getName();
		System.out.println(methodName);
		// 获取目标对象 class com.xuyu.service.impl.UserServiceImpl
		Class<?> classTarget = pjp.getTarget().getClass();
		System.out.println(classTarget);
		// 获取目标对象类型 [Ljava.lang.Class;@1c1bbc4e
		Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
		System.out.println(par);
		// 根据目标对象方法名称和对象类型，获取目标对象方法路径 public void com.xuyu.service.impl.UserServiceImpl.add()
		Method objMethod = classTarget.getMethod(methodName, par);
		System.out.println(objMethod);
		ExtTransaction extTransaction = objMethod.getDeclaredAnnotation(ExtTransaction.class);
		return extTransaction;
	}

}
