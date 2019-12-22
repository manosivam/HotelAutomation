package com.myhotel.utils;

public class Logger {

	private static Logger logger;

	private Logger() {}

	public static Logger getLoggerInstance()
	{
		if(logger == null)
		{
			logger = new Logger();
		}
		return logger;
	}

	public void logLine(String message)
	{
		System.out.println(message);
	}

	public void log(String message)
	{
		System.out.print(message);
	}

}
