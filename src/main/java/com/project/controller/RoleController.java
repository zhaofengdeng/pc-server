package com.project.controller;

import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.framework.controller.BaseController;
import com.model.Paginate;
import com.project.config.PaginateConfig;
import com.project.model.Permission;
import com.project.model.Role;
import com.util.EbeanELUtil;
import com.util.EbeanPaginateUtil;
import com.util.EbeanUtil;
import com.util.base.MapUtil;
import com.util.base.ModelUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.Expr;
import io.ebean.Expression;
import io.ebean.ExpressionList;
import io.ebean.Query;
import javafx.scene.control.Pagination;

@RestController
@RequestMapping(value = "/role")
public class RoleController extends BaseController{
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		Role role = ModelUtil.toModel(params, Role.class);
		ExpressionList<Role> el = Ebean.find(Role.class).where().eq("deleted", false);
		el.ne("id", role.getId());
		Expression el1 = Expr.eq("name", role.getName());
		Expression el2 = Expr.eq("engName", role.getEngName());
		el.or(el1, el2);
		int count = el.findCount();
		if(count>0) {
			return ajaxForm.setError("该角色已存在");
		}
		role.saveOrUpdate();
		
		return ajaxForm.setSuccess(role);
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<Role> el = Ebean.find(Role.class).where().eq("deleted", false);
		String name =MapUtil.getString(params, "name");
		EbeanELUtil.like(el, "name", name);
		Query<Role> query = el.orderBy("updatedAt desc");
		Paginate paginate=super.paginate(el, params);
		return paginate.toAjaxForm();
	}
	@RequestMapping(value = "/search_all", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchAll() {
		AjaxForm ajaxForm=new AjaxForm();
		ExpressionList<Role> el = Ebean.find(Role.class).where().eq("deleted", false);
		List<Role> roles = el.orderBy("updatedAt desc").findList();
		return ajaxForm.setSuccess(roles);
	}

	@RequestMapping(value = "/search_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchById(@RequestBody Map<String, String> params) {
		String id = params.get("id");

		ExpressionList<Role> el = Ebean.find(Role.class).where().eq("deleted", false);
		Role role = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (role == null) {
			return ajaxForm.setError("错误");
		}
		return ajaxForm.setSuccess(role);
	}

}
