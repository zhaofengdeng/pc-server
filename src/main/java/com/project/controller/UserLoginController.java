package com.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.key.SessionKeys;
import com.project.model.LogUserLogin;
import com.project.model.User;
import com.project.util.SessionUtil;
import com.util.base.ModelUtil;
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
		return ajaxForm.setError(-100, "未登录");
	}

	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm logout() {
		AjaxForm ajaxForm = new AjaxForm();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.removeAttribute(SessionKeys.LOGIN_USER);
		session.removeAttribute(SessionKeys.PERMISSION_URLS);
		if (currentUser.isAuthenticated()) {
			currentUser.logout();
		}
		return ajaxForm.setSuccess(null);
	}

	@RequestMapping(value = "/change_passwd", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm changePasswd(@RequestBody Map<String, String> params) {
		AjaxForm ajaxForm = new AjaxForm();
		User user = SessionUtil.getUser();
		String passwd = params.get("passwd");
		if (user == null) {
			return ajaxForm.setError("修改失败，检测该用户会话失效");
		}
		if(StringUtil.isNullOrEmpty(passwd)) {
			return ajaxForm.setError("修改失败，请输入新的密码");
		}
		User newUser = Ebean.find(User.class).where().eq("id", user.getId()).findOne();
		newUser.setPasswd(StringUtil.Md5BASE64(passwd));
		newUser.saveOrUpdate();
		return ajaxForm.setSuccess("密码修改成功");
	}
	@RequestMapping(value = "/regist", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		User user = ModelUtil.toModel(params, User.class);
		user.setMoney(0D);
		user.setType("顾客");
		user.setEnable(true);
		user.save();
		

		return ajaxForm.setSuccess("注册成功，密码为123456，为保证安全请登录系统后尽快修改");
	}
	@RequestMapping(value = "/no_permission", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm noPermission() {
		AjaxForm ajaxForm = new AjaxForm();
		return ajaxForm.setError(-101, "没有权限");
	}

	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm login(@RequestBody Map<String, String> params) {
		AjaxForm ajaxForm = new AjaxForm();

		String userAccount = params.get("userAccount");
		String userPwd = params.get("userPwd");
		if (StringUtil.isNullOrEmpty(userAccount)) {
			return ajaxForm.setError("用户名为空");
		}
		if (StringUtil.isNullOrEmpty(userPwd)) {
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
		try {
			SessionUtil.setSessionInfo(SessionKeys.LOGIN_USER, user);
			Subject currentUser = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getAccount());
			currentUser.login(token);
			new LogUserLogin().save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ajaxForm.setSuccess(user);
	}

}
