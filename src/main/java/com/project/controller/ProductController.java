package com.project.controller;

import java.util.List;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.framework.controller.BaseController;
import com.model.Paginate;
import com.project.model.Product;
import com.project.model.User;
import com.util.EbeanELUtil;
import com.util.base.IntUtil;
import com.util.base.MapUtil;
import com.util.base.ModelUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;

@RestController
@RequestMapping(value = "/product")
public class ProductController  extends BaseController{
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		Product model = ModelUtil.toModel(params, Product.class);
		if(model.getId()==null) {
			model.setAllQty(0);
			model.setSellQty(0);
		}
		model.saveOrUpdate();

		return ajaxForm.setSuccess(model);
	}
	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<Product> el = Ebean.find(Product.class).where().eq("deleted", false);
		String name = MapUtil.getString(params, "name");
		EbeanELUtil.like(el, "name", name);
		Query<Product> query = el.orderBy("updatedAt desc");
		Paginate paginate = super.paginate(el, params);
		return paginate.toAjaxForm();
	}
	@RequestMapping(value = "/add_product", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm addGoods(@RequestBody Map<String, String> params) {
		String id = params.get("id");
		String qty = params.get("qty");

		ExpressionList<Product> el = Ebean.find(Product.class).where().eq("deleted", false);
		Product model = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (model == null) {
			return ajaxForm.setError("错误");
		}
		model.setAllQty(model.getAllQty()+IntUtil.parseInt(qty));
		model.update();
		return ajaxForm.setSuccess(model);
	}
	
	@RequestMapping(value = "/search_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchById(@RequestBody Map<String, String> params) {
		String id = params.get("id");

		ExpressionList<Product> el = Ebean.find(Product.class).where().eq("deleted", false);
		Product model = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (model == null) {
			return ajaxForm.setError("错误");
		}
		return ajaxForm.setSuccess(model);
	}

}
