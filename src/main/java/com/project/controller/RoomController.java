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
import com.project.model.Room;
import com.project.model.RoomScoreLog;
import com.project.model.Student;
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
@RequestMapping(value = "/room")
public class RoomController extends BaseController {
	@RequestMapping(value = "/save_or_update", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm saveOrUpdate(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		Room room = ModelUtil.toModel(params, Room.class);
		int qty = Ebean.find(Room.class).where().eq("no", room.getNo()).eq("deleted", false).ne("id", room.getId()).findCount();
		if(qty>0){
			return ajaxForm.setError("寝室号重复");
		}
		room.saveOrUpdate();

		return ajaxForm.setSuccess(room);
	}
	@RequestMapping(value = "/score", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm score(@RequestBody Map<String, Object> params) {
		AjaxForm ajaxForm = new AjaxForm();
		Room room = ModelUtil.toModel(params, Room.class);
		room.saveOrUpdate();
		RoomScoreLog log=new RoomScoreLog();
		log.setScore(room.getScore());
		log.setRoom(room);
		log.save();
		return ajaxForm.setSuccess(room);
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm search(@RequestBody Map<String, Object> params) {
		ExpressionList<Room> el = Ebean.find(Room.class).where().eq("deleted", false);
		String name = MapUtil.getString(params, "name");
		String no = MapUtil.getString(params, "no");
		EbeanELUtil.like(el, "no", no);
		EbeanELUtil.like(el, "name", name);
		Query<Room> query = el.orderBy("updatedAt desc");
		Paginate paginate = super.paginate(el, params);
		return paginate.toAjaxForm();
	}


	@RequestMapping(value = "/search_by_id", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchById(@RequestBody Map<String, String> params) {
		String id = params.get("id");

		ExpressionList<Room> el = Ebean.find(Room.class).where().eq("deleted", false);
		Room room = el.eq("id", id).findOne();
		AjaxForm ajaxForm = new AjaxForm();
		if (room == null) {
			return ajaxForm.setError("错误");
		}
		return ajaxForm.setSuccess(room);
	}


}
