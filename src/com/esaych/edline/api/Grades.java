package com.esaych.edline.api;

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
	
	Category(String src) {
		
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