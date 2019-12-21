package com.project.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.project.key.SessionKeys;
import com.project.model.User;

public class SessionUtil {
	// 设置session
	public static void setSessionInfo(String key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.setAttribute(key, value);
	}

	public static User getUser() {
		return (User) SessionUtil.getSessionInfo(SessionKeys.LOGIN_USER);
	}

	// 得到session
	public static Object getSessionInfo(String key) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		return session.getAttribute(key);
	}
}
