package com.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.model.BaseEntity;

import io.ebean.annotation.DbComment;
@Entity
@Table(name = "tbl_book")
@DbComment("书籍表")
public class Book extends BaseEntity{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DbComment("id")
	private Long id;

	@Lob
	private String img;
	private String code;
	
	private String name;
	private String author;
	
	private String chuBanCompany;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
	private Date chuBanDate;
	private Double buyMoney;
	private Double sellMoney;
	
	private String sellCompany;
	private String sellAddress;
	private String sellPhone;
	private Integer allQty;
	private Integer sellQty;
	
	@Lob
	private String brief;
	
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getChuBanCompany() {
		return chuBanCompany;
	}

	public void setChuBanCompany(String chuBanCompany) {
		this.chuBanCompany = chuBanCompany;
	}

	public Date getChuBanDate() {
		return chuBanDate;
	}

	public void setChuBanDate(Date chuBanDate) {
		this.chuBanDate = chuBanDate;
	}

	public Double getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(Double buyMoney) {
		this.buyMoney = buyMoney;
	}

	public Double getSellMoney() {
		return sellMoney;
	}

	public void setSellMoney(Double sellMoney) {
		this.sellMoney = sellMoney;
	}

	public String getSellCompany() {
		return sellCompany;
	}

	public void setSellCompany(String sellCompany) {
		this.sellCompany = sellCompany;
	}

	public String getSellAddress() {
		return sellAddress;
	}

	public void setSellAddress(String sellAddress) {
		this.sellAddress = sellAddress;
	}

	public String getSellPhone() {
		return sellPhone;
	}

	public void setSellPhone(String sellPhone) {
		this.sellPhone = sellPhone;
	}

	public Integer getAllQty() {
		return allQty;
	}

	public void setAllQty(Integer allQty) {
		this.allQty = allQty;
	}

	public Integer getSellQty() {
		return sellQty;
	}

	public void setSellQty(Integer sellQty) {
		this.sellQty = sellQty;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
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
