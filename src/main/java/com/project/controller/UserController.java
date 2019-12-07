package com.project.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.Paginate;
import com.project.config.PaginateConfig;
import com.project.model.User;
import com.util.EbeanPaginateUtil;
import com.util.EbeanUtil;
import com.util.base.ModelUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import javafx.scene.control.Pagination;

@RestController
@RequestMapping(value = "/user")
public class UserController   {
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		System.out.println(params.get("id"));
		User user = ModelUtil.toModel(params, User.class);
		user.saveOrUpdate();
		AjaxForm ajaxForm = new AjaxForm();
		return ajaxForm.setSuccess(user);
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, String> params) {
		ExpressionList<User> el = Ebean.find(User.class).where().eq("deleted", false);
		Paginate users = EbeanPaginateUtil.paginate(el, params.get(PaginateConfig.KEY_CUR_PAGE),
				params.get(PaginateConfig.KEY_MAX_PER_PAGE));
		AjaxForm ajaxForm = new AjaxForm();
		return ajaxForm.setSuccess(users);
	}
	@RequestMapping(value = "/search_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchById(@RequestBody Map<String, String> params) {
		String id = params.get("id");
		
		ExpressionList<User> el = Ebean.find(User.class).where().eq("deleted", false);
		User user = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if(user==null) {
			return ajaxForm.setError("错误");
		}
		return ajaxForm.setSuccess(user);
	}

}
