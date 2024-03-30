package com.api.auto.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtils {
	
	private static String CONFIG_PATH = "./configuration/configs.properties";
	private static String TOKEN_PATH = "./configuration/token.properties";
	
	public static String getProperty(String key) {
		Properties properties = new Properties();
		String value = null;
		FileInputStream fileInputStream = null;
		
		try {
			fileInputStream = new FileInputStream(CONFIG_PATH);
			properties.load(fileInputStream);
			value = properties.getProperty(key);
			return value;
		}catch (Exception ex) {
			System.out.println("Xay ra loi khi doc gia tri cua " + key);
			ex.printStackTrace();
		}finally {
			if(fileInputStream != null) {
				try {
					fileInputStream.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	public static void saveToken(String token, String value) {
		Properties properties = new Properties();
		FileOutputStream fileOutputStream = null;
		
		try {
			fileOutputStream = new FileOutputStream(TOKEN_PATH);
			properties.setProperty(token, value);
			properties.store(fileOutputStream, "Set new value in properties");
			System.out.println("Set new value in file properties success.");
		}catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			if(fileOutputStream != null) {
				try {
					fileOutputStream.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getToken(String token) {
		Properties properties = new Properties();
		String value = null;
		FileInputStream fileInputStream = null;
		
		try {
			fileInputStream = new FileInputStream(TOKEN_PATH);
			properties.load(fileInputStream);
			value = properties.getProperty(token);
			return value;
		}catch (Exception ex) {
			System.out.println("Xay ra loi khi doc gia tri token ");
			ex.printStackTrace();
		}finally {
			if(fileInputStream != null) {
				try {
					fileInputStream.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
}
