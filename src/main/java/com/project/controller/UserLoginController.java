package com.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.key.SessionKeys;
import com.project.model.User;
import com.project.util.SessionUtil;
import com.util.base.StringUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.ExpressionList;

@RestController
@RequestMapping(value = "/user_login")
public class UserLoginController {
	@RequestMapping(value = "/go_login", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm goLogin() {
		AjaxForm ajaxForm = new AjaxForm();
		return ajaxForm.setError(-73, "未登录");
	}

	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm login(@RequestBody Map<String, String> params) {
		AjaxForm ajaxForm = new AjaxForm();
		
		String userAccount = params.get("userAccount");
		String userPwd = params.get("userPwd");
		if(StringUtil.isNullOrEmpty(userAccount)) {
			return ajaxForm.setError("用户名为空");
		}
		if(StringUtil.isNullOrEmpty(userPwd)) {
			return ajaxForm.setError("密码为空");
		}
		String md5Pwd = StringUtil.Md5BASE64(userPwd);
		ExpressionList<User> el = Ebean.find(User.class).where().eq("deleted", false);
		el.eq("account", userAccount);
		el.eq("passwd", md5Pwd);
		List<User> users = el.findList();
		if (users.size() == 0) {
			return ajaxForm.setError("用户名密码错误");
		}
		User user = users.get(0);
		SessionUtil.setSessionInfo(SessionKeys.LOGIN_USER, user);
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getAccount());
		currentUser.login(token);
		return ajaxForm.setSuccess(user);
	}

}
