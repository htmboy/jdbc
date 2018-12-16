package cn.itcast.jdbc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.itcast.jdbc.domain.User;

public class ReflectTest { // 反射, 代码阅读性差, 但是, 灵活

	public static void main(String[] args) throws Exception {
		Class clazz = User.class;
		// clazz = Bean.class;
		Object obj = create(clazz); // 不做类型限制
		// User user = (User) create(User.class); // 为了方便, 重写toString() 
		System.out.println(obj);
		
		invoke1(obj, "showName");
		field(clazz);
	}
	
	static Object create(Class clazz) throws Exception { // 在不知道类的方法时, 可以用下面这种方法尝试找到, 并构造出实例
		Constructor con = clazz.getConstructor(String.class); // 找到参数为String的构造方法, 如果参数有String类型的, 就可以构造
		Object obj = con.newInstance("test name"); // 构造出实例, 有多个参数, 用逗号隔开
		return obj;
	}
	
	static void invoke1(Object obj, String methodName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException { // 先用get.Constructor 和 newInstance 构造出对象, 再传入, 
		Method[] ms = obj.getClass().getDeclaredMethods(); // java提供的 描述方法的, 也是对象. 把对象的共有和私有方法全部找到
		ms = obj.getClass().getMethods(); // 会把公共方法和超类的方法也拿到 , 一般用这个
		
		for(Method m : ms) {
			//System.out.println(m.getName());
			if(methodName.equals(m.getName()))
				m.invoke(obj, null); // 调用方法  参数说明, obj为实例, 第二个参数为方法参数
		}
		
		// 更精确的调用方法
		Method m = obj.getClass().getMethod(methodName, null);
		m.invoke(obj, null);
	}
	
	static void field(Class clazz) throws Exception { // 查找对象属性
		Field[] fs = clazz.getDeclaredFields(); // 找私有和共有的属性, 但不建议使用这个
		// fs = clazz.getFields(); // 只找共有的属性
		for(Field f : fs)
			System.out.println(f.getName());
	}
	
	static void annon(Class clazz) throws Exception { // Annotation 英语为 注释
		Annotation[] as = clazz.getAnnotations();
	}
}
