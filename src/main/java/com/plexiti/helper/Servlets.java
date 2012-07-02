package com.plexiti.helper;

import java.net.MalformedURLException;
import java.net.URL;

public class Servlets {
	
	public static String parseFilename(String urlString, boolean withExtension) {
		String path;
		try {
			path = new URL(urlString).getPath();
			String fileName = path.substring(path.lastIndexOf('/') + 1);
			return withExtension || !fileName.contains(".") ? fileName : fileName.substring(0, fileName.lastIndexOf('.'));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
