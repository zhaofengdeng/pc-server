package com.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Role;
import com.project.model.TreeNode;
import com.util.form.AjaxForm;

import io.ebean.Ebean;
import io.ebean.ExpressionList;

@RestController
@RequestMapping(value = "/tree_node")
public class TreeNodeController {
	@RequestMapping(value = "/search_all", method = { RequestMethod.GET, RequestMethod.POST })
	public AjaxForm searchAll() {
		AjaxForm ajaxForm=new AjaxForm();
		ExpressionList<TreeNode> el = Ebean.find(TreeNode.class).where().eq("deleted", false);
		List<TreeNode> list = el.orderBy("sort asc").findList();
		return ajaxForm.setSuccess(list);
	}

}
