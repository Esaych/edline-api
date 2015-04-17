package com.esaych.edline.api;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Course {
	
	private String course;
	private String link;
	private Grades grades;
	private String teacher;
	private Response res;
	
	/**
	 * Course stores information regarding a classes found on the edline page
	 * @param courseName name of the course
	 * @param link to class homepage
	 * @param res the response of the edline homepage
	 */
	
	public Course(String courseName, String link, Response res) {
		this.course = courseName;
		course = toTitleCase(course.replaceAll("[0-9]", "").replaceAll(" - ", "").replaceAll("\\(.*?\\)","")).trim();
		this.res = res;
		this.link = link;
	}

	/**
	 * Parse the Current Assignments Report for this class
	 */
	
	public void loadAssignments() {
		try {
			res = Connect.meTo(Connect.EDLINE_URL + link + "/Current_Assignments_Report");

			Document doc = Jsoup.parse(res.body());
			if (doc.select("iframe#docViewBodyFrame").attr("src").equals(""))
				return;

			res = Connect.meTo(doc.select("iframe#docViewBodyFrame").attr("src"));

			grades = new Grades(this, res.body());

		} catch (IOException e) {
			System.out.println("Failed to load grades, not a class with grades");
			e.printStackTrace();
		}
	}
	
	/**
	 * Title case the given string
	 * @param givenString
	 * @return returns the Title Case Version of the String
	 */
	
	private String toTitleCase(String givenString) {
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
	
	public String getCourseName() {
		return course;
	}
	
	public Grades getGrades() {
		if (grades == null)
			loadAssignments();
		return grades;
	}
	
	public String getLink() {
		return link;
	}
	
	protected void setTeacher(String t) {
		teacher = t;
	}
	
	public String getTeacher() {
		return teacher;
	}
}
