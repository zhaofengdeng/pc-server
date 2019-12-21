package com.project.controller;

import java.util.List;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.framework.controller.BaseController;
import com.model.Paginate;
import com.project.model.Role;
import com.project.model.Permission;
import com.util.EbeanELUtil;
import com.util.base.MapUtil;
import com.util.base.ModelUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;

@RestController
@RequestMapping(value = "/permission", method = RequestMethod.POST)
public class PermissionController extends BaseController {
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		Permission model = ModelUtil.toModel(params, Permission.class);
//		List<String> roleIds = MapUtil.getList(params, "roleIds");
//		List<Role> roles = Ebean.find(Role.class).where().in("id", roleIds).findList();
//		if (roles.size() != roleIds.size()) {
//			return ajaxForm.setError("角色选择异常!");
//		}
//		model.setRoles(roles);
		model.saveOrUpdate();
		return ajaxForm.setSuccess(model);
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<Permission> el = Ebean.find(Permission.class).where().eq("deleted", false);
		String name = MapUtil.getString(params, "name");
		String url = MapUtil.getString(params, "url");
		EbeanELUtil.like(el, "name", name);
		EbeanELUtil.like(el, "url", url);
		Query<Permission> query = el.orderBy("sort");
		Paginate paginate = super.paginate(el, params);
		return paginate.toAjaxForm();
	}

	@RequestMapping(value = "/search_all", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchAll() {
		AjaxForm ajaxForm = new AjaxForm();
		ExpressionList<Permission> el = Ebean.find(Permission.class).where().eq("deleted", false);
		List<Permission> list = el.orderBy("sort asc").findList();
		return ajaxForm.setSuccess(list);
	}

	@RequestMapping(value = "/search_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchById(@RequestBody Map<String, String> params) {
		String id = params.get("id");

		ExpressionList<Permission> el = Ebean.find(Permission.class).where().eq("deleted", false);
		Permission permissionController = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (permissionController == null) {
			return ajaxForm.setError("错误");
		}
		return ajaxForm.setSuccess(permissionController);
	}
}
