package com.trickle.os.controller.rest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.*;

@RestController
@RequiredArgsConstructor
public class ProcessController {

	public BufferedImage convertFileToImage(MultipartFile imageFile) {
		try {
			byte[] imageBytes = imageFile.getBytes();
			ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
			BufferedImage bufferedImage = ImageIO.read(bis);
			bis.close();
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/process/ocr")
	public String ocrImage(MultipartFile imageFile) throws IOException, TesseractException {
		BufferedImage in = convertFileToImage(imageFile);
		ITesseract instance = new Tesseract();
		return instance.doOCR(in);
	}
	
	
	@PostMapping("/process/ocrb")
	public String ocrImage(byte[] imageData) throws IOException, TesseractException {
		System.out.println("ImageData : "+imageData.length);
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
		ITesseract instance = new Tesseract();
		return instance.doOCR(image);
	}
}
