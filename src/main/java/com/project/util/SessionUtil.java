package com.project.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class SessionUtil {
	// 设置session
	public static void setSessionInfo(String key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.setAttribute(key, value);
	}

	// 得到session
	public static Object getSessionInfo(String key) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return session.getAttribute(key);
	}
}
