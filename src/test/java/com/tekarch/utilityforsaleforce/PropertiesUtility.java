package com.tekarch.utilityforsaleforce;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtility {
	private FileInputStream stream = null;
	private Properties propertyFile = null;

	public Properties loadFile(String fileName) {
		System.out.println("loading started");
		String propertyFilePath = null;
		switch (fileName) {
		case "loginDataProperties":
			System.out.println("login file found");
			propertyFilePath = Constants.LOGIN_PROPERTIES;
			break;
		default:
			System.out.println("No file found");
		}
		try {
			System.out.println("Loading the login propertyFile");
			stream = new FileInputStream(propertyFilePath);
			propertyFile = new Properties();
			propertyFile.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	//	System.out.println("Returning propertyfile");
		return propertyFile;
	}

	public  String getPropertyData(String key) {
	//	System.out.println("Entered getPropertyData");
	//	System.out.println(propertyFile);
	/*
	 * if (propertyFile.isEmpty()) { System.out.println("property file empty"); }
	 * else { System.out.println("Property file is  not empty"); }
	 */
		String value = propertyFile.getProperty(key);
		System.out.println("Property we got from this file is:" + value);
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

}
