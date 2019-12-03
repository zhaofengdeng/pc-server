import com.project.model.User;

import io.ebean.Ebean;

public class Test {
	public static void main(String[] args) {
		Ebean.find(User.class).findList();
	}
}
