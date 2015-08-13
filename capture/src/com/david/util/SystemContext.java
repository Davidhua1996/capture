package com.david.util;

public class SystemContext {
	private static String COOKIE_NAME = "ASP.NET_SessionId";
	public static String getCookie_name() {
		return COOKIE_NAME;
	}
	public static void setCookie_name(String cookie_name) {
		COOKIE_NAME=cookie_name;
	}
}
