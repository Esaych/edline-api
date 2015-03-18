package com.esaych.edline.api;

import java.util.ArrayList;

public class Grades {
	private String src;
	private String textsrc;
	
	public String course;
	public String teacher;
	
	public Grades(String course, String source) {
		this.course = course;
		src = source.substring(source.indexOf("<div class=\"edlDocViewContents\" style=\"\" >"), source.indexOf("</div>" , source.indexOf("<div class=\"edlDocViewContents\" style=\"\" >")));
		textsrc = shaveSrc(src);
		
		teacher = src.substring(src.indexOf("Teacher: ") + 9, src.indexOf("<", src.indexOf("Teacher: ")));
		System.out.println("Class: " + course + " tought by teacher: " + teacher);
		
		System.out.println(textsrc);
		
		String scores = textsrc.substring(textsrc.indexOf("Category"),textsrc.indexOf("Current Assignments")-1).replace("\n", " ").replace("&nbsp", "*").trim();
		String work = textsrc.substring(textsrc.indexOf("Current Assignments")).replace("\n", " ").replace("&nbsp", "*").trim();
		
		ArrayList<Category> categories;
		int i = scores.indexOf("*");
		
		ArrayList<Assignment> assignments;
	}
	
	private String shaveSrc(String src) {
		boolean inHTML = false;
		String output = "";
		for (char c : src.toCharArray()) {
			if (c == '<')
				inHTML = true;
			if (!inHTML)
				output += c;
			if (c == '>')
				inHTML = false;
		}
		return output;
	}
}

class Category {
	String name;
	int weight;
	double pts;
	double max; 
	double percent;
	
	Category(String src) {//Source is a single row of info
		int i = 0;
		this.name=src.substring(i,src.indexOf("\n"));
		i=src.indexOf("\n");
		this.weight=Integer.valueOf(src.substring(i,src.indexOf("\n",i)));
		i=src.indexOf("\n",i);
		this.pts=Double.valueOf(src.substring(i,src.indexOf("\n",i)));
		i=src.indexOf("\n",i);
		this.max=Double.valueOf(src.substring(i,src.indexOf("\n",i)));
		i=src.indexOf("\n",i);
		this.percent=Double.valueOf(src.substring(i,src.indexOf("\n",i)));
	}
}

class Assignment {
	String name;
	String due;
	String category;
	String weight;
	double grade;
	double max;
	char letter;
	
	Assignment(String src) {
		
	}
}