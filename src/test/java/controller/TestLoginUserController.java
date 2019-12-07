package controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.project.Application;
import com.util.TestControllerUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class TestLoginUserController {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		// 让每个测试用例启动之前都构建这样一个启动项
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/**
	 * 用户名为空测试
	 * @throws Exception
	 */
	@Test
	public void	loginByNullAccount() throws Exception {
		TestControllerUtil controllerUtil=new TestControllerUtil(mockMvc);
		String url="/user_login/login";
		JSONObject requestJson=new JSONObject();
		Map<String,String> expects=new HashMap<>();
		expects.put("data", "用户名为空");
		expects.put("success", "false");
		controllerUtil.checkByRequestBody(url, requestJson, expects);
		
	}
	/**
	 * 密码为空测试
	 * @throws Exception
	 */
	@Test
	public void	loginByNullPasswd() throws Exception {
		TestControllerUtil controllerUtil=new TestControllerUtil(mockMvc);
		String url="/user_login/login";
		JSONObject requestJson=new JSONObject();
		requestJson.put("userAccount", "admin");
		
		Map<String,String> expects=new HashMap<>();
		expects.put("data", "密码为空");
		expects.put("success", "false");
		controllerUtil.checkByRequestBody(url, requestJson, expects);
	}
	/**
	 * 密码错误测试
	 * @throws Exception
	 */
	@Test
	public void	loginByErrorPwd() throws Exception {
		TestControllerUtil controllerUtil=new TestControllerUtil(mockMvc);
		String url="/user_login/login";
		JSONObject requestJson=new JSONObject();
		requestJson.put("userAccount", "admin");
		requestJson.put("userPwd", "admin123");
		
		Map<String,String> expects=new HashMap<>();
		expects.put("data", "用户名密码错误");
		expects.put("success", "false");
		controllerUtil.checkByRequestBody(url, requestJson, expects);
	}
	/**
	 * 登录成功测试
	 * @throws Exception
	 */
	@Test
	public void	loginSuccess() throws Exception {
		TestControllerUtil controllerUtil=new TestControllerUtil(mockMvc);
		String url="/user_login/login";
		JSONObject requestJson=new JSONObject();
		requestJson.put("userAccount", "admin");
		requestJson.put("userPwd", "admin");
		
		Map<String,String> expects=new HashMap<>();
		expects.put("success", "true");
		controllerUtil.checkByRequestBody(url, requestJson, expects);
	}

}
