package com.project.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.model.BaseEntity;

import io.ebean.Ebean;
import io.ebean.annotation.DbComment;

@Entity
@Table(name = "tbl_notice")
@DbComment("公告表")
public class Notice extends BaseEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DbComment("id")
	private Long id;

	@DbComment("标题")
	public String title;

	@DbComment("详细内容")
	@Lob
	public String msg;
	

	
	@DbComment("插入时间")
	private Date insertedAt;

	@DbComment("插入人")
	private String inserter;

	@DbComment("修改时间")
	private Date updatedAt;

	@DbComment("修改人")
	private String updater;

	@DbComment("是否删除")
	private Boolean deleted;

	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}








	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getInsertedAt() {
		return insertedAt;
	}

	public void setInsertedAt(Date insertedAt) {
		this.insertedAt = insertedAt;
	}

	public String getInserter() {
		return inserter;
	}

	public void setInserter(String inserter) {
		this.inserter = inserter;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	
}
