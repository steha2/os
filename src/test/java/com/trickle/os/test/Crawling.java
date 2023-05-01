package com.trickle.os.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Crawling {
	private ChromeDriver driver;

	private List<String> titles = new ArrayList<String>();
	
	private List<String> srcs = new ArrayList<String>();

	private static final String NAVER_SEARCH_URL = "https://search.naver.com/search.naver?sm=tab_sug.top&where=image&query=";

	public List<String> getTitles(){
		return titles;
	}
	
	public List<String> getSrcs(){
		return srcs;
	}
	
	public Crawling() {
		System.setProperty("webdriver.chrome.driver", "D:\\cd\\chromedriver.exe");
	}
	
	public ChromeDriver getDriver() {
		return driver;
	}
	
	public static void saveImg(String imageUrl, File saveFile) {
		try {
			URL url = new URL(imageUrl);
			InputStream inputStream = url.openStream();
			OutputStream outputStream;
			int i = 1;
			while (saveFile.exists()) {
				// 파일이 존재하면 이미지 크기 체크
				if (isImageSizeEqual(url, saveFile)) {
					System.out.println("Same image size found, skipping save");
					return;
				}
				// 파일 이름 변경
				String fileName = saveFile.getName();
				String fileExtension = fileName.substring(fileName.lastIndexOf("."));
				String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + i + fileExtension;
				saveFile = new File(saveFile.getParentFile(), newFileName);
				i++;
			}

			outputStream = new FileOutputStream(saveFile);

			byte[] buffer = new byte[2048];
			int length;

			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}

			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isImageSizeEqual(URL url, File file) throws IOException {
		BufferedImage image1 = ImageIO.read(url);
		BufferedImage image2 = ImageIO.read(file);
		return image1.getWidth() == image2.getWidth() && image1.getHeight() == image2.getHeight();
	}


	 public  void parse(String search) {
		ChromeOptions options = new ChromeOptions();
	    options.addArguments("--remote-allow-origins=*");
	    driver = new ChromeDriver(options);

		driver.get(NAVER_SEARCH_URL + search); // 브라우저에서 url로 이동한다.
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

		List<WebElement> elements = driver.findElements(By.className("thumb"));
		for (WebElement element : elements) {
			if (element.getRect().width < 100)
				continue;

			WebElement a = element.findElement(By.tagName("a"));
			String title = a.getAttribute("title");
			WebElement img = element.findElement(By.tagName("img"));
			srcs.add(img.getAttribute("src"));
			titles.add(title);
		}
	}

	public static String removeSpChar(String str) {
		str = str.replaceAll("[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]", ""); // 특수문자 제거
		str = str.replaceAll("\\s+", " "); // 연속된 공백 제거
		return str.trim(); // 문자열 앞뒤 공백 제거
	}
}
