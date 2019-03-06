package com.ouyanghj.spider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ImageThread implements Runnable {

	InputStream inputStream = null;
	FileOutputStream outputStream = null;
	int begin = 0;
	int last = 0;
	String dir;
	List<String> imageUrls = new ArrayList<>();

	// 设置线程需要的参数
	public ImageThread(final int begin, final int last, final ArrayList<String> imageUrls, final String downloadDir) {
		this.begin = begin;
		this.last = last;
		this.imageUrls = imageUrls;
		this.dir = downloadDir;
	}

	@Override
	public void run() {
		for (int i = this.begin; i < this.last; i++) {
			System.out.println("图片URL: " + this.imageUrls.get(i));

			try {
				URL url = new URL(this.imageUrls.get(i));
				URLConnection conn = url.openConnection();
				conn.setConnectTimeout(1000);
				conn.setReadTimeout(5000);
				conn.connect();
				this.inputStream = conn.getInputStream();
			} catch (Exception e) {
				continue;
			}

			System.out.println("获取" + this.imageUrls.get(i) + " success!");

			// 创建文件，以url名为文件名
			String filename = this.dir + '/'
			                + this.imageUrls.get(i).substring(this.imageUrls.get(i).lastIndexOf('/') + 1);
			File file1 = new File(filename);
			try {
				if (!file1.exists()) {
					file1.createNewFile();

					this.outputStream = new FileOutputStream(new File(filename));
					byte[] buf = new byte[102400];
					int length = 0;
					while ((length = this.inputStream.read(buf, 0, buf.length)) != -1) {
						this.outputStream.write(buf, 0, length);
					}
				}
			} catch (FileNotFoundException e) {
				continue;
			} catch (IOException e) {
				continue;
			}

			try {
				this.inputStream.close();
				this.outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
