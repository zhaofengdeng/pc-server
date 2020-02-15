package com.project.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.model.BaseEntity;
import com.util.base.StringUtil;

import io.ebean.annotation.DbComment;

@Entity
@Table(name = "tbl_vacation")
@DbComment("请假表")
public class Vacation extends BaseEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DbComment("id")
	private Long id;

	@DbComment("请假时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
	public Date beginDate;

	@DbComment("销假时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
	public Date endDate;

	@DbComment("请假原因")
	public String content;

	@ManyToOne
	public Student student;

	@DbComment("状态")
	public Integer status;

	@Column(name = "inserted_at")
	@DbComment("插入时间")
	private Date insertedAt;

	@Column(name = "inserter")
	@DbComment("插入人")
	private String inserter;

	@Column(name = "updated_at")
	@DbComment("修改时间")
	private Date updatedAt;

	@Column(name = "updater")
	@DbComment("修改人")
	private String updater;

	@Column(name = "deleted")
	@DbComment("deleted")
	private Boolean deleted;
	public String getStatusText() {
		if(this.getStatus()==1) {
			return "未销假";
		}
		if(this.getStatus()==2) {
			return "已销假";
		}
		return "";
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
