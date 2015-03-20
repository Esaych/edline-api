package com.esaych.edline.api;

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
		Scanner input = new Scanner(System.in);
		System.out.println("Edline Login:\nUsername: ");
		String username = "avenoo";//input.nextLine();
		System.out.println("Password: ");
		String password = "rob123dude";//input.nextLine();
		input.close();
		Response res = Connect.login(username, password);
		parseClasses(res);
		
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
