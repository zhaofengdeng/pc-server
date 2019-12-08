package com.framework.controller;

import java.util.HashMap;
import java.util.Map;

import com.model.Paginate;
import com.project.config.PaginateConfig;
import com.util.EbeanPaginateUtil;
import com.util.base.MapUtil;

import io.ebean.ExpressionList;
import io.ebean.Query;

public class BaseController {
	/**
	 * 分页
	 * 
	 * @param el
	 * @param params
	 * @return
	 */
	public Paginate paginate(ExpressionList el, Map<String, Object> params) {
		Object object = params.get("paginate");
		Map<String, Object> map = new HashMap<String, Object>();
		if (object != null) {
			map = (Map<String, Object>) object;
		}
		String curPage = MapUtil.getString(map, PaginateConfig.KEY_CUR_PAGE);
		String maxPerPage = MapUtil.getString(map, PaginateConfig.KEY_MAX_PER_PAGE);
		return EbeanPaginateUtil.paginate(el, curPage, maxPerPage);
	}
	public Paginate paginate(Query query, Map<String, Object> params) {
		Object object = params.get("paginate");
		Map<String, Object> map = new HashMap<String, Object>();
		if (object != null) {
			map = (Map<String, Object>) object;
		}
		String curPage = MapUtil.getString(map, PaginateConfig.KEY_CUR_PAGE);
		String maxPerPage = MapUtil.getString(map, PaginateConfig.KEY_MAX_PER_PAGE);
		return EbeanPaginateUtil.paginate(query, curPage, maxPerPage);
	}
}
