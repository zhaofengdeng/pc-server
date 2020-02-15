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
import com.project.model.Notice;
import com.project.model.Permission;
import com.project.model.Role;
import com.project.model.Room;
import com.project.model.Student;
import com.project.model.User;
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
@RequestMapping(value = "/student")
public class StudentController extends BaseController {
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		String roomNo = MapUtil.getString(params, "roomNo");
		Room room = Ebean.find(Room.class).where().eq("no", roomNo).eq("deleted", false).findOne();
		if(room==null) {
			return ajaxForm.setError("寝室号错误");
		}
		Student student = ModelUtil.toModel(params, Student.class);
		if(student.getId()==null) {
			User user=new User();
			user.setAccount(student.getNo());
			user.setName(student.getName());
			user.setPasswd(StringUtil.Md5BASE64("123456"));
			user.setEnable(true);
			user.setType("学生");
			user.save();
			student.setUser(user);
		}
		
		student.setRoom(room);
		student.saveOrUpdate();
		
		return ajaxForm.setSuccess(student);
	}
	@RequestMapping(value = "/search_by_user_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchByUserId(@RequestBody Map<String, Object> params) {
		String userId = MapUtil.getString(params, "userId");
		Student student = Ebean.find(Student.class).where().eq("deleted", false).eq("user.id",userId).findOne();
		return new AjaxForm().setSuccess(student);
	}
	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<Student> el = Ebean.find(Student.class).where().eq("deleted", false);
		String name = MapUtil.getString(params, "name");
		String no = MapUtil.getString(params, "no");
		EbeanELUtil.like(el, "no", no);
		EbeanELUtil.like(el, "name", name);
		Query<Student> query = el.orderBy("updatedAt desc");
		Paginate paginate = super.paginate(el, params);
		return paginate.toAjaxForm();
	}

	@RequestMapping(value = "/search_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchById(@RequestBody Map<String, String> params) {
		String id = params.get("id");

		ExpressionList<Student> el = Ebean.find(Student.class).where().eq("deleted", false);
		Student student = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (student == null) {
			return ajaxForm.setError("错误");
		}
		return ajaxForm.setSuccess(student);
	}
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm delete(@RequestBody Map<String, String> params) {
		String id = params.get("id");
		
		ExpressionList<Student> el = Ebean.find(Student.class).where().eq("deleted", false);
		Student student = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (student == null) {
			return ajaxForm.setError("错误");
		}
		
		student.setDeleted(true);
		student.saveOrUpdate();
		User user = student.getUser();
		user.setDeleted(true);
		user.saveOrUpdate();
		return ajaxForm.setSuccess(student);
	}


}
