package com.project.config;

import java.util.ArrayList;
import java.util.List;

public class ViewURLConfig {
	private static final List<String> urls=new ArrayList<>();
	static {
		urls.add("/log/page/begin");
		urls.add("/log/page/end");
		urls.add("/user_login/go_login");
		urls.add("/user_login/login");
		urls.add("/user_login/no_permission");
		urls.add("/user_login/logout");
		urls.add("/user_login/change_passwd");
	}
	public static List<String> getUrls() {
		return urls;
	}
	
}
