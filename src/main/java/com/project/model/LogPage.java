package com.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.ebean.Ebean;
import io.ebean.annotation.DbComment;

@Entity
@Table(name = "tbl_log_page")
@DbComment("页面日志表")
public class LogPage {

	@DbComment("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@Id
	private Long id;

	@DbComment("用户ID")
	@ManyToOne
	private User user;

	@DbComment("名称")
	private String name;
	
	@DbComment("当前页面路径")
	private String path;
	
	@DbComment("当前参数")
	@Lob
	private String query;
	
	@DbComment("上一个页面路径")
	private String lastPath;

	@DbComment("活跃时间")
	private Long activeTime;

	@DbComment("开始时间")
	private Date beginAt;

	@DbComment("结束时间")
	private Date endAt;

	@DbComment("状态:0:未结束 1.已结束  -1异常：未开始记录 -2重复结束")
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Long activeTime) {
		this.activeTime = activeTime;
	}

	public Date getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(Date beginAt) {
		this.beginAt = beginAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLastPath() {
		return lastPath;
	}

	public void setLastPath(String lastPath) {
		this.lastPath = lastPath;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
