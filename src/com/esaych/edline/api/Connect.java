package com.esaych.edline.api;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class Connect {
	
	private static Map<String, String> loginCookies;
	public static final String EDLINE_URL = "https://www.edline.net";
	
	public static Response login(String username, String password) throws IOException {
		Response res = Jsoup.connect(EDLINE_URL + "/InterstitialLogin.page").timeout(0).userAgent("Chrome/12.0.742.122").execute();

		Map<String, String> preLogCookies = res.cookies();
		System.out.println("Pre Login Cookies: " + preLogCookies);

		res = Jsoup.connect(EDLINE_URL + "/post/InterstitialLogin.page")
				.data("TCNK", "authenticationEntryComponent", 
						"submitEvent", "1",
						"guestLoginEvent", "",
						"enterClicked", "false",
						"bscf", "",
						"bscv", "",
						"targetEntid", "",
						"ajaxSupported", "yes",
						"screenName", username,
						"kclq", password)
						.cookies(preLogCookies)
						.userAgent("Chrome/12.0.742.122")
						.method(Method.POST)
						.timeout(0)
						.execute();

		loginCookies = res.cookies();
		loginCookies.putAll(preLogCookies);
		System.out.println("Login Cookies: " + loginCookies);

		System.out.println("Connected to: " + res.url());
		if (res.url().toString().contains("Notification.page")) {
			System.out.println("Username and Password Incorrect");
			System.exit(1);
		}

		return res;
	}
	
	public static Response meTo(String url) throws IOException {
		return Jsoup.connect(url)
				.timeout(0)
				.cookies(loginCookies)
				.userAgent("Chrome/12.0.742.122")
				.execute();
	}
}
