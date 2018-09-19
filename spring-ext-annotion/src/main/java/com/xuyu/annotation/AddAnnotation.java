package com.xuyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
//����ʱ������δ��룬�������䲻��
@Retention(RetentionPolicy.RUNTIME)
// @interface ����ע��
public @interface AddAnnotation {

	// ��дSpring����ע��
	int userId() default 1;

	String userName() default "����";

	String[]arrays();
}
