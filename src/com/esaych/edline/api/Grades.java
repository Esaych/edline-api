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
		
		String scores = textsrc.substring(textsrc.indexOf("Category"),textsrc.indexOf("Current Assignments",textsrc.indexOf("Category"))).replace("\r\n", "|").replace("\n","").replace("&nbsp;", "*").replace(" / ","|").trim();
		String work = textsrc.substring(textsrc.indexOf("Current Assignments",textsrc.indexOf("Current Assignments"))).replace("\r\n", " ").replace("/n"," ").replace("&nbsp", "*").trim();
		
		System.out.println(scores);
		
		ArrayList<Category> categories = new ArrayList<Category>();
		int i = scores.indexOf("*")+1;
		
		while(i<scores.indexOf("Current Grade")){
			categories.add(new Category(scores.substring(i, scores.indexOf("*",i))));
			i = scores.indexOf("*",i)+1;
		}
		
		for(Category f:categories){
			System.out.println("Hello" + f);
		}
		
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
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
		System.out.println(src + "\t" + src.length());
		int i = src.indexOf("|");
		System.out.println(i);
		this.name = i!=src.lastIndexOf("|") ? src.substring(i+1,src.indexOf("|",i+1)) : "";
		i= i!=src.lastIndexOf("|") ? src.indexOf("|",i+1): src.lastIndexOf("|");
		System.out.println(i);
		this.weight = i!=src.lastIndexOf("|") ? Integer.valueOf(src.substring(i+1,src.indexOf("|",i+1))) : 0;
		i= i!=src.lastIndexOf("|") ? src.indexOf("|",i+1): src.lastIndexOf("|");
		System.out.println(i);
		this.pts = i!=src.lastIndexOf("|") ? Double.valueOf(src.substring(i+1,src.indexOf("|",i+1))) : 0.0;
		i= i!=src.lastIndexOf("|") ? src.indexOf("|",i+1): src.lastIndexOf("|");
		System.out.println(i);
		this.max = i!=src.lastIndexOf("|") ? Double.valueOf(src.substring(i+1,src.indexOf("|",i+1))) : 0.0;
		i= i!=src.lastIndexOf("|") ? src.indexOf("|",i+1): src.lastIndexOf("|");
		this.percent = i!=src.lastIndexOf("|") ? Double.valueOf(src.substring(i+1,src.indexOf("|",i+1))) : 0.0;
	}
	
	public String toString(){
		return name + " " + weight + " " + pts + " " + max + " " + percent;
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