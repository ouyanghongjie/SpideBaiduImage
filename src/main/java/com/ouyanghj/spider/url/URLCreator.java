package com.ouyanghj.spider.url;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class URLCreator {

	// 构建需要抓取的百度图片页面
	public static List<String> createPageUrl(final String urlMain, final int numOfPages, final String keyword)
	                throws UnsupportedEncodingException {
		List<String> list = new ArrayList<>();
		// 构建需要爬取的页面Url
		for (int i = 0; i < numOfPages; i++) {
			String urlPage = "http://" + urlMain + keyword + "&pn=" + (i * 60);
			list.add(urlPage);
		}

		return list;
	}

	// 创建每个要爬取图片的Url
	public static List<String> createImageUrl(final List<String> list) {
		List<String> imagelist = new ArrayList<>();
		imagelist = UrlFetcher.urlParse(imagelist, list);

		return imagelist;
	}
}
