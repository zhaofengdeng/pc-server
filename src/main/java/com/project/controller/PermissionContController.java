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
import com.project.model.PermissionController;
import com.util.EbeanELUtil;
import com.util.base.MapUtil;
import com.util.base.ModelUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;

@RestController
@RequestMapping(value = "/permission_controller", method = RequestMethod.POST)
public class PermissionContController extends BaseController {
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		PermissionController model = ModelUtil.toModel(params, PermissionController.class);
		System.out.println(model.getNode().getId());
		model.saveOrUpdate();
		return ajaxForm.setSuccess(model);
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<PermissionController> el = Ebean.find(PermissionController.class).where().eq("deleted", false);
		String name = MapUtil.getString(params, "name");
		String url = MapUtil.getString(params, "url");
		EbeanELUtil.like(el, "name", name);
		EbeanELUtil.like(el, "url", url);
		Query<PermissionController> query = el.orderBy("sort");
		Paginate paginate = super.paginate(el, params);
		return paginate.toAjaxForm();
	}

	@RequestMapping(value = "/search_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchById(@RequestBody Map<String, String> params) {
		String id = params.get("id");

		ExpressionList<PermissionController> el = Ebean.find(PermissionController.class).where().eq("deleted", false);
		PermissionController permissionController = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (permissionController == null) {
			return ajaxForm.setError("错误");
		}
		return ajaxForm.setSuccess(permissionController);
	}
}
