package com.project.util;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.project.config.ViewURLConfig;
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



	public static boolean hasPermission(HttpSession session, String url) {
		if (url.indexOf("//") == 0) {
			url = url.substring(1);
		}
		for (String viewUrl : ViewURLConfig.getUrls()) {
			if (viewUrl.equals(url)) {
				return true;
			}
		}
		Map<String, String> map = (Map<String, String>) session.getAttribute(SessionKeys.PERMISSION_URLS);
		
		if(map==null) {
			return true;
		}
		if (map.containsKey(url)) {
			return true;
		}
		return false;
	}
}
