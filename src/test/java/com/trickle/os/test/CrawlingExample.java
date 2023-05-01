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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CrawlingExample {
	private WebDriver driver;

	List<String> titles = new ArrayList<String>();
	List<String> srcs = new ArrayList<String>();

	
	static String URL = "https://search.naver.com/search.naver?sm=tab_sug.top&where=image&query=";

	static 	int LENGTH = 1; //반복수 [검색어 + i] 로 검색
	static 	String DIRNAME = "화장품";
	static  String[] SEARCHS = { "화장품" } ; //검색어
	
	

	public static void saveImg(String url, File saveFile) {
		try {
			// 1. 수집 대상 URL
			URL url2 = new URL(url);
			InputStream inputStream = url2.openStream();

			OutputStream outputStream;
			int i = 1;

			while (saveFile.exists()) {
				// 파일이 존재하면 이미지 크기 체크
				if (isImageSizeEqual(url2, saveFile)) {
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

	public static String removeSpChar(String str) {
		str = str.replaceAll("[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]", ""); // 특수문자 제거
		str = str.replaceAll("\\s+", " "); // 연속된 공백 제거
		return str.trim(); // 문자열 앞뒤 공백 제거
	}

	private static final String url = "https://naver.com";

	public void process(String search, String dir) {
		if(dir == null || dir.isBlank()) dir = search;
		System.setProperty("webdriver.chrome.driver", "D:\\cd\\chromedriver.exe");
		// 크롬 드라이버 셋팅 (드라이버 설치한 경로 입력)

	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("--remote-allow-origins=*");

//	    options.addArguments("--disable-popup-blocking");       //팝업안띄움
//	    options.addArguments("--headless");                       //브라우저 안띄움
//	    options.addArguments("--disable-gpu");			//gpu 비활성화
//	    options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
	    driver = new ChromeDriver(options);

	    
		try {
			System.out.println(getDataList(search));
			System.out.println("+++++++++++++++result+++++++++++++++");
			System.out.println(titles);
			System.out.println(srcs);
			File save = new File("D:/resources/images");

			for(String f : dir.split("/")) {
				File f1 = new File(save,f);
				if(!f1.exists()) f1.mkdir();
			}
			
			File searchf = new File(save, dir);

			for (int i = 0; i < titles.size(); i++) {
				String t = removeSpChar(titles.get(i));
				System.out.println(t);
				String s = srcs.get(i);
				System.out.println(s);
				if (!s.startsWith("http"))
					continue;

				String s1 = s.split("\\?")[1];

				String s2 = s1.split("&")[0];
				String s3 = s2.substring(s2.lastIndexOf("."));
				if (s3.length() == 4 || s3.length() == 5)
					saveImg(s, new File(searchf, t + s3));

			}

			System.out.println("검색 이미지 개수 : " + titles.size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.close(); // 탭 닫기
		driver.quit(); // 브라우저 닫기
	}

	/**
	 * data가져오기
	 */
	private List<String> getDataList(String search) throws InterruptedException {
		List<String> list = new ArrayList<>();

		driver.get(URL + search); // 브라우저에서 url로 이동한다.
		Thread.sleep(2000); // 브라우저 로딩될때까지 잠시 기다린다.

		By.tagName("div");
		List<WebElement> elements = driver.findElements(By.className("thumb"));

		for (WebElement element : elements) {
//			System.out.println("src: "  + element.findElement(By.tagName("a")).getAttribute("src"));
//			System.out.println(element.getAttribute("style"));
//			log.info(element.getRect().getDimension());
//		    System.out.println(element);
			if (element.getRect().width < 100)
				continue;

			WebElement a = element.findElement(By.tagName("a"));
			String title = a.getAttribute("title");
			WebElement img = element.findElement(By.tagName("img"));
			srcs.add(img.getAttribute("src"));
			titles.add(title);

			list.add(element.getRect().x + "  " + element.getRect().y);
		}
		System.out.println("SIZE:" + elements.size());
		return list;
	}

	static String[] ES = { "!", "@", "#", "$", "%", "*", "/", ".", "`", "_", "=", "}", "+" };

	public static void main(String[] args) {

		for(String sc : SEARCHS) {
			for(int i=0; i<LENGTH; i++) {
				new CrawlingExample().process(sc+(i==0?"":String.valueOf(i)), DIRNAME);
			}
		}
	}
}













