package com.esaych.edline.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Edline {
	public static String school;
	public static String name;
	private static ArrayList<Class> classList = new ArrayList<Class>();

	public static void main(String[] args) throws Exception {
		System.out.println("Logging In...");
		File file = new File("credentials.txt");
		if (!file.exists()) {
			writeLoginFile();
			System.exit(0);
		}
		@SuppressWarnings("resource")
		Scanner creds = new Scanner(file);
		String user = "";
		String pass = "";
		if (creds.hasNextLine()) {
			user = creds.nextLine();
			pass = creds.nextLine();
		} else
			writeLoginFile();
		user = user.substring(9, user.length());
		pass = pass.substring(9, pass.length());
		Response res = Connect.login(user, pass);
		parseClasses(res);
		
	}
	
	private static void writeLoginFile() {
		FileWriter fw;
		try {
			fw = new FileWriter("credentials.txt");
			fw.write("Username:myusername\nPassword:mypassword");
			fw.close();
			System.out.println("Please fill in your information in the credentals file\n(do not add spaces after the ':')");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses main page of Edline for school, name, and classes
	 * @param src
	 */
	
	public static void parseClasses(Response res) {
		String src = res.body();
		school = src.substring(src.indexOf("<title>") + 7, src.indexOf("</title>") - 11); 
		System.out.println("Attending School: " + school);
		
		int magicNum = src.indexOf("class=\"ed-studentName notranslate\" tabindex=\"-1\">") + 49;
		name = src.substring(magicNum, src.indexOf("</a>", magicNum));
		System.out.println("Name: " + name);
		
		Document doc = Jsoup.parse(src.substring(src.indexOf("<div type=\"menu\" title=\"My Classes"), src.indexOf("<div type=\"menu\" title=\"My Content\"")));

		int i = 0;
		do {
			classList.add(new Class(doc.select("div#myClasses"+i).attr("title"), doc.select("div#myClasses"+i).attr("action"), res));
			i++;
		} while (!doc.select("div#myClasses"+i).attr("title").equals(""));
		
		System.out.println("Class List: " + classList);
	}
	

	

}
