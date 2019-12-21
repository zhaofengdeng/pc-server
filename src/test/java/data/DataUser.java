package data;

import java.util.ArrayList;

import java.util.List;

import com.project.model.Permission;
import com.project.model.Role;
import com.project.model.TreeNode;
import com.project.model.User;
import com.util.base.StringUtil;

import io.ebean.Ebean;

public class DataUser {
	public static String[][] permissions = new String[][] { { "权限管理", "权限更新", "/permission/save_or_update" },
			{ "权限管理", "权限-查询", "/permission/search" }, { "权限管理", "权限查询-全部", "/permission/search_all" },
			{ "权限管理", "权限查询-通过ID", "/permission/search_by_id" }, { "角色管理", "角色更新", "/role/save_or_update" },
			{ "角色管理", "角色-查询", "/role/search" }, { "角色管理", "角色查询-全部", "/role/search_all" },
			{ "角色管理", "角色查询-通过ID", "/role/search_by_id" }, { "角色管理", "树形菜单数据", "/tree_node/search_all" },
			{ "用户管理", "用户更新", "/user/save_or_update" }, { "用户管理", "用户查询", "/user/search" },
			{ "角色管理", "用户查询-通过ID", "/user/search_by_id" }, };

	public static void main(String[] args) {

		Double i = 0D;
		for (String[] permissionStr : permissions) {
			Permission permission = new Permission();
			permission.setName(permissionStr[1]);
			permission.setSort(i++);
			permission.setUrl(permissionStr[2]);
			permission.setNode(Ebean.find(TreeNode.class).where().eq("name", permissionStr[0]).findOne());
			permission.save();
		}

		Role role = new Role();
		role.setEngName("admin");
		role.setName("管理员");
		role.setPermissions(Ebean.find(Permission.class).findList());

		role.save();
		User user = new User();
		user.setAccount("admin");
		user.setEnable(true);
		user.setRoles(Ebean.find(Role.class).findList());
		user.setPasswd(StringUtil.Md5BASE64("admin"));
		user.save();
	}
}
