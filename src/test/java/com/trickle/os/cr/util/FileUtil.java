package com.trickle.os.cr.util;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.time.LocalDateTime;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.trickle.os.cr.gui.Gui;
import com.trickle.os.cr.gui.panel.CustomPanel;

public class FileUtil {
	public static void copy(String src, String newFile) {
		try {
			File srcFile = new File(src);
			File destFile = new File(newFile);

			InputStream inputStream = new FileInputStream(srcFile);

			OutputStream outputStream = new FileOutputStream(destFile);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}

			inputStream.close();
			outputStream.close();

			System.out.println("File copied successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readString(String path) {
		try {
			return Files.readString(new File(path).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// -----------------------------------create Simple
	// JFileChooser--------------------------------//
	public static JFileChooser createFileChooser(File file, String... exts) {
		JFileChooser fileChooser = new JFileChooser(file);
		if (exts != null && exts.length > 0) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Files", exts);
			fileChooser.setFileFilter(filter);
		}
		return fileChooser;
	}

	public static JFileChooser createFileChooser(String... exts) {
		return createFileChooser(new File(""), exts);
	}

	public static File[] getFiles(CustomPanel customPanel, File current, String... exts) {
		return getFiles(customPanel.getPanel(), current, exts);
	}

	public static File[] getFiles(Component parent, File current, String... exts) {
		JFileChooser fileChooser = createFileChooser(exts);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(true);
		if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFiles();
		} else {
			return new File[0];
		}
	}

	public static File getFile(CustomPanel customPanel, File current, String... exts) {
		return getFile(customPanel.getPanel(), current, exts);
	}

	public static File getFile(Component parent, File current, String... exts) {
		JFileChooser fileChooser = createFileChooser(current, exts);
		if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else {
			return null;
		}
	}

	public static File[] getFiles(String... exts) {
		JFileChooser fileChooser = createFileChooser(exts);
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFiles();
		} else {
			return null;
		}
	}

	public static File getFile(String... exts) {
		JFileChooser fileChooser = createFileChooser(exts);
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else {
			return null;
		}
	}

	public static Object getObjectFromJar(String path, String className) {
		try {
			URL jarUrl = new URL("file:/"+path);
			@SuppressWarnings("resource")
			URLClassLoader classLoader = new URLClassLoader(new URL[] { jarUrl });
			Class<?> clazz = classLoader.loadClass(className);
			return clazz.getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}