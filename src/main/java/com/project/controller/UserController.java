package com.project.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.framework.controller.BaseController;
import com.model.Paginate;
import com.project.config.PaginateConfig;
import com.project.model.User;
import com.util.EbeanELUtil;
import com.util.EbeanPaginateUtil;
import com.util.EbeanUtil;
import com.util.base.MapUtil;
import com.util.base.ModelUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import javafx.scene.control.Pagination;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController{
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		User user = ModelUtil.toModel(params, User.class);
		user.saveOrUpdate();
		AjaxForm ajaxForm = new AjaxForm();
		return ajaxForm.setSuccess(user);
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<User> el = Ebean.find(User.class).where().eq("deleted", false);
		String name =MapUtil.getString(params, "name");
		String account =MapUtil.getString(params, "account");
		EbeanELUtil.like(el, "account", account);
		EbeanELUtil.like(el, "name", name);
		Paginate paginate=super.paginate(el, params);
		return paginate.toAjaxForm();
	}

	@RequestMapping(value = "/search_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchById(@RequestBody Map<String, String> params) {
		String id = params.get("id");

		ExpressionList<User> el = Ebean.find(User.class).where().eq("deleted", false);
		User user = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (user == null) {
			return ajaxForm.setError("错误");
		}
		return ajaxForm.setSuccess(user);
	}

}
