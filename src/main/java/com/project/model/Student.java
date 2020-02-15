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

import com.model.BaseEntity;
import com.util.base.StringUtil;

import io.ebean.annotation.DbComment;

@Entity
@Table(name = "tbl_tudent")
@DbComment("学生表")
public class Student extends BaseEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DbComment("id")
	private Long id;

	@DbComment("姓名")
	public String name;
	@DbComment("电话")
	public String phone;

	@DbComment("性别")
	public String sex;
	@ManyToOne 
	public User user;
	
	@DbComment("地址")
	public String address;

	@DbComment("班级")
	public String clazz;
	
	@ManyToOne
	private Room room;

	@DbComment("学号")
	public String no;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
