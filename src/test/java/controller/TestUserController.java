package controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.project.Application;
import com.util.TestControllerUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
public class TestUserController {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		// 让每个测试用例启动之前都构建这样一个启动项
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

//	@Test
//	public void save() throws Exception {
//		TestControllerUtil controllerUtil = new TestControllerUtil(mockMvc);
//		String url = "/user/save_or_update";
//		for (int i = 0; i < 10; i++) {
//			JSONObject requestJson = new JSONObject();
//			requestJson.put("account", "test00" + i);
//			requestJson.put("name", "姓名00");
//			Map<String, String> expects = new HashMap<>();
//			expects.put("success", "true");
//			controllerUtil.checkByRequestBody(url, requestJson, expects);
//		}
//	}
//
//	@Test
//	public void update() throws Exception {
//		TestControllerUtil controllerUtil = new TestControllerUtil(mockMvc);
//		String url = "/user/save_or_update";
//		JSONObject requestJson = new JSONObject();
//		requestJson.put("id", "49");
//		requestJson.put("account", "fffff");
//		requestJson.put("name", "赵丰登");
//		Map<String, String> expects = new HashMap<>();
//		expects.put("success", "true");
//		controllerUtil.checkByRequestBody(url, requestJson, expects);
//	}
	@Test
	public void search() throws Exception {
		TestControllerUtil controllerUtil = new TestControllerUtil(mockMvc);
		String url = "/user/search";
		JSONObject requestJson = new JSONObject();
		Map<String, String> expects = new HashMap<>();
		expects.put("success", "true");
		controllerUtil.checkByRequestBody(url, requestJson, expects);
		String result = controllerUtil.getResult();
		JSONArray array = new JSONArray(new JSONObject(result).get("data"));
	}
}
