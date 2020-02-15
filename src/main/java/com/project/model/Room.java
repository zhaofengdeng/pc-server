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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.model.BaseEntity;

import io.ebean.Ebean;
import io.ebean.annotation.DbComment;

@Entity
@Table(name = "tbl_room")
@DbComment("角色表")
public class Room extends BaseEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DbComment("id")
	private Long id;

	@DbComment("寝室号")
	public String no;

	@DbComment("总人数")
	public Integer sumQty;
	

	@DbComment("分数")
	public Double score;
	
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

	public Integer getCurQty() {
		int qty = Ebean.find(Student.class).where().eq("room.id", this.getId()).eq("deleted", false).findCount();
		return qty;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getSumQty() {
		return sumQty;
	}

	public void setSumQty(Integer sumQty) {
		this.sumQty = sumQty;
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


	public Double getScore() {
		return score;
	}


	public void setScore(Double score) {
		this.score = score;
	}

	
}
