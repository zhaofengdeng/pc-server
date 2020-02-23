package com.project.controller;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.framework.controller.BaseController;
import com.model.Paginate;
import com.project.model.Product;
import com.project.model.Order;
import com.project.model.OrderDetail;
import com.project.model.User;
import com.util.EbeanELUtil;
import com.util.base.MapUtil;
import com.util.base.ModelUtil;
import com.util.base.StringUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;
import io.ebean.SqlRow;

@RestController
@RequestMapping(value = "/order")
public class OrderController extends BaseController {
	@RequestMapping(value = "/add_product", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm addBook(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		String productId = MapUtil.getString(params, "productId");
		String userId = MapUtil.getString(params, "userId");
		if (StringUtil.isNullOrEmpty(productId)) {
			return ajaxForm.setError("productId为空");
		}
		if (StringUtil.isNullOrEmpty(userId)) {
			return ajaxForm.setError("userId为空");
		}
		User user = Ebean.find(User.class).where().eq("id", userId).findOne();
		if (user == null) {
			return ajaxForm.setError("userId异常");
		}
		Product product = Ebean.find(Product.class).where().eq("id", productId).findOne();
		if (user == null) {
			return ajaxForm.setError("productId异常");
		}
		List<Order> orders = Ebean.find(Order.class).where().eq("user.id", userId).eq("deleted", false).eq("status", 0)
				.findList();
		Order order = null;
		if (orders.size() > 0) {
			order = orders.get(0);
		} else {
			order = new Order();
			order.setUser(user);
			order.setStatus(0);
			order.setNo("O" + StringUtil.getFileNameBySysDate());
			order.save();
		}
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setProduct(product);
		orderDetail.setMoney(product.getMoney());
		orderDetail.setOrder(order);
		orderDetail.save();
		order.updatePayMoney();

		return ajaxForm.setSuccess(null);
	}

	@RequestMapping(value = "/delete_book", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm deleteBook(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		String id = MapUtil.getString(params, "id");
		if (StringUtil.isNullOrEmpty(id)) {
			return ajaxForm.setError("请求异常");
		}
		OrderDetail model = Ebean.find(OrderDetail.class).where().eq("id", id).findOne();
		if (model == null) {
			return ajaxForm.setError("操作异常");
		}
		model.setDeleted(true);
		model.update();
		model.getOrder().updatePayMoney();
		return ajaxForm.setSuccess(null);
	}

	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		Order order = ModelUtil.toModel(params, Order.class);
		order.update();

		return ajaxForm.setSuccess(order);
	}

	@RequestMapping(value = "/payment", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm payment(@RequestBody Map<String, String> params) {
		AjaxForm ajaxForm = new AjaxForm();
		String id = params.get("id");
		String flag = params.get("flag");

		List<OrderDetail> list = Ebean.find(OrderDetail.class).where().eq("deleted", false).eq("order.id", id)
				.findList();
		if (list.size() == 0) {
			return ajaxForm.setError("订单异常，无法付款");
		}
		for (OrderDetail orderDetail : list) {
			Product book = orderDetail.getProduct();
			if (book.getAllQty() - book.getSellQty() <= 0) {
				return ajaxForm.setError(book.getName() + "已售完");
			}
		}
		Order order = list.get(0).getOrder();
		Double money = order.getPayMoney();
		User user = order.getUser();
		if (user.getMoney() < money) {
			return ajaxForm.setError("账户余额不够");
		}
		order.setFlag(flag);
		order.setInsertedAt(new Date());
		user.setMoney(user.getMoney() - money);
		user.update();
		order.setStatus(1);
		order.setPayStatus(1);
		order.update();
		for (OrderDetail orderDetail : list) {
			Product book = orderDetail.getProduct();
			book.setSellQty(book.getSellQty() + 1);
			book.update();
		}
		return ajaxForm.setSuccess(user);
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<Order> el = Ebean.find(Order.class).where().eq("deleted", false);
		String status = MapUtil.getString(params, "status");
		String payStatus = MapUtil.getString(params, "payStatus");
		String userId = MapUtil.getString(params, "userId");
		EbeanELUtil.eq(el, "user.id", userId);
		EbeanELUtil.eq(el, "status", status);
		EbeanELUtil.eq(el, "payStatus", payStatus);
		Query<Order> query = el.orderBy("id desc");
		Paginate paginate = super.paginate(el, params);
		return paginate.toAjaxForm();
	}
	@RequestMapping(value = "/search_details", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchDetails(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		ExpressionList<OrderDetail> el = Ebean.find(OrderDetail.class).where().eq("deleted", false);
		String userId = MapUtil.getString(params, "userId");
		el.eq("order.user.id", userId);
		el.eq("order.status", 0);
		Query<OrderDetail> query = el.orderBy("updatedAt desc");
		return ajaxForm.setSuccess(query.findList());
	}
	@RequestMapping(value = "/search_details_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchDetailsById(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		String id = MapUtil.getString(params, "id");
		ExpressionList<OrderDetail> el = Ebean.find(OrderDetail.class).where().eq("deleted", false);
		el.eq("order.id", id);
		Query<OrderDetail> query = el.orderBy("updatedAt desc");
		return ajaxForm.setSuccess(query.findList());
	}


}
