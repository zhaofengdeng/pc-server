package com.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.model.BaseEntity;

import io.ebean.annotation.DbComment;

@Entity
@Table(name = "tbl_tree_node")
@DbComment("树节点")
public class TreeNode  extends BaseEntity{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DbComment("id")
	private Long id;
	
	
	@DbComment("名称")
	private String name;
	
	@DbComment("是否是超级父节点")
	private Boolean isSuperParentNode;
	
	@ManyToOne
	private TreeNode parentNode;
	
	@DbComment("排序")
	private Double sort;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsSuperParentNode() {
		return isSuperParentNode;
	}

	public void setIsSuperParentNode(Boolean isSuperParentNode) {
		this.isSuperParentNode = isSuperParentNode;
	}

	public TreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(TreeNode parentNode) {
		this.parentNode = parentNode;
	}

	public Double getSort() {
		return sort;
	}

	public void setSort(Double sort) {
		this.sort = sort;
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
