package com.trickle.os.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class Test3 {
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

	
}
