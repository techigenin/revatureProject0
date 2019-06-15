package com.mJames.project1.java.core;

import org.apache.log4j.Logger;

public class Logging {
	private static Logger log = Logger.getRootLogger();

	public static void infoLog(String msg) {
		log.info(msg);
	}
	
	public static void warnLog(String msg)
	{
		log.warn(msg);
	}
	
	public static void errorLog(String msg)
	{
		log.error(msg);
	}
	
}
