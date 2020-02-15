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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.model.BaseEntity;

import io.ebean.Ebean;
import io.ebean.annotation.DbComment;

@Entity
@Table(name = "tbl_room_score_log")
@DbComment("寝室分数表")
public class RoomScoreLog extends BaseEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DbComment("id")
	private Long id;
	
	@ManyToOne
	private Room room;

	@DbComment("分数")
	private Double score;

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



	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
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
