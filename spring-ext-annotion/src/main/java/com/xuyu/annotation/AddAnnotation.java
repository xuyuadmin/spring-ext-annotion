package com.xuyu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
//运行时调用这段代码，不加则反射不到
@Retention(RetentionPolicy.RUNTIME)
// @interface 定义注解
public @interface AddAnnotation {

	// 手写Spring事务注解
	int userId() default 1;

	String userName() default "须臾";

	String[]arrays();
}
