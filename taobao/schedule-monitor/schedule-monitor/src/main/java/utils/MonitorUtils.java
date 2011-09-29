/**
 * @(#) MonitorUtils.java Created on 2010-12-30 下午02:47:32
 * Copyright (c) 2010 by Taobao.com.
 */
package utils;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 类名称：MonitorUtils
 * 类描述：工具类
 * 创建人：jishao
 * 创建时间：2010-12-30 下午02:47:32
 * 
 */
public class MonitorUtils {

	private static transient Log logger = LogFactory.getLog(MonitorUtils.class);

	public static String[] StatProps = new String[] { "fetchDataNum",
			"fetchDataCount", "dealDataSucess", "dealDataFail",
			"dealSpendTime", "otherCompareCount" };

	/**
	 * setComponentsValue(为对象的变量赋值)
	 * @param o 需要赋值的对象  p 变量名 v 值
	 * @Exception 异常对象
	 * @author jishao
	 * @since 2010-12-30 下午02:55:18
	 */
	public void setComponentsValue(Object o, String p, Object v) {
		Field[] fields = o.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			try {
				boolean accessFlag = fields[i].isAccessible();
				if (p.equals(varName)) {
					fields[i].setAccessible(true);
					fields[i].setLong(o, Long.parseLong((String) v));
					logger.info("ScheduleStat传入的变量" + varName + "值：" + v);
				}
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

}
