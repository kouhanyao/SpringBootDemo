package com.example.demo.utils;

import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *  参数获取
 * @author: youzg
 * @version: 1.0
 * @since 2016-11-23
 */
public final class RequestUtil {

	public static final String EMPTY_STRING = "";

	/**
	 * 特殊的参数
	 */
	public static final Set<String> SPECIALS = new HashSet<String>();

	static {
	}

	/**
	 * 不能创建实例
	 */
	private RequestUtil() {

	}

	/**
	 * 返回 键值对应 Map
	 * 
	 * @param request
	 * @return
	 */
	public final static Map<String, Object> getRequestMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Enumeration<String> elements = request.getParameterNames(); elements.hasMoreElements();) {
			String key = elements.nextElement();
			String[] values = request.getParameterValues(key);
			map.put(key,StringUtil.join(values, ","));
		}
		return map;
	}

	/**
	 * 返回 键值对应 Map
	 *
	 * @param request
	 * @return
	 */
	public final static Map<String, Object> getRequestHeader(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Enumeration<String> elements = request.getHeaderNames(); elements.hasMoreElements();) {
			String key = elements.nextElement();
			Enumeration<String> values = request.getHeaders(key);
			map.put(key,StringUtil.join(Collections.list(values), ","));
		}
		return map;
	}
	
	/**
	 * 返回 键值对应 Map
	 * 
	 * @param request
	 * @return
	 */
	public final static Map<String, Object> getParamMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Enumeration<String> elements = request.getParameterNames(); elements.hasMoreElements();) {
			String key = (String) elements.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}

	/**
	 * 返回 键值对应 Map
	 * 
	 * @param request
	 * @return
	 */
	public final static Map<String, Object> getRequestMap(WebRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator<String> elements = request.getParameterNames(); elements.hasNext();) {
			String key = (String) elements.next();
			try {
				map.put(key, new String(request.getParameter(key).getBytes("ISO-8859-1"),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
			}
		}
		return map;
	}

	/**
	 * 返回 键值对应 Map
	 * 
	 * @param request
	 * @return
	 */
	public final static Map<String, Object> getQueryMap(HttpServletRequest request) {
		return getQueryMap(request, "ckb");
	}

	/**
	 * 返回 键值对应 Map
	 * 
	 * @param request
	 * @return
	 */
	public final static Map<String, Object> getQueryMap(HttpServletRequest request, String prefix) {
		Map<String, Object> qryMap = getRequestMap(request);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (Iterator iter = qryMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			String entryKey = entry.getKey().toString();
			if (SPECIALS.contains(entryKey)) {
				paramMap.put(entryKey, entry.getValue());
			} else if (entryKey.startsWith(prefix) && entryKey.length() > 0) {
				String paramKey = entryKey.replaceAll("^(" + prefix + ")", "");
				String key = StringUtil.firstCharLowerCase(paramKey);
				String value = (String) qryMap.get(key);
				paramMap.put(key, value);
			}
		}
		return paramMap;
	}

	/**
	 * 获取参数 默认返回空
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public final static String getParameter(HttpServletRequest request, String param) {
		return request.getParameter(param);
	}
	
	/**
	 * 获取参数 默认返回空
	 * 
	 * @param request
	 * @param param 参数名
	 * @param defValue 默认值
	 * @return
	 */
	public final static String getParameter(HttpServletRequest request, String param , String defValue) {
		String value = getParameter(request, param);
		return StringUtil.isValid(value) ? value : defValue;
	}
	
	/**
	 * 取得<html:base/>
	 * @param request
	 * @return
	 */
	public final static String getBaseURL(HttpServletRequest request) {
		String scheme = request.getScheme();
		String server = request.getServerName();
		int port = request.getServerPort();
		String uri = request.getRequestURI();
		StringBuilder url = new StringBuilder(128);
        url.append(scheme);
        url.append("://");
        url.append(server);
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(uri);
        return url.toString();
	}

	/**
	 * 获取参数 默认返回空
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public final static String getString(HttpServletRequest request, String param) {
		String value = request.getParameter(param);
		return value != null ? value.trim() : EMPTY_STRING;
	}

	/**
	 * 获取参数
	 * 
	 * @param request
	 * @param param
	 * @param defaultValue
	 */
	public final static String getString(HttpServletRequest request, String param, String defaultValue) {
		String value = getString(request, param);
		return value.length() > 0 ? value : defaultValue;
	}

	/**
	 * 获取整型数
	 * 
	 * @param request
	 * @param param
	 */
	public final static Integer getInt(HttpServletRequest request, String param) {
		String value = getString(request, param);
		return value.length() > 0 ? Integer.parseInt(value) : null;
	}

	/**
	 * 获取整型数
	 * 
	 * @param request
	 * @param param
	 * @param defaultValue
	 */
	public final static Integer getInt(HttpServletRequest request, String param, int defaultValue) {
		String value = getString(request, param);
		return value.length() > 0 ? Integer.parseInt(value) : defaultValue;
	}

	/**
	 * 获取长整型数
	 */
	public final static Long getLong(HttpServletRequest request, String param) {
		String value = getString(request, param);
		return value.length() > 0 ? Long.parseLong(value) : null;
	}

	/**
	 * 获取长整型数
	 */
	public final static Long getLong(HttpServletRequest request, String param, long defaultValue) {
		String value = getString(request, param);
		return value.length() > 0 ? Long.parseLong(value) : defaultValue;
	}

	/**
	 * 获取参数是有效
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public final static Boolean getBoolean(HttpServletRequest request, String param) {
		String value = getString(request, param);
		return value.length() > 0;
	}

	/**
	 * 获取参数是有效
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	public final static Boolean isValid(HttpServletRequest request, String param) {
		return getBoolean(request, param);
	}

	/**
	 * 获取 Double
	 * 
	 * @param request
	 * @param param
	 */
	public final static Double getDouble(HttpServletRequest request, String param) {
		String value = getString(request, param);
		return value.length() > 0 ? Double.parseDouble(value) : null;
	}

	/**
	 * 获取 Session 中的属性
	 */
	public final static Object getSessionAttribute(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return session.getAttribute(key);
	}

	/**
	 * 设置 Session 中的属性
	 */
	public final static void setSessionAttribute(HttpServletRequest request, String key, Object value) {
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
	}

	/**
	 * 设置 Session 中的属性
	 */
	public final static void removeSessionAttribute(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		session.removeAttribute(key);
	}

	/**
	 * 获取 request 中的属性
	 * 
	 * @param request
	 * @param name
	 */
	public final static Object getRequestAttribute(ServletRequest request, String name) {
		return request.getAttribute(name);
	}

	/**
	 * 获取字符串数组
	 * 
	 * @param request
	 * @param param
	 */
	public final static String[] getValues(HttpServletRequest request, String param) {
		String[] values = request.getParameterValues(param);
		return values != null ? values : new String[0];
	}

	/**
	 * 获取字符串数组
	 * 
	 * @param request
	 * @param param
	 */
	public final static String[] getParameterValues(HttpServletRequest request, String param, String[] defaultValues) {
		String[] values = getValues(request, param);
		return values.length > 0 ? values : defaultValues;
	}
	
	/**
	 * 获取整型数组
	 * 
	 * @param request
	 * @param param
	 */
	public final static Integer[] getIntValues(HttpServletRequest request, String param) {
		String[] stringValues = getValues(request, param);
		Integer[] intValues = new Integer[stringValues.length];
		for (int i = 0; i < stringValues.length; i++) {
			intValues[i] = Integer.parseInt(stringValues[i]);
		}
		return intValues;
	}
	
	/**
	 * 取得客户端IP
	 * @param request
	 * @return
	 */
    public static String getClientIP(HttpServletRequest request) {
        String remoteIP = request.getHeader("X-Forwarded-For");
        String UNKNOWN = "unknown";
        if (!StringUtil.isValid(remoteIP) || UNKNOWN.equalsIgnoreCase(remoteIP)) {
            remoteIP = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtil.isValid(remoteIP) || UNKNOWN.equalsIgnoreCase(remoteIP)) {
            remoteIP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtil.isValid(remoteIP) || UNKNOWN.equalsIgnoreCase(remoteIP)) {
            remoteIP = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtil.isValid(remoteIP) || UNKNOWN.equalsIgnoreCase(remoteIP)) {
            remoteIP = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtil.isValid(remoteIP) || UNKNOWN.equalsIgnoreCase(remoteIP)) {
            remoteIP = request.getRemoteAddr();
        }
        return remoteIP;
    }
}