package com.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.framework.controller.BaseController;
import com.model.Paginate;
import com.project.model.Book;
import com.project.model.Goods;
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
@RequestMapping(value = "/book")
public class BookController  extends BaseController{
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		Book model = ModelUtil.toModel(params, Book.class);
		if(model.getId()==null) {
			model.setAllQty(0);
			model.setSellQty(0);
		}
		model.saveOrUpdate();

		return ajaxForm.setSuccess(model);
	}
	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<Book> el = Ebean.find(Book.class).where().eq("deleted", false);
		String code = MapUtil.getString(params, "code");
		String name = MapUtil.getString(params, "name");
		EbeanELUtil.like(el, "code", code);
		EbeanELUtil.like(el, "name", name);
		Query<Book> query = el.orderBy("updatedAt desc");
		Paginate paginate = super.paginate(el, params);
		return paginate.toAjaxForm();
	}
	@RequestMapping(value = "/add_goods", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm addGoods(@RequestBody Map<String, String> params) {
		String id = params.get("id");
		String qty = params.get("qty");

		ExpressionList<Book> el = Ebean.find(Book.class).where().eq("deleted", false);
		Book model = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (model == null) {
			return ajaxForm.setError("错误");
		}
		Goods goods=new Goods();
		goods.setBook(model);
		goods.setQty(IntUtil.parseInt(qty));
		goods.save();
		model.setAllQty(model.getAllQty()+goods.getQty());
		model.update();
		return ajaxForm.setSuccess(model);
	}
	
	@RequestMapping(value = "/search_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchById(@RequestBody Map<String, String> params) {
		String id = params.get("id");

		ExpressionList<Book> el = Ebean.find(Book.class).where().eq("deleted", false);
		Book model = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (model == null) {
			return ajaxForm.setError("错误");
		}
		return ajaxForm.setSuccess(model);
	}
	@RequestMapping(value = "/search_goods_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchGoodsById(@RequestBody Map<String, String> params) {
		String id = params.get("id");
		AjaxForm ajaxForm = new AjaxForm();
		List<Goods> list = Ebean.find(Goods.class).where().eq("book.id", id).eq("deleted", false).orderBy("id desc").findList();
		return ajaxForm.setSuccess(list);
	}
}
