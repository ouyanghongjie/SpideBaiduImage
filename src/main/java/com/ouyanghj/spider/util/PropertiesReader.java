package com.ouyanghj.spider.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesReader {

	private final Properties properties;

	public PropertiesReader(final String path) throws IOException {
		this.properties = new Properties();
		InputStream is = new BufferedInputStream(new FileInputStream(path));
		BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));// 解决读取properties文件中产生中文乱码的问题
		this.properties.load(bf);
	}

	public String getProperty(final String key) {
		return this.properties.getProperty(key);
	}

	public int getIntProperty(final String name, final int defaultValue) {
		String value = this.properties.getProperty(name);
		if (value == null) {
			return defaultValue;
		}
		return (new Integer(value)).intValue();
	}
}
