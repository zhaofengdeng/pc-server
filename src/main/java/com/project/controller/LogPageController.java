package com.project.controller;

import java.util.Date;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.key.SessionKeys;
import com.project.model.LogPage;
import com.project.util.SessionUtil;
import com.util.form.AjaxForm;

import io.ebean.Ebean;

@RestController
@RequestMapping(value = "/log/page", method = RequestMethod.POST)
public class LogPageController {
	@RequestMapping(value = "/begin", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm begin(@RequestBody Map<String, String> map) {
		AjaxForm ajaxForm = new AjaxForm();
		
		
		LogPage logPage=new LogPage();
		initLog(logPage, map);
		logPage.setStatus(0);
		Ebean.save(logPage);
		
		return ajaxForm.setSuccess(logPage);
	}
	private void initLog(LogPage logPage,Map<String, String> map) {
		logPage.setName(map.get("name"));
		logPage.setQuery(map.get("query"));
		logPage.setPath(map.get("path"));
		logPage.setBeginAt(new Date());
		logPage.setUser(SessionUtil.getUser());
		logPage.setLastPath(map.get("lastPath"));
	}
	@RequestMapping(value = "/end", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm end(@RequestBody Map<String, String> map) {
		AjaxForm ajaxForm = new AjaxForm();
		String id = map.get("id");
		String name = map.get("name");
		String relevantId = map.get("relevantId");
		LogPage log = Ebean.find(LogPage.class).where().eq("id", id).findOne();
		Date curDate = new Date();
		//没有找到开始记录
		if(log==null) {
			LogPage logPage=new LogPage();
			initLog(logPage, map);
			initLog(logPage, map);
			logPage.setStatus(-1);
			Ebean.save(logPage);
			return ajaxForm.setSuccess(null);
		}
		//已经结束，重复结束
		if(log.getEndAt()!=null) {
			LogPage logPage=new LogPage();
			logPage.setName(name);
			logPage.setBeginAt(curDate);
			logPage.setStatus(-2);
			Ebean.save(logPage);
			return ajaxForm.setSuccess(null);
		}
		//正常结束
		log.setEndAt(curDate);
		log.setStatus(1);
		long activeTime=curDate.getTime()-log.getBeginAt().getTime();
		log.setActiveTime(activeTime);
		Ebean.update(log);
		return ajaxForm.setSuccess(null);
	}
}
