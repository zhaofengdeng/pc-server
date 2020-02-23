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
import com.project.model.Product;
import com.project.model.ProductDetail;
import com.project.model.User;
import com.util.EbeanELUtil;
import com.util.EbeanPaginateUtil;
import com.util.EbeanUtil;
import com.util.base.IntUtil;
import com.util.base.MapUtil;
import com.util.base.ModelUtil;
import com.util.base.StringUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;
import javafx.scene.control.Pagination;

@RestController
@RequestMapping(value = "/product_detail")
public class ProductDetailController extends BaseController {
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, String> params) {
		AjaxForm ajaxForm = new AjaxForm();
		String productId = params.get("productId");
		String userId = params.get("userId");
		String comment = params.get("comment");
		String score = params.get("score");
		Product product = Ebean.find(Product.class).where().eq("id", productId).findOne();
		User user = Ebean.find(User.class).where().eq("id", userId).findOne();
		ProductDetail model=new ProductDetail();
		model.setUser(user);
		model.setProduct(product);
		model.setScore(IntUtil.parseInt(score));
		model.setComment(comment);
		model.save();
		return ajaxForm.setSuccess(user);
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<ProductDetail> el = Ebean.find(ProductDetail.class).where().eq("deleted", false);
		String productId = MapUtil.getString(params, "productId");
		EbeanELUtil.eq(el, "product.id", productId);
		Query<ProductDetail> query = el.orderBy("updatedAt desc");
		Paginate paginate = super.paginate(el, params);
		return paginate.toAjaxForm();
	}



}
