package com.javabase.base.cache;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/**
 * 对象类型所占字节大小.
 * 
 * @author bruce
 *
 */
public class ObjBytesSizeUtil {

	private ObjBytesSizeUtil() {
		
	}

	static final Hashtable pSizes;
	
	static {
		pSizes = new Hashtable();
		pSizes.put(Boolean.TYPE, new Integer(1));
		pSizes.put(Byte.TYPE, new Integer(1));
		pSizes.put(Void.TYPE, new Integer(1));
		
		pSizes.put(Character.TYPE, new Integer(2));
		pSizes.put(Short.TYPE, new Integer(2));
		
		pSizes.put(Integer.TYPE, new Integer(4));
		pSizes.put(Float.TYPE, new Integer(4));
		
		pSizes.put(Long.TYPE, new Integer(8));
		pSizes.put(Double.TYPE, new Integer(8));
	}

	private static final class Root {
		
	}

	private static void sizeof(Object obj, IdentityHashMap known, Hashtable types) {
		Stack stack = new Stack();
		stack.push(new Root().getClass());
		stack.push(obj);
		int rounds = 0;
		while (!stack.empty()) {
			Object o = stack.pop();
			Object parentClass = stack.pop();
			known.put(o, o);
			rounds += 1;
			int size = 8; 
			Class oc = o.getClass();
			if (oc.isArray()) {
				size += 8;
				int alen = Array.getLength(o);
				Class ec = oc.getComponentType();
				Integer l = (Integer) pSizes.get(ec);
				if (l != null) {
					size = size + alen * l.intValue();
				} else {
					size = size + 4 * alen;
					Object[] oa = (Object[]) o;
					for (int i = 0; i < alen; i++) {
						Object child = oa[i];
						if (child != null && !known.containsKey(child)) {
							stack.push(parentClass);
							stack.push(child);
						}
					}
				}
			} else {
				Class c = oc;
				while (c != null) {
					Field[] fields = c.getDeclaredFields();
					for (int i = 0; i < fields.length; i++) {
						Field f = fields[i];
						if (Modifier.isStatic(f.getModifiers())) {
							continue;
						}
						Class fc = f.getType();
						Integer l = (Integer) pSizes.get(fc);
						if (l != null) {
							size += l.longValue();
							continue;
						}
						size += 4;
						Object child;
						f.setAccessible(true);
						try {
							child = f.get(o);
							if (child == null || known.containsKey(child)) {
								continue;
							}
							stack.push(oc); 
							stack.push(child);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					c = c.getSuperclass();
				}
			}

			Map parents = (Map) types.get(oc);
			if (parents == null) {
				types.put(oc, parents = new HashMap());
			}
			Pair p = (Pair) parents.get(parentClass);
			if (p == null) {
				p = new Pair();
				parents.put(parentClass, p);
			}
			p.count += 1;
			p.size += size;
		}
	}

	public static Hashtable sizeof(Object o) {
		Hashtable types = new Hashtable();
		sizeof(o, new IdentityHashMap(), types);
		return types;
	}

	public static long printTypes(Hashtable types) {
		Enumeration it = types.keys();
		long count = 0, size = 0;
		while (it.hasMoreElements()) {
			Class c = (Class) it.nextElement();
			Map parents = (Map) types.get(c);
			Iterator pit = parents.keySet().iterator();
			while (pit.hasNext()) {
				Class parenClass = (Class) pit.next();
				Pair p = (Pair) parents.get(parenClass);
				String pname = parenClass.getName();
				count += p.count;
				size += p.size;
			}
		}
		return size;
	}
	
	public static class Pair {
		public int count;
		public long size;
	}
	
	public static void main(String[] args) {
		Object object = new Object();  //8
		boolean tag = false;
		int numtest = 1;  //8+4
		double doubletest = 5.23;  //8+8
		String[] strs = new String[]{""}; //
		String str = "我是中国人，我爱我的祖国！"; 
		System.out.println(printTypes(sizeof(object)));
		System.out.println(printTypes(sizeof(tag)));
		System.out.println(printTypes(sizeof(numtest)));
		System.out.println(printTypes(sizeof(doubletest)));
		System.out.println(printTypes(sizeof(strs)));
		System.out.println(printTypes(sizeof(str)));
		
	}
}