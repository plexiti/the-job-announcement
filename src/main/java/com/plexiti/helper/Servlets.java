package com.plexiti.helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

public final class Servlets {
	
	protected static Logger log = Logger.getLogger(Servlets.class.getName());
	
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
	
    public static String browser(HttpServletRequest request) {
        String userAgent= request.getHeader("user-agent");
        log.info("Logging request from User agent [" + userAgent + "]");
        String[] browsers = {"chrome", "safari", "firefox", "opera", "msie"};
        userAgent = userAgent.toLowerCase();
        for (String shortCut: browsers) {
            if (userAgent.indexOf(shortCut) != -1 ) {
            	shortCut = shortCut.equals("msie") ? "ie" : shortCut;
            	log.info("Detected User agent type: [" + shortCut + "]");
            	return shortCut;
            }
        }
    	log.warning("Could not detect Browser Vendor.");
        return "";
    }
	
}
