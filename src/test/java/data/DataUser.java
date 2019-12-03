package data;

import com.project.model.User;
import com.util.base.StringUtil;

public class DataUser {
	public static void run() {
		User user=new User();
		user.setAccount("admin");
		user.setPasswd(StringUtil.Md5BASE64("admin"));
		user.save();
	}
}
