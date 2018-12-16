package cn.itcast.jdbc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.itcast.jdbc.domain.User;

public class ReflectTest { // ����, �����Ķ��Բ�, ����, ���

	public static void main(String[] args) throws Exception {
		Class clazz = User.class;
		// clazz = Bean.class;
		Object obj = create(clazz); // ������������
		// User user = (User) create(User.class); // Ϊ�˷���, ��дtoString() 
		System.out.println(obj);
		
		invoke1(obj, "showName");
		field(clazz);
	}
	
	static Object create(Class clazz) throws Exception { // �ڲ�֪����ķ���ʱ, �������������ַ��������ҵ�, �������ʵ��
		Constructor con = clazz.getConstructor(String.class); // �ҵ�����ΪString�Ĺ��췽��, ���������String���͵�, �Ϳ��Թ���
		Object obj = con.newInstance("test name"); // �����ʵ��, �ж������, �ö��Ÿ���
		return obj;
	}
	
	static void invoke1(Object obj, String methodName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException { // ����get.Constructor �� newInstance ���������, �ٴ���, 
		Method[] ms = obj.getClass().getDeclaredMethods(); // java�ṩ�� ����������, Ҳ�Ƕ���. �Ѷ���Ĺ��к�˽�з���ȫ���ҵ�
		ms = obj.getClass().getMethods(); // ��ѹ��������ͳ���ķ���Ҳ�õ� , һ�������
		
		for(Method m : ms) {
			//System.out.println(m.getName());
			if(methodName.equals(m.getName()))
				m.invoke(obj, null); // ���÷���  ����˵��, objΪʵ��, �ڶ�������Ϊ��������
		}
		
		// ����ȷ�ĵ��÷���
		Method m = obj.getClass().getMethod(methodName, null);
		m.invoke(obj, null);
	}
	
	static void field(Class clazz) throws Exception { // ���Ҷ�������
		Field[] fs = clazz.getDeclaredFields(); // ��˽�к͹��е�����, ��������ʹ�����
		// fs = clazz.getFields(); // ֻ�ҹ��е�����
		for(Field f : fs)
			System.out.println(f.getName());
	}
	
	static void annon(Class clazz) throws Exception { // Annotation Ӣ��Ϊ ע��
		Annotation[] as = clazz.getAnnotations();
	}
}
