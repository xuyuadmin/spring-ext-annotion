package com.xuyu;

import java.util.ArrayList;

/**
 * 解释注解类型
 * @author Administrator
 *
 */
public class Test002 {

	// @Override 表示为重写
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Deprecated
	public static void add() {
		// // api 过时
		// new Date().parse("");
	}

	public static void main(String[] args) {
		new ArrayList<>();
		add();
	}

}
