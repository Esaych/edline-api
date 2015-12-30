# edline-api
Java http://www.edline.net Parser, and API

**Quick Start:**

Get all course names:

`ArrayList<Course> courses = Edline.load(username, password);`
		
or to get a specific course:

```
Edline.load(username, password);
Course course = Edline.getCourse(courseName);
```
		
for every course in that list you may:

```
myCourse.getCourseName(); //get name of found course
myCourse.getLink(); //get Edline link to that webpage
myCourse.getTeacher(); //get name of teacher
Grades grades = myCourse.getGrades(); //get current grade report of the class
```

for every grades report you may:

```
grades.overallGrade(); //get the grade you currently have in the class
ArrayList<Category> categories = grades.getCategories(); //ex: formative, summative, practice/prep
ArrayList<Assignment> assignments = grades.getAssignments(); //ex: Unit 1A TEST
```

for every category you may:

```
category.toString(); //retrieve string val of category
call any of these vars: //ex: category.name;
String name;
int weight;
double pts;
double max; 
double percent;
double accpercent; //(calculated percentage aside from rounded online value)
```

for every assignment you may:

```
assignment.toString(); //retrieve string val of assignment
call any of these vars: //ex: assignment.name;
String name;
String due;
String category;
double weight;
double grade;
double max;
char letter;
```
	    
Example Code:
```
ArrayList<Course> courses = load("myUsername", "myPassword");
for (Course c : courses)
  if (c.getGrades() != null)
    c.getGrades().toString();
```
