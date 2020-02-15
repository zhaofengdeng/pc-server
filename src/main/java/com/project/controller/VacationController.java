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
import com.project.model.Student;
import com.project.model.Vacation;
import com.util.EbeanELUtil;
import com.util.EbeanPaginateUtil;
import com.util.EbeanUtil;
import com.util.base.MapUtil;
import com.util.base.ModelUtil;
import com.util.base.StringUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;
import javafx.scene.control.Pagination;

@RestController
@RequestMapping(value = "/vacation")
public class VacationController extends BaseController {
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		Vacation vacation = ModelUtil.toModel(params, Vacation.class);
		if(vacation.getId()==null) {
			vacation.setStudent(Ebean.find(Student.class).where().eq("id", MapUtil.getString(params, "studentId")).findOne());
		}
		vacation.saveOrUpdate();

		return ajaxForm.setSuccess(vacation);
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<Vacation> el = Ebean.find(Vacation.class).where().eq("deleted", false);
		String name = MapUtil.getString(params, "name");
		String no = MapUtil.getString(params, "no");
		EbeanELUtil.like(el, "student.no", no);
		EbeanELUtil.like(el, "student.name", name);
		
		Query<Vacation> query = el.orderBy("updatedAt desc");
		Paginate paginate = super.paginate(el, params);
		return paginate.toAjaxForm();
	}

	@RequestMapping(value = "/search_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchById(@RequestBody Map<String, String> params) {
		String id = params.get("id");

		ExpressionList<Vacation> el = Ebean.find(Vacation.class).where().eq("deleted", false);
		Vacation vacation = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (vacation == null) {
			return ajaxForm.setError("错误");
		}
		return ajaxForm.setSuccess(vacation);
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm delete(@RequestBody Map<String, String> params) {
		String id = params.get("id");

		ExpressionList<Vacation> el = Ebean.find(Vacation.class).where().eq("deleted", false);
		Vacation vacation = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (vacation == null) {
			return ajaxForm.setError("错误");
		}
		vacation.setDeleted(true);
		vacation.saveOrUpdate();
		return ajaxForm.setSuccess(vacation);
	}

}
