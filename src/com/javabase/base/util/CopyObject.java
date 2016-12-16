package com.javabase.base.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 复制对象
 * 
 * @author bruce.
 *
 */
public class CopyObject
{

	private static Logger log = Logger.getLogger(CopyObject.class);

	/**
	 * 根据字段拷贝List数组到对象, 如果有日期格式则根据传进来的日期格式转换日期
	 * 
	 * @param src
	 *            list数组
	 * @param des
	 *            对象
	 * @param fields
	 *            字段名称
	 * @param dataformat
	 *            日期格式
	 * 
	 * @return 返回拷贝成功或是标识 －1为失败标识
	 */
	@SuppressWarnings("unchecked")
	public static String copyObjects(List src, Object des, String[] fields,
			String dataformat)
	{

		Class desClass = des.getClass();
		String result = "error";
		try
		{

			Method[] methoddes = desClass.getMethods();

			for (int i = 0; i < src.size(); i++)
			{

				for (Method method : methoddes)
				{

					if (!"Date".equals(method.getReturnType().getSimpleName())
							|| !"Timestamp".equals(method.getReturnType()
									.getSimpleName()))
					{

						if (method.getName().startsWith("set")
								&& method.getName().toLowerCase().equals(
										"set" + fields[i]))
						{

							String type = method.toGenericString();
							String str = src.get(i).toString();
							String gettype = type.substring(
									type.indexOf("(") + 1, type
											.lastIndexOf(")"));
							if (type.indexOf("java.util.Date") == -1
									&& type.indexOf("java.sql.Timestamp") == -1)
							{

								if ("int".equals(gettype)
										|| gettype.indexOf("Integer") != -1)
								{

									if ("".equals(str) || str == null)
									{
										method.invoke(des, 0);
									} else
									{
										System.out.println(gettype + " "
												+ method.getName() + " " + " "
												+ str);
										int temp = Integer.valueOf(str, 16);
										method.invoke(des, temp);
									}

								} else if ("double".equals(gettype)
										|| gettype.indexOf("Double") != -1)
								{

									if ("".equals(str) || str == null)
									{
										method.invoke(des, 0);
									} else
									{
										double temp = Double.parseDouble(str);
										method.invoke(des, temp);
									}

								} else
								{
									// System.out.println(gettype + " "
									// + method.getName() + " " + " "
									// + str);
									method.invoke(des, str);
								}
							} else
							{
								SimpleDateFormat format = new SimpleDateFormat(
										dataformat);
								Date date = null;
								if ("".equals(str) || str == null)
								{
									date = format.parse(new Date().toString());
								} else
								{
									date = format.parse(str);
								}
								if (type.indexOf("java.sql.Timestamp") != -1)
								{
									method.invoke(des, new Timestamp(date
											.getTime()));
								} else
								{
									method.invoke(des, date);
								}
							}
						}
					} else
					{
					}
				}
			}
			result = "ok";
		} catch (Exception e)
		{
			log.error("[级联上报]拷贝数据异常: " + e.getMessage());
		}
		return result;
	}

	/**
	 * 批量拷贝Bean对象
	 * 
	 * @param src
	 *            源端Bean对象
	 * @param des
	 *            目的端Bean对象
	 * @param copyNull
	 *            是否拷贝为Null对象
	 */
	@SuppressWarnings("unchecked")
	public static void copyFields(Object src, Object des, boolean copyNull)
	{
		final String get = "get";
		final String set = "set";
		final String is = "is";

		Class srcClass = src.getClass();
		Class desClass = des.getClass();

		Method[] methods = srcClass.getMethods();
		for (int i = 0; i < methods.length; i++)
		{
			Method method = methods[i];
			String methodName = method.getName();
			if ((methodName.startsWith(get) && !methodName.equals("getClass"))
					|| methodName.startsWith(is))
			{
				String invokeMethodName = set + methodName.substring(3);

				try
				{
					Method invokeMethod = desClass.getMethod(invokeMethodName,
							new Class[]
							{ method.getReturnType() });
					Object result = method.invoke(src, new Object[]
					{});

					if (!copyNull && result == null)
					{
						continue;
					}
					invokeMethod.invoke(des, new Object[]
					{ result });
				} catch (Exception e)
				{
					log.error("[级联上报]拷贝数据异常copyFields: " + e.getMessage());
				}
			}
		}
	}
}
