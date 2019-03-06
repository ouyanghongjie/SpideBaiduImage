package com.ouyanghj.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ouyanghj.spider.url.URLCreator;
import com.ouyanghj.spider.util.PropertiesReader;

/**
 * Created by ouyang hongjie on 19-01-03
 *
 */
public class SpiderFromBaidu {

	public static void main(final String[] args) throws IOException {
		// 读取配置数据
		PropertiesReader propReader = new PropertiesReader("src/main/resources/config.properties");

		// 首先得到页面URL
		List<String> urlMains = URLCreator.createPageUrl(propReader.getProperty("baiduURL"),
		                propReader.getIntProperty("numOfPages", 1), propReader.getProperty("keyword"));

		// 使用Jsoup和FastJson解析出所有的图片源链接
		List<String> imageUrls = URLCreator.createImageUrl(urlMains);

		// 对图片源链接进行下载（使用多线程进行下载）创建进程
		spideFromBaidu(imageUrls, propReader.getProperty("downloadDir"), propReader.getIntProperty("numOfThreads", 10));

	}

	private static void spideFromBaidu(final List<String> imageUrls, final String downloadDir, final int numOfThreads) {
		int average = imageUrls.size() / numOfThreads;
		int sum = 0;

		for (int i = 0; i < numOfThreads; i++) {
			int begin = sum;
			sum += average;
			int last = sum;

			Thread image = null;
			if (i < 9) {
				image = new Thread(new ImageThread(begin, last, (ArrayList<String>) imageUrls, downloadDir));
			} else {
				image = new Thread(
				                new ImageThread(begin, imageUrls.size(), (ArrayList<String>) imageUrls, downloadDir));
			}

			image.start();
		}
	}
}
