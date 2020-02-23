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

import com.model.BaseEntity;

import io.ebean.Ebean;
import io.ebean.annotation.DbComment;

@Entity
@Table(name = "tbl_order")
@DbComment("订单表")
public class Order extends BaseEntity{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DbComment("id")
	private Long id;
	
	

	private String no;
	private String flag;
	
	@DbComment("状态：0.购物车1:未发货 2已.发货3确认.收货")
	private Integer status;

	@DbComment("状态：0.未付款 1.已付款")
	private Integer payStatus;

	@DbComment("支付金额")
	private Double payMoney;
	

	@ManyToOne
	private User user;
	
	
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
		if(status==null) {
			return "";
		}
		if(status==0) {
			return "未付款";
		}
		if(status==1) {
			return "未发货";
		}
		if(status==2) {
			return "已发货";
		}
		if(status==3) {
			return "确认收货";
		}
		return "";
	}
	public void updatePayMoney() {
		List<OrderDetail> list = Ebean.find(OrderDetail.class).where().eq("order.id", this.getId()).eq("deleted", false).findList();
		Double payMoneyAll=new Double(0);
		for (OrderDetail orderDetail : list) {
			payMoneyAll=orderDetail.getMoney()+payMoneyAll;
		}
		this.setPayMoney(payMoneyAll);
		this.update();
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}
