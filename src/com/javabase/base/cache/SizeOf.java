package com.javabase.base.cache;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Use to estimate an object memory size.
 * 
 * @author bruce
 * 
 */
public final class SizeOf {
	
   private static final Map<Class, Integer> primitiveSizes = new IdentityHashMap<Class, Integer>() {
      {
         put(boolean.class, new Integer(1));
         put(byte.class, new Integer(1));
         put(char.class, new Integer(2));
         put(short.class, new Integer(2));
         put(int.class, new Integer(4));
         put(float.class, new Integer(4));
         put(double.class, new Integer(8));
         put(long.class, new Integer(8));
      }
   };

   public static long estimate(Object obj) {
      Map visited = new IdentityHashMap();
      Stack stack = new Stack();
      long result = _estimate(visited, stack, obj);

      while(!stack.isEmpty()) {
         result += _estimate(visited, stack, stack.pop());
      }

      visited.clear();
      return result;
   }

   private static boolean skipObject(Map visited, Stack stack, Object obj) {
      return (obj == null) || visited.containsKey(obj);
   }

   private static long _estimate(Map visited, Stack stack, Object obj) {
      if(skipObject(visited, stack, obj)) {
         return 0;
      }

      visited.put(obj, null);
      long result = 0;
      Class clazz = obj.getClass();

      if(clazz.isArray()) {
         return _estimateArray(visited, stack, obj);
      }

      while(clazz != null) {
         Field[] fields = clazz.getDeclaredFields();

         for(int i = 0; i < fields.length; i++) {
            if(!Modifier.isStatic(fields[i].getModifiers())) {
               if(fields[i].getType().isPrimitive()) {
                  result += getPrimitiveFieldSize(fields[i].getType());
               }
               else {
                  result += getPointerSize();
                  fields[i].setAccessible(true);

                  try {
                     Object toBeDone = fields[i].get(obj);

                     if(toBeDone != null) {
                        stack.add(toBeDone);
                     }
                  }
                  catch(IllegalAccessException ex) {
                     assert false;
                  }
               }
            }
         }

         clazz = clazz.getSuperclass();
      }

      result += getClassSize();
      return roundUpToNearestEightBytes(result);
   }

   private static long roundUpToNearestEightBytes(long result) {
      if((result % 8) != 0) {
         result += 8 - (result % 8);
      }

      return result;
   }

   protected static long _estimateArray(Map visited, Stack stack, Object obj) {
      long result = 16;
      int length = Array.getLength(obj);

      if(length != 0) {
         Class arrayElementClazz = obj.getClass().getComponentType();

         if(arrayElementClazz.isPrimitive()) {
            result += length * getPrimitiveArrayElementSize(arrayElementClazz);
         }
         else {
            for(int i = 0; i < length; i++) {
               result += getPointerSize()
                  + _estimate(visited, stack, Array.get(obj, i));
            }
         }
      }

      return result;
   }

   private static int getPrimitiveFieldSize(Class clazz) {
      return ((Integer) primitiveSizes.get(clazz)).intValue();
   }

   private static int getPrimitiveArrayElementSize(Class clazz) {
      return getPrimitiveFieldSize(clazz);
   }

   private static int getPointerSize() {
      return 4;
   }

   private static int getClassSize() {
      return 8;
   }
}