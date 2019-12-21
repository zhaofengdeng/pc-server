package com.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.ebean.Ebean;
import io.ebean.annotation.DbComment;

@Entity
@Table(name = "tbl_log_user_login")
@DbComment("用户登录日志")
public class LogUserLogin {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DbComment("id")
	private Long id;
	
	@ManyToOne
	private User user;
	
	@Column(name = "inserted_at")
	@DbComment("登录时间")
	private Date insertedAt;

	public void save(User user) {
		this.setUser(user);
		this.setInsertedAt(new Date());
		Ebean.save(this);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getInsertedAt() {
		return insertedAt;
	}

	public void setInsertedAt(Date insertedAt) {
		this.insertedAt = insertedAt;
	}
	
	
	
}
