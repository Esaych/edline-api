package com.esaych.edline.api;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Class {
	
	public String course;
	public String link;
	public Grades grades;
	public String teacher;
	
	public Class(String courseName, String link, Response res) {
		this.course = courseName;
		course = toTitleCase(course.replaceAll("[0-9]", "").replaceAll(" - ", "").replaceAll("\\(.*?\\)","")).trim();
		
		this.link = link;
		
		try {
		res = Connect.meTo(Connect.EDLINE_URL + link + "/Current_Assignments_Report");

		Document doc = Jsoup.parse(res.body());
		if (doc.select("iframe#docViewBodyFrame").attr("src").equals(""))
			return;
		
		res = Connect.meTo(doc.select("iframe#docViewBodyFrame").attr("src"));

		grades = new Grades(course, res.body());
		
		} catch (IOException e) {
			System.out.println("Failed to load grades, not a class with grades");
			e.printStackTrace();
		}
	}
	
	public String toTitleCase(String givenString) {
	    String[] arr = givenString.split(" ");
	    String str = "";
	    for (String s : arr)
	    	if (s.length() > 0)
	    		if (s.length() < 3)
	    			str += s.toUpperCase() + " ";
	    		else
	    			str += s.toUpperCase().charAt(0) + s.toLowerCase().substring(1) + " ";
	    return str;
	}  
}
