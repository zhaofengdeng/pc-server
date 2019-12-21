package data;

import com.project.model.TreeNode;
import com.util.base.StringUtil;

import io.ebean.Ebean;

public class DataTreeNode {
	public static String[][] nodes = new String[][] { 
		{ "权限URL" }, 
		{ "系统管理", "权限URL" }, 
		{ "用户管理", "系统管理" },
		{ "角色管理", "系统管理" },
		{ "权限管理", "系统管理" } };

	public static void main(String[] args) {
		for (String[] nodeStr : nodes) {
			TreeNode model = new TreeNode();
			model.setName(nodeStr[0]);
			if (nodeStr.length>1) {
				model.setParentNode(Ebean.find(TreeNode.class).where().eq("name", nodeStr[1]).findOne());
			}else {
				model.setIsSuperParentNode(true);
			}
			model.save();
		}

	}
}
