package com.util;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.util.base.MapUtil;

public class TestControllerUtil {
	private MockMvc mockMvc;
	private String result;

	public TestControllerUtil(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	public void checkByRequestBody(String url, JSONObject requestJson, Map<String, String> expects) throws Exception {
		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)
				.content(requestJson.toString()));
		List<String> keys = MapUtil.keys(expects);
		for (String key : keys) {
			perform.andExpect(MockMvcResultMatchers.jsonPath(key).value(expects.get(key)));
		}
		// 期望的返回值 或者返回状态码
		result = perform.andReturn().getResponse().getContentAsString();
	}

}
