package com.project.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.project.key.SessionKeys;
import com.project.model.User;


//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm {

	// 角色权限和对应权限添加
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/* 这里编写授权代码 */
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Subject currentUser = SecurityUtils.getSubject();
		Object objectUser = currentUser.getSession().getAttribute(SessionKeys.LOGIN_USER);
		Set<String> permissions = new HashSet<String>();
		Set<String> roles = new HashSet<>();
		if (objectUser != null) {
			User user = (User) objectUser;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", user.getId());
			permissions.add("user");

		}
		info.setStringPermissions(permissions);
		info.setRoles(roles);
		return info;

	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		/* 这里编写认证代码 */
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SimpleAuthenticationInfo auth = null;
		auth = new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());

		return auth;
	}

	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
}
