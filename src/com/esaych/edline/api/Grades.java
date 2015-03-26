package com.esaych.edline.api;

import java.util.ArrayList;
import java.util.Iterator;

public class Grades {
	private String src;
	private ArrayList<String> srcarray;
	private Iterator<String> it;
	
	private Course course;
	private double classGrade;
	
	private ArrayList<Category> categories;
	private ArrayList<Assignment> assignments;
	
	public Grades(Course mycourse, String source) {
		this.course = mycourse;
		src = source.substring(source.indexOf("<div class=\"edlDocViewContents\" style=\"\" >"), source.indexOf("</div>" , source.indexOf("<div class=\"edlDocViewContents\" style=\"\" >")));
		srcarray = shaveSrc(src);
		
//		System.out.println("\n\n");
		
		//Collect teacher name
		course.setTeacher(src.substring(src.indexOf("Teacher: ") + 9, src.indexOf("<", src.indexOf("Teacher: "))));
//		System.out.println("Class: " + course.getCourseName() + " taught by teacher: " + course.getTeacher());
		
		//initiate arraylists for grades
		categories = new ArrayList<Category>();
		assignments = new ArrayList<Assignment>();
		it = srcarray.iterator();
		
		//Find category header, and start parsing for grades there
		advanceIterator("Category");
		while (true) {
			advanceIterator("[STARTROW]");
			String next = it.next();
			if (next.equals("Current Grade"))
				break;
			categories.add(new Category(next, it.next(), it.next(), it.next())); //in every row, feed next parts of iterator
		}
		
		//after the categories there is current grades row, found here
		classGrade = Double.parseDouble(it.next()); //TODO: see if this breaks when no grades entered
//		System.out.println("\nCurrent Grade: " + classGrade + "%\n");
		advanceIterator("Current Assignments");
		advanceIterator("[STARTROW]");
//		
//		//test statement
//		for (Category f : categories){
//			System.out.println(f);
//		}
		
		//after current grade, assignments start
		advanceIterator("[STARTROW]");
		while (it.hasNext()) {
			it.next();
			assignments.add(new Assignment(it.next(), it.next(), it.next(), it.next(), it.next(), it.next(), it.next()));
			advanceIterator("[STARTROW]");
		}
		
//		//test statement
//		for (Assignment a : assignments) {
//			System.out.println(a);
//		}
	}
	
	/**
	 * Advances location of private (it)erator
	 * @param toWhat var to advance to, if not found, advances to end
	 */
	
	private void advanceIterator(String toWhat) {
		while (it.hasNext())
			if (it.next().equals(toWhat))
				break;
	}
	
	/**
	 * Creates an array list of all rows, delimited by [STARTROW] and [ENDROW]
	 * @param src source to clean up
	 * @return arraylist of source
	 */
	
	private ArrayList<String> shaveSrc(String src) {
		boolean inHTML = false;
		String output = "";
		char[] array = src.toCharArray();
		for (int i = 0; i < array.length; i++) {
			char c = array[i];
			if (c == '<') {
				inHTML = true;
				if ((array[i+1] + "" + array[i+2]).toLowerCase().equals("tr"))
					output += "\n[STARTROW]\n";
				if ((array[i+1] + "" + array[i+2] + array[i+3]).toLowerCase().equals("/tr"))
					output += "\n[ENDROW]\n";
			}
			if (!inHTML)
				output += c;
			if (c == '>')
				inHTML = false;
		}
		
		String[] srcArray = output.split("\n");
		ArrayList<String> srcList = new ArrayList<String>();
		for (String s : srcArray)
			if (!s.replaceAll("\n", "").trim().equals(""))
				srcList.add(s.replaceAll("\n", "").trim());
		return srcList;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public ArrayList<Category> getCategories() {
		return categories;
	}
	
	public ArrayList<Assignment> getAssignments() {
		return assignments;
	}
	
	public double overallGrade() {
		return classGrade;
	}
}

class Category {
	String name;
	int weight;
	double pts;
	double max; 
	double percent;
	double accpercent;
	
	boolean incomplete = false;
	
	Category(String name, String weight, String pts, String percent) {//Source is a single row of info
		this.name = name;
		this.weight = Integer.parseInt(weight);
		try {
			this.pts = Double.parseDouble(pts.split("/")[0].trim());
			this.max = Double.parseDouble(pts.split("/")[1].trim());
			this.percent = Double.parseDouble(percent);
			accpercent = this.pts/max;
		} catch (NumberFormatException e) {
			this.pts = 0;
			this.max = 0;
			this.percent = 0;
			accpercent = 0;
			
			incomplete = true;
		}
	}
	
	public String toString() {
		return name + ": " + weight + " " + pts + "/" + max + " = " + percent + "%";
	}
}

class Assignment {
	String name;
	String due;
	String category;
	double weight;
	double grade;
	double max;
	char letter;
	
	boolean incomplete = false;
	
	Assignment(String name, String due, String category, String weight, String grade, String max, String letter) {
		this.name = name;
		this.due = due;
		this.category = category;
		try {
			this.weight = Double.valueOf(weight);
			this.grade = Double.valueOf(grade);
			this.max = Double.valueOf(max);
			this.letter = letter.charAt(0);
		} catch (NumberFormatException e) {
			this.weight = 0;
			this.grade = 0;
			try {
				this.max = Double.valueOf(max);
			} catch (NumberFormatException e2) {
				this.max = 0;
			}
			this.letter = (letter + ' ').charAt(0);

			incomplete = true;
		}
	}

	public String toString() {
		return name + " - Due: " + due + " Category: " + category + " Weight: " + weight + " Grade: " + grade + "/" + max + " = " + letter;
	}
}